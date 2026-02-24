package com.akshay.smart_inventory.service.dashboard;

import com.akshay.smart_inventory.dto.response.DashboardResponse;
import com.akshay.smart_inventory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService implements IDashboardService {

    private final ProductRepository productRepository;
    private final BatchRepository batchRepository;
    private final WastageRepository wastageRepository;
    private final AlertRepository alertRepository;

    @Override
    public DashboardResponse getDashboard() {

        DashboardResponse response = new DashboardResponse();

        response.setTotalProducts(productRepository.count());
        response.setTotalBatches(batchRepository.count());
        response.setTotalActiveStock(batchRepository.getTotalActiveStock());
        response.setTotalFinancialLoss(wastageRepository.getTotalLossAmount());
        response.setActiveAlerts(alertRepository.countByResolvedFalse());
        LocalDate today = LocalDate.now();
        LocalDate nextSevenDays = today.plusDays(7);

        response.setHighRiskBatches(
                batchRepository.getHighRiskBatchCountBetween(
                        today,
                        nextSevenDays
                )
        );

        return response;
    }
}