package com.akshay.smart_inventory.service.sale;

import com.akshay.smart_inventory.dto.request.SaleRequest;
import com.akshay.smart_inventory.dto.response.SaleResponse;
import com.akshay.smart_inventory.exception.ResourceNotFoundException;
import com.akshay.smart_inventory.model.Batch;
import com.akshay.smart_inventory.model.Product;
import com.akshay.smart_inventory.model.Sale;
import com.akshay.smart_inventory.model.SaleItem;
import com.akshay.smart_inventory.repository.BatchRepository;
import com.akshay.smart_inventory.repository.ProductRepository;
import com.akshay.smart_inventory.repository.SaleRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService implements ISaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final BatchRepository batchRepository;

    @Override
    @Transactional
    public SaleResponse createSale(SaleRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        List<Batch> batches =
                batchRepository.findByProductIdOrderByExpiryDateAsc(product.getId());

        if (batches.isEmpty()) {
            throw new RuntimeException("No stock available for this product");
        }

        int remainingQuantity = request.getQuantity();

        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setSaleDate(LocalDateTime.now());
        sale.setTotalQuantity(request.getQuantity());

        List<SaleItem> saleItems = new ArrayList<>();

        for (Batch batch : batches) {

            if (remainingQuantity <= 0) break;

            // Skip expired
            if (batch.getExpiryDate().isBefore(LocalDate.now())) continue;

            // Skip empty
            if (batch.getQuantity() <= 0) continue;

            int quantityFromThisBatch;

            if (batch.getQuantity() >= remainingQuantity) {
                quantityFromThisBatch = remainingQuantity;
            } else {
                quantityFromThisBatch = batch.getQuantity();
            }

            batch.setQuantity(batch.getQuantity() - quantityFromThisBatch);
            batchRepository.save(batch);

            SaleItem saleItem = new SaleItem();
            saleItem.setBatch(batch);
            saleItem.setQuantityTaken(quantityFromThisBatch);
            saleItem.setSale(sale);

            saleItems.add(saleItem);

            remainingQuantity -= quantityFromThisBatch;
            System.out.println("Processing batch: " + batch.getBatchNumber());
        }

        if (remainingQuantity > 0) {
            throw new RuntimeException("Insufficient stock available");
        }

        sale.setSaleItems(saleItems);

        Sale savedSale = saleRepository.save(sale);

        return mapToResponse(savedSale);
    }

    private SaleResponse mapToResponse(Sale sale) {

        SaleResponse response = new SaleResponse();
        response.setSaleId(sale.getId());
        response.setProductId(sale.getProduct().getId());
        response.setProductName(sale.getProduct().getName());
        response.setTotalQuantity(sale.getTotalQuantity());
        response.setSaleDate(sale.getSaleDate());

        return response;
    }
}