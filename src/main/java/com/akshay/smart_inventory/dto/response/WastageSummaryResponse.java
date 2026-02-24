package com.akshay.smart_inventory.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class WastageSummaryResponse {

    private Integer totalQuantityWasted;
    private BigDecimal totalFinancialLoss;
    private Integer totalRecords;
}