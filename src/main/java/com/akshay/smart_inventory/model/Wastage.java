package com.akshay.smart_inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wastage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private WastageReason reason;

    private BigDecimal totalLossAmount;

    private LocalDateTime recordedAt;
}