package com.akshay.smart_inventory.service.batch;

import com.akshay.smart_inventory.dto.request.BatchRequest;
import com.akshay.smart_inventory.dto.response.BatchResponse;
import com.akshay.smart_inventory.exception.DuplicateResourceException;
import com.akshay.smart_inventory.exception.ResourceNotFoundException;
import com.akshay.smart_inventory.model.Batch;
import com.akshay.smart_inventory.model.Product;
import com.akshay.smart_inventory.repository.BatchRepository;
import com.akshay.smart_inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchService implements IBatchService {

    private final BatchRepository batchRepository;
    private final ProductRepository productRepository;

    @Override
    public BatchResponse createBatch(BatchRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (batchRepository.existsByBatchNumberAndProductId(
                request.getBatchNumber(), request.getProductId())) {

            throw new DuplicateResourceException(
                    "Batch number already exists for this product");
        }

        Batch batch = new Batch();
        batch.setBatchNumber(request.getBatchNumber());
        batch.setExpiryDate(request.getExpiryDate());
        batch.setQuantity(request.getQuantity());
        batch.setCostPrice(request.getCostPrice());
        batch.setReceivedDate(request.getReceivedDate());
        batch.setSupplierName(request.getSupplierName());
        batch.setProduct(product);

        Batch savedBatch = batchRepository.save(batch);

        return mapToResponse(savedBatch);
    }

    @Override
    public BatchResponse getBatchById(Long id) {

        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));

        return mapToResponse(batch);
    }

    @Override
    public List<BatchResponse> getAllBatches() {

        return batchRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBatch(Long id) {

        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));

        batchRepository.delete(batch);
    }

    private BatchResponse mapToResponse(Batch batch) {

        BatchResponse response = new BatchResponse();

        response.setId(batch.getId());
        response.setBatchNumber(batch.getBatchNumber());
        response.setExpiryDate(batch.getExpiryDate());
        response.setQuantity(batch.getQuantity());
        response.setCostPrice(batch.getCostPrice());
        response.setReceivedDate(batch.getReceivedDate());
        response.setSupplierName(batch.getSupplierName());
        response.setProductId(batch.getProduct().getId());
        response.setProductName(batch.getProduct().getName());

        long daysLeft =
                java.time.temporal.ChronoUnit.DAYS
                        .between(LocalDate.now(), batch.getExpiryDate());

        response.setDaysLeft((int) daysLeft);
        response.setRiskScore(calculateRiskScore(batch));

        return response;
    }

    @Override
    public List<BatchResponse> getExpiredBatches() {

        List<Batch> expiredBatches =
                batchRepository.findByExpiryDateBefore(LocalDate.now());

        return expiredBatches.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<BatchResponse> getBatchesExpiringInDays(int days) {

        LocalDate today = LocalDate.now();
        LocalDate targetDate = today.plusDays(days);

        List<Batch> batches =
                batchRepository.findByExpiryDateBetween(today, targetDate);

        return batches.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private int calculateRiskScore(Batch batch) {

        long daysLeft =
                java.time.temporal.ChronoUnit.DAYS
                        .between(LocalDate.now(), batch.getExpiryDate());

        int urgencyFactor;

        if (daysLeft <= 7) {
            urgencyFactor = 3;
        } else if (daysLeft <= 15) {
            urgencyFactor = 2;
        } else if (daysLeft <= 30) {
            urgencyFactor = 1;
        } else {
            urgencyFactor = 0;
        }

        return batch.getQuantity() * urgencyFactor;
    }

    @Override
    public List<BatchResponse> getHighRiskBatches() {

        return batchRepository.findAll()
                .stream()
                .filter(batch -> batch.getQuantity() > 0)
                .filter(batch -> !batch.getExpiryDate().isBefore(LocalDate.now()))
                .map(this::mapToResponse)
                .filter(response -> response.getRiskScore() > 0)
                .toList();
    }
}