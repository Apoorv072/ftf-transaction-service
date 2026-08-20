package com.ftf.transaction_service.dto;

import com.ftf.transaction_service.entity.TransactionStatus;
import com.ftf.transaction_service.entity.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {

    private Long id;
    private String transactionReference;

    private Long sourceAccountId;
    private Long destinationAccountId;

    private BigDecimal amount;
    private String currency;

    private TransactionType transactionType;
    private TransactionStatus status;

    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;

    // getters and setters
}