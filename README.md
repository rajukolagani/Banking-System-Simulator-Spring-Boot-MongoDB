**Banking System Simulator – Spring Boot + MongoDB**
A mini-project simulating a real-world Banking Service Application using Spring Boot, REST APIs, and MongoDB.
This application implements core banking operations such as account creation, deposit, withdrawal, transfer, and transaction history while following a clean layered architecture.

 Features
 Account Management
Create new bank accounts
Auto-generate account numbers (Name initials + 4-digit random number)
Update account holder name
Fetch account details
Delete account
Input validation included
Transactions
Deposit funds
Withdraw funds (with validation)
Transfer funds between accounts
Unique transaction ID generation
Store transaction metadata
Linked to accounts

Persistence
Uses Spring Data MongoDB

Collections:
accounts
transactions

Project Structure
com.bankingsystem
│── controller        # REST Controllers
│── service           # Business Logic
│── repository        # Mongo Repositories
│── model             # MongoDB Document Models
│── exception         # Custom Exceptions + Global Handler
│── config            # (Optional) Config files
└── BankingSystemApplication.java

 REST API Endpoints
 MongoDB Collections
accounts
{
  "_id": "674ff37dca123e48f9b2d5ab",
  "accountNumber": "RAJ1234",
  "holderName": "Raju",
  "balance": 12000,
  "status": "ACTIVE",
  "createdAt": "2025-11-07T09:30:00Z"
}

transactions
{
  "_id": "674ff3aeca123e48f9b2d5ac",
  "transactionId": "TXN-20251107-001",
  "type": "DEPOSIT",
  "amount": 500,
  "timestamp": "2025-11-07T09:32:10Z",
  "status": "SUCCESS",
  "sourceAccount": null,
  "destinationAccount": "RAJ1234"
}

Tech Stack
Component	Technology
Backend	Spring Boot
Database	MongoDB
Build Tool	Maven
ORM Layer	Spring Data MongoDB
Validation	Jakarta Bean Validation
Testing	JUnit 5, Mockito
API Testing	Postman
Testing (JUnit 5 + Mockito)
Unit Tests Included:
Service Layer
getAccount
deposit
withdraw
transfer
Failed scenarios (invalid amount, insufficient balance, account not found)
Repository Layer
Custom finder test for transaction
