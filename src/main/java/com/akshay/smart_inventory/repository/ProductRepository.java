package com.akshay.smart_inventory.repository;

import com.akshay.smart_inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByNameAndCategoryId(String name, Long categoryId);

}