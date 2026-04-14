package com.burgershop.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ComboRequest {

    @NotBlank(message = "Combo name is required")
    private String name;

    @NotNull(message = "Combo price is required")
    @Positive(message = "Combo price must be positive")
    private Double comboPrice;

    @NotEmpty(message = "Combo must have at least one item")
    @Valid
    private List<ComboItemRequest> items;

    public ComboRequest(String name, Double comboPrice, List<ComboItemRequest> items) {
        this.name = name;
        this.comboPrice = comboPrice;
        this.items = items;
    }

}
