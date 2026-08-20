package com.ftf.transaction_service.repository;

import com.ftf.transaction_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionReference(
            String transactionReference
    );

    boolean existsByTransactionReference(
            String transactionReference
    );
}
