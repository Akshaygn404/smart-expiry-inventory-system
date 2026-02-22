package com.akshay.smart_inventory.repository;

import com.akshay.smart_inventory.dto.response.CategoryResponse;
import com.akshay.smart_inventory.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    CategoryResponse findByName(@NotBlank @Size(min = 3, max = 50) String name);

    boolean existsByName(@NotBlank @Size(min = 3, max = 50) String name);
}
