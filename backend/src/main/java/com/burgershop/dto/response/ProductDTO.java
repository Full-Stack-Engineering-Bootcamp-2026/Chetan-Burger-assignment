package com.burgershop.dto.response;

import com.burgershop.enums.ProductCategory;
import com.burgershop.enums.ProductType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private ProductCategory category;
    private ProductType type;
}