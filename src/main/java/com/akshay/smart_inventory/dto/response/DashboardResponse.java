package com.akshay.smart_inventory.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DashboardResponse {

    private Long totalProducts;
    private Long totalBatches;
    private Integer totalActiveStock;
    private BigDecimal totalFinancialLoss;
    private Long activeAlerts;
    private Long highRiskBatches;
}