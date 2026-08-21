package com.ftf.transaction_service.dto;

import com.ftf.transaction_service.entity.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {

    @NotBlank(message = "Source account number is required")
    private String sourceAccountNumber;

    @NotBlank(message = "Destination account number is required")
    private String destinationAccountNumber;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    @Size(min = 3,max = 3, message = "Currency must be 3 characters")
    private String currency;

    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

}
