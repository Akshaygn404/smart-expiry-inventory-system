package com.akshay.smart_inventory.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {


    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @Min(value = 0, message = "Reorder level cannot be negative")
    private Integer reorderLevel;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}