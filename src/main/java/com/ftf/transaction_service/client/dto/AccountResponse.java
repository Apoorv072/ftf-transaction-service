package com.ftf.transaction_service.client.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {

    private Long id;
    private String accountNumber;
    private String currency;
    private BigDecimal balance;
    private String status;

}
