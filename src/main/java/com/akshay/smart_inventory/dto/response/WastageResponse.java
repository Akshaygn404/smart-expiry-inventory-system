package com.akshay.smart_inventory.dto.response;

import com.akshay.smart_inventory.model.WastageReason;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WastageResponse {

    private Long id;
    private Long batchId;
    private String batchNumber;
    private Integer quantity;
    private WastageReason reason;
    private BigDecimal totalLossAmount;
    private LocalDateTime recordedAt;
}