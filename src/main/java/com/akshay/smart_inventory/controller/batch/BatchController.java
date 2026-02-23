package com.akshay.smart_inventory.controller.batch;


import com.akshay.smart_inventory.dto.request.BatchRequest;
import com.akshay.smart_inventory.dto.response.BatchResponse;
import com.akshay.smart_inventory.service.batch.IBatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/batches")
@RequiredArgsConstructor
public class BatchController {

    private final IBatchService batchService;
    @PostMapping
    public ResponseEntity<BatchResponse> createBatch(
            @Valid @RequestBody BatchRequest request) {

        BatchResponse response = batchService.createBatch(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchResponse> getBatchById(@PathVariable Long id) {

        BatchResponse response = batchService.getBatchById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BatchResponse>> getAllBatches() {

        List<BatchResponse> response = batchService.getAllBatches();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBatch(@PathVariable Long id) {

        batchService.deleteBatch(id);

        return ResponseEntity.ok("Batch deleted successfully");
    }

    @GetMapping("/expired")
    public ResponseEntity<List<BatchResponse>> getExpiredBatches() {

        return ResponseEntity.ok(batchService.getExpiredBatches());
    }

    @GetMapping("/expiring")
    public ResponseEntity<List<BatchResponse>> getBatchesExpiringInDays(
            @RequestParam int days) {

        return ResponseEntity.ok(batchService.getBatchesExpiringInDays(days));
    }
}
