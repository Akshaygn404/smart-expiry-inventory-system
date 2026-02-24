package com.akshay.smart_inventory.service.wastage;

import com.akshay.smart_inventory.dto.request.WastageRequest;
import com.akshay.smart_inventory.dto.response.WastageResponse;
import com.akshay.smart_inventory.dto.response.WastageSummaryResponse;

import java.util.List;

public interface IWastageService {

    WastageResponse recordWastage(WastageRequest request);

    List<WastageResponse> getAllWastage();

    WastageSummaryResponse getWastageSummary();
}