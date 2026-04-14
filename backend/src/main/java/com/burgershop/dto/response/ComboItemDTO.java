package com.burgershop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComboItemDTO {

    private Long productId;
    private String productName;
    private Double productPrice;
    private Integer quantity;

    
}
