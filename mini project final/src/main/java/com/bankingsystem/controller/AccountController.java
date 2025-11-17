package com.bankingsystem.controller;

import com.bankingsystem.model.Account;
import com.bankingsystem.model.Transaction;
import com.bankingsystem.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
//REST API + CSR Architecture
@RestController
@RequestMapping("/api/accounts")                                                            
@Validated
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Map<String, String> body) {
        String holderName = body.get("holderName");
        Account created = accountService.createAccount(holderName);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccount(accountNumber));
    }

    @PutMapping("/{accountNumber}/deposit")
    public ResponseEntity<Transaction> deposit(@PathVariable String accountNumber, @RequestBody Map<String, Object> body) {
        double amount = Double.parseDouble(body.get("amount").toString());
        Transaction txn = accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok(txn);
    }

    @PutMapping("/{accountNumber}/withdraw")
    public ResponseEntity<Transaction> withdraw(@PathVariable String accountNumber, @RequestBody Map<String, Object> body) {
        double amount = Double.parseDouble(body.get("amount").toString());
        Transaction txn = accountService.withdraw(accountNumber, amount);
        return ResponseEntity.ok(txn);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestBody Map<String, Object> body) {
        String source = body.get("sourceAccount").toString();
        String destination = body.get("destinationAccount").toString();
        double amount = Double.parseDouble(body.get("amount").toString());
        Transaction txn = accountService.transfer(source, destination, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(txn);
    }

    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getTransactionsForAccount(accountNumber));
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<Account> updateAccount(@PathVariable String accountNumber, @RequestBody Map<String, String> body) {
        String holderName = body.get("holderName");
        return ResponseEntity.ok(accountService.updateAccount(accountNumber, holderName));
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<?> deleteAccount(@PathVariable String accountNumber) {
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.noContent().build();
    }
}
