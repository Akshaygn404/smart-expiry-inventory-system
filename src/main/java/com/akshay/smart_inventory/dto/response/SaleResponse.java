package com.akshay.smart_inventory.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SaleResponse {

    private Long saleId;
    private Long productId;
    private String productName;
    private Integer totalQuantity;
    private LocalDateTime saleDate;
}