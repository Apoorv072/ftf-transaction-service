package com.ftf.transaction_service.service;

import com.ftf.transaction_service.dto.TransactionRequest;
import com.ftf.transaction_service.dto.TransactionResponse;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request, String idempotencyKey);
}