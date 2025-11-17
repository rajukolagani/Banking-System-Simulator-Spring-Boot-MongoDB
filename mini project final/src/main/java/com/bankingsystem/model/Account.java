package com.bankingsystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "accounts")
public class Account {
    @Id
    private String id;
    private String accountNumber;
    private String holderName;
    private double balance;
    private String status; // ACTIVE / INACTIVE
    private Instant createdAt;

    public Account() {}   //Constructor used for creating account

    public Account(String accountNumber, String holderName) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = 0.0;
        this.status = "ACTIVE";
        this.createdAt = Instant.now();
    }

    // getters and setters encapsulation
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
