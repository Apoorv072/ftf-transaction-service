package com.ftf.transaction_service.client.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LimitReservationRequest {

    private Long accountId;
    private BigDecimal amount;
    private String currency;
    private String transactionReference;

}
