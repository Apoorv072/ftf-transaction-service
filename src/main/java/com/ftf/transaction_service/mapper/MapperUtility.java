package com.ftf.transaction_service.mapper;
import com.ftf.transaction_service.dto.TransactionResponse;
import com.ftf.transaction_service.entity.Transaction;

public final class MapperUtility {

    private MapperUtility() {
    }

    public static TransactionResponse mapToTransactionResponse(
            Transaction transaction) {

        TransactionResponse response = new TransactionResponse();

        response.setId(transaction.getId());
        response.setTransactionReference(
                transaction.getTransactionReference()
        );
        response.setSourceAccountId(
                transaction.getSourceAccountId()
        );
        response.setDestinationAccountId(
                transaction.getDestinationAccountId()
        );
        response.setAmount(transaction.getAmount());
        response.setCurrency(transaction.getCurrency());
        response.setTransactionType(
                transaction.getTransactionType()
        );
        response.setStatus(transaction.getStatus());
        response.setDescription(transaction.getDescription());
        response.setCreatedAt(transaction.getCreatedAt());
        response.setUpdatedAt(transaction.getUpdatedAt());
        response.setCompletedAt(transaction.getCompletedAt());

        return response;
    }
}