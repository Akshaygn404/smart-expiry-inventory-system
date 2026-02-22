package com.akshay.smart_inventory.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BatchResponse {

    private Long id;
    private String batchNumber;
    private LocalDate expiryDate;
    private Integer quantity;
    private BigDecimal costPrice;
    private LocalDate receivedDate;
    private String supplierName;

    private Long productId;
    private String productName;
}