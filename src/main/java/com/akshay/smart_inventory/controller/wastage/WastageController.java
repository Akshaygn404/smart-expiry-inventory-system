package com.akshay.smart_inventory.controller.wastage;

import com.akshay.smart_inventory.dto.request.WastageRequest;
import com.akshay.smart_inventory.dto.response.TopWastedProductResponse;
import com.akshay.smart_inventory.dto.response.WastageMonthlyResponse;
import com.akshay.smart_inventory.dto.response.WastageResponse;
import com.akshay.smart_inventory.dto.response.WastageSummaryResponse;
import com.akshay.smart_inventory.service.wastage.IWastageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/wastage")
@RequiredArgsConstructor
public class WastageController {

    private final IWastageService wastageService;

    @PostMapping
    public ResponseEntity<WastageResponse> recordWastage(
            @RequestBody WastageRequest request) {

        return ResponseEntity.ok(
                wastageService.recordWastage(request));
    }

    @GetMapping
    public ResponseEntity<List<WastageResponse>> getAllWastage() {
        return ResponseEntity.ok(
                wastageService.getAllWastage());
    }

    @GetMapping("/summary")
    public ResponseEntity<WastageSummaryResponse> getSummary() {
        return ResponseEntity.ok(
                wastageService.getWastageSummary());
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<WastageMonthlyResponse>> getMonthlyStats() {
        return ResponseEntity.ok(
                wastageService.getMonthlyWastage());
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<TopWastedProductResponse>> getTopWastedProducts() {
        return ResponseEntity.ok(
                wastageService.getTopWastedProducts());
    }
}