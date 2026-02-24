package com.akshay.smart_inventory.service.batch;

import com.akshay.smart_inventory.dto.request.BatchRequest;
import com.akshay.smart_inventory.dto.response.BatchResponse;

import java.util.List;

public interface IBatchService {

    BatchResponse createBatch(BatchRequest request);

    BatchResponse getBatchById(Long id);

    List<BatchResponse> getAllBatches();

    void deleteBatch(Long id);

    List<BatchResponse> getExpiredBatches();

    List<BatchResponse> getBatchesExpiringInDays(int days);

    List<BatchResponse> getHighRiskBatches();
}