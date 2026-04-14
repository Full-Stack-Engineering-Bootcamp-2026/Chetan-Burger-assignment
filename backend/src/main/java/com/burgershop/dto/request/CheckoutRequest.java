package com.burgershop.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CheckoutRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotEmpty(message = "Cart cannot be empty")
    @Valid
    private List<CartItemRequest> items;

 

    public CheckoutRequest(String name, String email, List<CartItemRequest> items) {
        this.name = name;
        this.email = email;
        this.items = items;
    }

    
}
