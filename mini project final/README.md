# Banking System Simulator (Spring Boot + MongoDB)

## Overview
A simple Spring Boot monolithic backend simulating basic banking operations: account management and transactions.
Built with Spring Boot, Spring Data MongoDB.

## Requirements
- Java 17+
- Maven
- MongoDB (running on localhost:27017 or update application.properties)

## Run
1. Start MongoDB (e.g., `mongod`).
2. Build: `mvn clean package`
3. Run: `mvn spring-boot:run` or `java -jar target/banking-system-simulator-0.0.1-SNAPSHOT.jar`

## Endpoints
- POST `/api/accounts` -> create account. Body: `{ "holderName": "John Doe" }`
- GET `/api/accounts/{accountNumber}` -> get account
- PUT `/api/accounts/{accountNumber}/deposit` -> deposit. Body: `{ "amount": 1000 }`
- PUT `/api/accounts/{accountNumber}/withdraw` -> withdraw. Body: `{ "amount": 100 }`
- POST `/api/accounts/transfer` -> transfer. Body: `{ "sourceAccount":"ABC1234","destinationAccount":"DEF5678","amount":200 }`
- GET `/api/accounts/{accountNumber}/transactions` -> list transactions

## Notes
- Transactions stored in `transactions` collection.
- Accounts stored in `accounts` collection.
