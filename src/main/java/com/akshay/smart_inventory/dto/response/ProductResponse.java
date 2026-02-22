package com.akshay.smart_inventory.dto.response;

import lombok.Data;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Integer reorderLevel;

    private Long categoryId;
    private String categoryName;
}