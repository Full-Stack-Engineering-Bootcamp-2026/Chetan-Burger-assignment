package com.burgershop.service;

import com.burgershop.dto.request.ComboItemRequest;
import com.burgershop.dto.request.ComboRequest;
import com.burgershop.dto.response.ComboDTO;
import com.burgershop.dto.response.ComboItemDTO;
import com.burgershop.entity.Combo;
import com.burgershop.entity.ComboItem;
import com.burgershop.entity.Product;
import com.burgershop.exception.ResourceNotFoundException;
import com.burgershop.repository.ComboRepository;
import com.burgershop.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ComboService {


    private final ComboRepository comboRepository;

    private final ProductRepository productRepository;

    public List<ComboDTO> getAllCombos() {
        return comboRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ComboDTO getComboById(Long id) {
        Combo combo = comboRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combo not found with id: " + id));
        return toDTO(combo);
    }

    
    public ComboDTO createCombo(ComboRequest request) {
        Combo combo = new Combo();
        combo.setName(request.getName());
        combo.setComboPrice(request.getComboPrice());

        List<ComboItem> items = new ArrayList<>();
        for (ComboItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemReq.getProductId()));
            ComboItem item = new ComboItem(combo, product, itemReq.getQuantity());
            items.add(item);
        }
        combo.setItems(items);

        return toDTO(comboRepository.save(combo));
    }

    public ComboDTO updateCombo(Long id, ComboRequest request) {
        Combo combo = comboRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combo not found with id: " + id));
        combo.setName(request.getName());
        combo.setComboPrice(request.getComboPrice());

        combo.getItems().clear();
        for (ComboItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemReq.getProductId()));
            ComboItem item = new ComboItem(combo, product, itemReq.getQuantity());
            combo.getItems().add(item);
        }

        return toDTO(comboRepository.save(combo));
    }

    public void deleteCombo(Long id) {
        if (!comboRepository.existsById(id)) {
            throw new ResourceNotFoundException("Combo not found with id: " + id);
        }
        comboRepository.deleteById(id);
    }

    private ComboDTO toDTO(Combo combo) {
        List<ComboItemDTO> items = combo.getItems().stream()
                .map(item -> new ComboItemDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());

        double actualPrice = items.stream()
                .mapToDouble(i -> i.getProductPrice() * i.getQuantity())
                .sum();

        return new ComboDTO(
                combo.getId(),
                combo.getName(),
                combo.getComboPrice(),
                actualPrice,
                items
        );
    }
}
