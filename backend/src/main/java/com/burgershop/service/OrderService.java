package com.burgershop.service;

import com.burgershop.dto.request.CartItemRequest;
import com.burgershop.dto.request.CheckoutRequest;
import com.burgershop.dto.response.AppliedComboDTO;
import com.burgershop.dto.response.OrderDTO;
import com.burgershop.dto.response.OrderItemDTO;
import com.burgershop.entity.Combo;
import com.burgershop.entity.Order;
import com.burgershop.entity.OrderItem;
import com.burgershop.entity.Product;
import com.burgershop.exception.BadRequestException;
import com.burgershop.exception.ResourceNotFoundException;
import com.burgershop.repository.ComboRepository;
import com.burgershop.repository.OrderRepository;
import com.burgershop.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {


    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final ComboRepository comboRepository;

    private final BillOptimizationService billOptimizationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

   
    public OrderDTO checkout(CheckoutRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BadRequestException("Cart cannot be empty");
        }

      
        Map<Long, Product> productMap = new HashMap<>();
        for (CartItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + item.getProductId()));
            productMap.put(product.getId(), product);
        }

        Map<Long, Integer> mergedItems = new HashMap<>();
        for (CartItemRequest item : request.getItems()) {
            mergedItems.merge(item.getProductId(), item.getQuantity(), Integer::sum);
        }

        for (Map.Entry<Long, Integer> entry : mergedItems.entrySet()) {
            if (entry.getValue() < 1 || entry.getValue() > 9) {
                throw new BadRequestException("Quantity must be between 1 and 9 for product id: " + entry.getKey());
            }
        }

        double actualTotal = 0;
        for (Map.Entry<Long, Integer> entry : mergedItems.entrySet()) {
            Product product = productMap.get(entry.getKey());
            actualTotal += product.getPrice() * entry.getValue();
        }

        List<Combo> combos = comboRepository.findAll();
        BillOptimizationService.OptimizationResult optimizationResult =
                billOptimizationService.optimize(mergedItems, productMap, combos);

        double optimizedTotal = optimizationResult.getTotalCost();
        List<AppliedComboDTO> appliedCombos = optimizationResult.getAppliedCombos();

        Order order = new Order();
        order.setUserName(request.getName());
        order.setEmail(request.getEmail());
        order.setActualTotal(actualTotal);
        order.setOptimizedTotal(optimizedTotal);

        try {
            order.setAppliedCombosJson(objectMapper.writeValueAsString(appliedCombos));
        } catch (JsonProcessingException e) {
            order.setAppliedCombosJson("[]");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : mergedItems.entrySet()) {
            Product product = productMap.get(entry.getKey());
            OrderItem orderItem = new OrderItem(order, product, entry.getValue(), product.getPrice());
            orderItems.add(orderItem);
        }
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return toDTO(savedOrder, deserializeCombos(savedOrder.getAppliedCombosJson()));
    }


    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(order -> toDTO(order, deserializeCombos(order.getAppliedCombosJson())))
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return toDTO(order, deserializeCombos(order.getAppliedCombosJson()));
    }


    private OrderDTO toDTO(Order order, List<AppliedComboDTO> appliedCombos) {
        List<OrderItemDTO> items = order.getItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getPrice() * item.getQuantity()
                ))
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getId(),
                order.getUserName(),
                order.getEmail(),
                items,
                order.getActualTotal(),
                order.getOptimizedTotal(),
                order.getActualTotal() - order.getOptimizedTotal(),
                appliedCombos,
                order.getCreatedAt()
        );
    }

    private List<AppliedComboDTO> deserializeCombos(String json) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<AppliedComboDTO>>() {});
        } catch (JsonProcessingException e) {
            return new ArrayList<>();
        }
    }
}
