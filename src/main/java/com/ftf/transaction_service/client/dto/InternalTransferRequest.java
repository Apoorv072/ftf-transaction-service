package com.ftf.transaction_service.client.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InternalTransferRequest {
    private Long sourceAccountId;
    private Long destinationAccountId;
    private BigDecimal amount;
    private String currency;
    private String transactionReference;
}
