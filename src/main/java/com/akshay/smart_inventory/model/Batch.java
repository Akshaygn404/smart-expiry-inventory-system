package com.akshay.smart_inventory.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "batches",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"batchNumber", "product_id"})
        }
)
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String batchNumber;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal costPrice;

    @Column(nullable = false)
    private LocalDate receivedDate;

    private String supplierName;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}