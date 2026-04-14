package com.burgershop.repository;

import com.burgershop.entity.Product;
import com.burgershop.enums.ProductCategory;
import com.burgershop.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(ProductCategory category);

    List<Product> findByType(ProductType type);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryAndType(ProductCategory category, ProductType type);

    List<Product> findByCategoryAndNameContainingIgnoreCase(ProductCategory category, String name);

    List<Product> findByTypeAndNameContainingIgnoreCase(ProductType type, String name);

    List<Product> findByCategoryAndTypeAndNameContainingIgnoreCase(ProductCategory category, ProductType type, String name);
}
