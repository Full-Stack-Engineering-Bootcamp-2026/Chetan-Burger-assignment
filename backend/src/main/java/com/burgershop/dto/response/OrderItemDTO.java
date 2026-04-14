package com.burgershop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long productId;
    private String productName;
    private Double unitPrice;
    private Integer quantity;
    private Double totalPrice;

}