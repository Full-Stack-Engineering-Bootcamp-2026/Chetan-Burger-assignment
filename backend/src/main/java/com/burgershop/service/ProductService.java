package com.burgershop.service;

import com.burgershop.dto.request.ProductRequest;
import com.burgershop.dto.response.ProductDTO;
import com.burgershop.entity.Product;
import com.burgershop.enums.ProductCategory;
import com.burgershop.enums.ProductType;
import com.burgershop.exception.ResourceNotFoundException;
import com.burgershop.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.burgershop.BurgerShopApplication.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    
    private final ProductRepository productRepository;

    // @Autowired
    // private ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts(ProductCategory category, ProductType type, String search) {
        List<Product> products;

        if (category != null && type != null && search != null && !search.isEmpty()) {
            products = productRepository.findByCategoryAndTypeAndNameContainingIgnoreCase(category, type, search);
        } else if (category != null && type != null) {
            products = productRepository.findByCategoryAndType(category, type);
        } else if (category != null && search != null && !search.isEmpty()) {
            products = productRepository.findByCategoryAndNameContainingIgnoreCase(category, search);
        } else if (type != null && search != null && !search.isEmpty()) {
            products = productRepository.findByTypeAndNameContainingIgnoreCase(type, search);
        } else if (category != null) {
            products = productRepository.findByCategory(category);
        } else if (type != null) {
            products = productRepository.findByType(type);
        } else if (search != null && !search.isEmpty()) {
            products = productRepository.findByNameContainingIgnoreCase(search);
        } else {
            products = productRepository.findAll();
        }

        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
                
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return toDTO(product);
    }

    public ProductDTO createProduct(ProductRequest request) {

       // modelMapper.map(request,Product.class);
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setType(request.getType());
        return toDTO(productRepository.save(product));
    }

    public ProductDTO updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setType(request.getType());
        return toDTO(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getType()
        );
    }
}
