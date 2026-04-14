package com.burgershop.controller;

import com.burgershop.dto.request.ProductRequest;
import com.burgershop.dto.response.ApiResponse;
import com.burgershop.dto.response.ProductDTO;
import com.burgershop.enums.ProductCategory;
import com.burgershop.enums.ProductType;
import com.burgershop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

   
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) ProductType type,
            @RequestParam(required = false) String search) {

        List<ProductDTO> products = productService.getAllProducts(category, type, search);
        return ResponseEntity.ok(ApiResponse.success(products, "Products fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success(product, "Product fetched successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductDTO product = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(product, "Product created successfully"));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(
            @PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        ProductDTO product = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.success(product, "Product updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Product deleted successfully"));
    }
}
