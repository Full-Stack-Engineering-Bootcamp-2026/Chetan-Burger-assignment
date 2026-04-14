package com.burgershop.dto.request;

import com.burgershop.enums.ProductCategory;
import com.burgershop.enums.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Category is required")
    private ProductCategory category;

    @NotNull(message = "Type is required")
    private ProductType type;

    public ProductRequest(String name, Double price, ProductCategory category, ProductType type) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.type = type;
    }

  
}
