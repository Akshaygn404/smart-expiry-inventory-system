package com.akshay.smart_inventory.service.category;

import com.akshay.smart_inventory.dto.request.CategoryRequest;
import com.akshay.smart_inventory.dto.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

}
