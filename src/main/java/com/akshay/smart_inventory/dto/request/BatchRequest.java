package com.akshay.smart_inventory.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BatchRequest {

    @NotBlank(message = "Batch number is required")
    private String batchNumber;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Cost price is required")
    private BigDecimal costPrice;

    @NotNull(message = "Received date is required")
    private LocalDate receivedDate;

    private String supplierName;

    @NotNull(message = "Product ID is required")
    private Long productId;
}