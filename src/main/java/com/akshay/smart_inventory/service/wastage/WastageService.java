package com.akshay.smart_inventory.service.wastage;

import com.akshay.smart_inventory.dto.request.WastageRequest;
import com.akshay.smart_inventory.dto.response.TopWastedProductResponse;
import com.akshay.smart_inventory.dto.response.WastageMonthlyResponse;
import com.akshay.smart_inventory.dto.response.WastageResponse;
import com.akshay.smart_inventory.dto.response.WastageSummaryResponse;
import com.akshay.smart_inventory.exception.ResourceNotFoundException;
import com.akshay.smart_inventory.model.Batch;
import com.akshay.smart_inventory.model.Wastage;
import com.akshay.smart_inventory.repository.BatchRepository;
import com.akshay.smart_inventory.repository.WastageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WastageService implements IWastageService {

    private final WastageRepository wastageRepository;
    private final BatchRepository batchRepository;

    @Override
    @Transactional
    public WastageResponse recordWastage(WastageRequest request) {

        Batch batch = batchRepository.findById(request.getBatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));

        if (request.getQuantity() <= 0) {
            throw new RuntimeException("Wastage quantity must be positive");
        }

        if (request.getQuantity() > batch.getQuantity()) {
            throw new RuntimeException("Wastage quantity exceeds available stock");
        }


        batch.setQuantity(batch.getQuantity() - request.getQuantity());
        batchRepository.save(batch);

        BigDecimal totalLoss =
                batch.getCostPrice()
                        .multiply(BigDecimal.valueOf(request.getQuantity()));
        Wastage wastage = new Wastage();
        wastage.setBatch(batch);
        wastage.setQuantity(request.getQuantity());
        wastage.setReason(request.getReason());
        wastage.setTotalLossAmount(totalLoss);
        wastage.setRecordedAt(LocalDateTime.now());

        Wastage saved = wastageRepository.save(wastage);

        return mapToResponse(saved);
    }

    @Override
    public List<WastageResponse> getAllWastage() {

        return wastageRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private WastageResponse mapToResponse(Wastage wastage) {

        WastageResponse response = new WastageResponse();

        response.setId(wastage.getId());
        response.setBatchId(wastage.getBatch().getId());
        response.setBatchNumber(wastage.getBatch().getBatchNumber());
        response.setQuantity(wastage.getQuantity());
        response.setReason(wastage.getReason());
        response.setTotalLossAmount(wastage.getTotalLossAmount());
        response.setRecordedAt(wastage.getRecordedAt());

        return response;
    }
    @Override
    public WastageSummaryResponse getWastageSummary() {

        WastageSummaryResponse response = new WastageSummaryResponse();

        response.setTotalQuantityWasted(
                wastageRepository.getTotalWastedQuantity());

        response.setTotalFinancialLoss(
                wastageRepository.getTotalLossAmount());

        response.setTotalRecords(
                (int) wastageRepository.count());

        return response;
    }
    @Override
    public List<WastageMonthlyResponse> getMonthlyWastage() {

        return wastageRepository.getMonthlyWastageStats()
                .stream()
                .map(row -> new WastageMonthlyResponse(
                        ((Number) row[0]).intValue(),
                        ((Number) row[1]).intValue(),
                        ((Number) row[2]).intValue(),
                        (java.math.BigDecimal) row[3]
                ))
                .toList();
    }

    @Override
    public List<TopWastedProductResponse> getTopWastedProducts() {

        return wastageRepository.getTopWastedProducts()
                .stream()
                .map(row -> new TopWastedProductResponse(
                        (String) row[0],
                        ((Number) row[1]).intValue(),
                        (java.math.BigDecimal) row[2]
                ))
                .toList();
    }
}