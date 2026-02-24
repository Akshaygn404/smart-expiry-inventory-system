package com.akshay.smart_inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AlertType type;

    private String message;

    @Enumerated(EnumType.STRING)
    private SeverityLevel severity;

    private LocalDateTime createdAt;

    private boolean resolved = false;
}