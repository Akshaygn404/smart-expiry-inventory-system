package com.akshay.smart_inventory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class WastageMonthlyResponse {

    private Integer year;
    private Integer month;
    private Integer totalQuantity;
    private BigDecimal totalLoss;
}