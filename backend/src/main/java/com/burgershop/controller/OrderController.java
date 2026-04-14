package com.burgershop.controller;

import com.burgershop.dto.request.CheckoutRequest;
import com.burgershop.dto.response.ApiResponse;
import com.burgershop.dto.response.OrderDTO;
import com.burgershop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

  
    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<OrderDTO>> checkout(@Valid @RequestBody CheckoutRequest request) {
        OrderDTO order = orderService.checkout(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(order, "Order placed successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(ApiResponse.success(orders, "Orders fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.success(order, "Order fetched successfully"));
    }
}
