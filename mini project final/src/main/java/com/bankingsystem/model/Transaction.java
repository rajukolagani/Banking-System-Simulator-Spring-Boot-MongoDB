package com.bankingsystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    private String transactionId; // e.g. TXN-20251107-001
    private String type; // DEPOSIT, WITHDRAW, TRANSFER
    private double amount;
    private Instant timestamp;
    private String status; // SUCCESS, FAILED
    private String sourceAccount;
    private String destinationAccount;

    public Transaction() {} //Default constructor used 

    public Transaction(String transactionId, String type, double amount, String status, String sourceAccount, String destinationAccount) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.timestamp = Instant.now();
        this.status = status;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
    }

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getSourceAccount() { return sourceAccount; }
    public void setSourceAccount(String sourceAccount) { this.sourceAccount = sourceAccount; }
    public String getDestinationAccount() { return destinationAccount; }
    public void setDestinationAccount(String destinationAccount) { this.destinationAccount = destinationAccount; }
}
