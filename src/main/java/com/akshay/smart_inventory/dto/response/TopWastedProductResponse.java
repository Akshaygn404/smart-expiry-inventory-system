package com.akshay.smart_inventory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TopWastedProductResponse {

    private String productName;
    private Integer totalQuantity;
    private BigDecimal totalLoss;
}