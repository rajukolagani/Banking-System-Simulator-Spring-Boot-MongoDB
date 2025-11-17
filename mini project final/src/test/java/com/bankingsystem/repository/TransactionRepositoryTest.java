package com.bankingsystem.repository;

import com.bankingsystem.model.Transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest // Mongo repo test
public class TransactionRepositoryTest {


    @Autowired
    private TransactionRepository transactionRepository; // repo

    @Test
    void testFindBySourceOrDestination() {
        Transaction txn = new Transaction(
                "TXN123",       
                "TRANSFER",     
                500,            
                "SUCCESS",      
                "ACC100",      
                "ACC200"        
        );
        txn.setTimestamp(Instant.now()); 

        transactionRepository.save(txn); 

        List<Transaction> results =
                transactionRepository.findBySourceAccountOrDestinationAccount(
                        "ACC100", "ACC100"); // test query

        assertFalse(results.isEmpty()); // found
        assertEquals("ACC100", results.get(0).getSourceAccount()); // matches
    }
}
