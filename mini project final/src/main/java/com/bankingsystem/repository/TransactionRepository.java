package com.bankingsystem.repository;

import com.bankingsystem.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
//Transaction History
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findBySourceAccountOrDestinationAccount(String source, String destination);
}
