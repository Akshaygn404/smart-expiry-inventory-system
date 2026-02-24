package com.akshay.smart_inventory.dto.request;

import com.akshay.smart_inventory.model.WastageReason;
import lombok.Data;

@Data
public class WastageRequest {

    private Long batchId;
    private Integer quantity;
    private WastageReason reason;
}