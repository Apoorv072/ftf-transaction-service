package com.ftf.transaction_service.service;

import com.ftf.transaction_service.client.AccountServiceClient;
import com.ftf.transaction_service.client.dto.AccountResponse;
import com.ftf.transaction_service.client.dto.InternalTransferRequest;
import com.ftf.transaction_service.dto.TransactionRequest;
import com.ftf.transaction_service.dto.TransactionResponse;
import com.ftf.transaction_service.entity.Transaction;
import com.ftf.transaction_service.entity.TransactionStatus;
import com.ftf.transaction_service.mapper.MapperUtility;
import com.ftf.transaction_service.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountServiceClient accountServiceClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountServiceClient accountServiceClient) {
        this.transactionRepository = transactionRepository;
        this.accountServiceClient = accountServiceClient;
    }

    @Override
    @Transactional
    public TransactionResponse createTransaction(
            TransactionRequest request, String idempotencyKey) {

        // Check whether the transaction is already present

        Optional<Transaction> existingTransaction =
                transactionRepository.findByIdempotencyKey(idempotencyKey);

        if (existingTransaction.isPresent()) {
            return MapperUtility.mapToTransactionResponse(
                    existingTransaction.get()
            );
        }

        AccountResponse sourceAccount =
                accountServiceClient.getAccountByNumber(
                        request.getSourceAccountNumber()
                );

        AccountResponse destinationAccount =
                accountServiceClient.getAccountByNumber(
                        request.getDestinationAccountNumber()
                );

        validateAccounts(
                sourceAccount,
                destinationAccount,
                request
        );

        Transaction transaction = new Transaction();

        transaction.setIdempotencyKey(idempotencyKey);

        transaction.setTransactionReference(
                generateTransactionReference()
        );

        transaction.setSourceAccountId(
                sourceAccount.getId()
        );

        transaction.setDestinationAccountId(
                destinationAccount.getId()
        );

        transaction.setAmount(request.getAmount());

        transaction.setCurrency(
                request.getCurrency().toUpperCase()
        );

        transaction.setTransactionType(
                request.getTransactionType()
        );

        transaction.setStatus(TransactionStatus.PENDING);

        transaction.setDescription(
                request.getDescription()
        );

        LocalDateTime now = LocalDateTime.now();

        transaction.setCreatedAt(now);
        transaction.setUpdatedAt(now);

        Transaction savedTransaction =
                transactionRepository.save(transaction);
       //   Creating internal transfer request and calling account service client
        try {

            InternalTransferRequest transferRequest =
                    new InternalTransferRequest();

            transferRequest.setSourceAccountId(
                    sourceAccount.getId()
            );

            transferRequest.setDestinationAccountId(
                    destinationAccount.getId()
            );

            transferRequest.setAmount(
                    request.getAmount()
            );

            transferRequest.setCurrency(
                    request.getCurrency()
            );

            transferRequest.setTransactionReference(
                    savedTransaction.getTransactionReference()
            );

            accountServiceClient.transfer(transferRequest);

            savedTransaction.setStatus(
                    TransactionStatus.COMPLETED
            );

        } catch (Exception e) {

            savedTransaction.setStatus(
                    TransactionStatus.FAILED
            );
        }

        return MapperUtility.mapToTransactionResponse(
                savedTransaction
        );
    }

    private String generateTransactionReference() {

        return "TXN-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 20)
                        .toUpperCase();
    }
    private void validateAccounts(
            AccountResponse sourceAccount,
            AccountResponse destinationAccount,
            TransactionRequest request) {

        if (!"ACTIVE".equals(sourceAccount.getStatus())) {
            throw new IllegalStateException(
                    "Source account is not active"
            );
        }

        if (!"ACTIVE".equals(destinationAccount.getStatus())) {
            throw new IllegalStateException(
                    "Destination account is not active"
            );
        }

        if (sourceAccount.getId()
                .equals(destinationAccount.getId())) {

            throw new IllegalArgumentException(
                    "Source and destination accounts cannot be the same"
            );
        }

        if (!sourceAccount.getCurrency()
                .equalsIgnoreCase(request.getCurrency())) {

            throw new IllegalArgumentException(
                    "Source account currency does not match transaction currency"
            );
        }

        if (!destinationAccount.getCurrency()
                .equalsIgnoreCase(request.getCurrency())) {

            throw new IllegalArgumentException(
                    "Destination account currency does not match transaction currency"
            );
        }
    }
}