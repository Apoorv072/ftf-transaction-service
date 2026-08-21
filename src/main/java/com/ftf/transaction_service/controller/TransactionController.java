package com.ftf.transaction_service.controller;

import com.ftf.transaction_service.client.AccountServiceClient;
import com.ftf.transaction_service.client.dto.AccountResponse;
import com.ftf.transaction_service.dto.TransactionRequest;
import com.ftf.transaction_service.dto.TransactionResponse;
import com.ftf.transaction_service.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountServiceClient accountServiceClient;
    public TransactionController(TransactionService transactionService, AccountServiceClient accountServiceClient) {
        this.transactionService = transactionService;
        this.accountServiceClient=accountServiceClient;
    }

//    @PostMapping
//    public ResponseEntity<TransactionResponse> createTransaction(
//            @Valid @RequestBody TransactionRequest request) {
//
//        TransactionResponse response =
//                transactionService.createTransaction(request);
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(response);
//    }


    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestHeader("Idempotency-Key") String idempotencyKey, @Valid @RequestBody TransactionRequest request) {
        System.out.println("flag 1");
        TransactionResponse response = transactionService.createTransaction(request, idempotencyKey);
        System.out.println("flag 2");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping("/test-account/{accountNumber}")
    public ResponseEntity<AccountResponse> testAccount(@PathVariable String accountNumber) {

        return ResponseEntity.ok(accountServiceClient.getAccountByNumber(accountNumber)
        );
    }
}