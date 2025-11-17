package com.bankingsystem.service;

import com.bankingsystem.exception.AccountNotFoundException;
import com.bankingsystem.exception.InvalidAmountException;
import com.bankingsystem.exception.InsufficientBalanceException;
import com.bankingsystem.model.Account;
import com.bankingsystem.model.Transaction;
import com.bankingsystem.repository.AccountRepository;
import com.bankingsystem.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
          //unique account numbers
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    private String generateAccountNumber(String holderName) {
        String initials = holderName.replaceAll("[^A-Za-z]", "");
        initials = initials.length() >= 3 ? initials.substring(0,3).toUpperCase() : initials.toUpperCase();
        int rand = (int)(Math.random() * 9000) + 1000;
        return initials + rand;
    }

    private String nextTxnId() {
        String ts = Instant.now().toString().replaceAll("[:\\.TZ]", "");
        if (ts.length() > 14) ts = ts.substring(0,14);
        return "TXN-" + ts + "-" + UUID.randomUUID().toString().substring(0,4);
    }

    public Account createAccount(String holderName) {
        if (holderName == null || holderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Holder name cannot be empty"); //Input Validation
        }
        String accNo = generateAccountNumber(holderName);
        while (accountRepository.existsByAccountNumber(accNo)) {
            accNo = generateAccountNumber(holderName);
        }
        Account acc = new Account(accNo, holderName);
        acc.setCreatedAt(Instant.now());
        return accountRepository.save(acc);
    }

    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));
    }

    public Account updateAccount(String accountNumber, String newHolderName) {
        Account acc = getAccount(accountNumber);
        if (newHolderName != null && !newHolderName.trim().isEmpty()) {
            acc.setHolderName(newHolderName);
        }
        return accountRepository.save(acc);
    }

    public void deleteAccount(String accountNumber) {
        Account acc = getAccount(accountNumber);
        accountRepository.delete(acc);
    }

    public Transaction deposit(String accountNumber, double amount) {
        if (amount <= 0) throw new InvalidAmountException("Deposit amount must be > 0");
        Account acc = getAccount(accountNumber);
        acc.setBalance(acc.getBalance() + amount);
        accountRepository.save(acc);

        Transaction txn = new Transaction(nextTxnId(), "DEPOSIT", amount, "SUCCESS", null, accountNumber);
        return transactionRepository.save(txn);
    }

    public Transaction withdraw(String accountNumber, double amount) {
        if (amount <= 0) throw new InvalidAmountException("Withdraw amount must be > 0"); //Custom Exception
        Account acc = getAccount(accountNumber);
        if (acc.getBalance() < amount) throw new InsufficientBalanceException("Insufficient balance");
        acc.setBalance(acc.getBalance() - amount);
        accountRepository.save(acc);

        Transaction txn = new Transaction(nextTxnId(), "WITHDRAW", amount, "SUCCESS", accountNumber, null);
        return transactionRepository.save(txn);
    }

    public Transaction transfer(String fromAcc, String toAcc, double amount) {
        if (amount <= 0) throw new InvalidAmountException("Transfer amount must be > 0");
        if (fromAcc.equals(toAcc)) throw new IllegalArgumentException("Source and destination must differ");

        Account src = getAccount(fromAcc);
        Account dst = getAccount(toAcc);

        if (src.getBalance() < amount) throw new InsufficientBalanceException("Insufficient balance in source account");

        src.setBalance(src.getBalance() - amount);
        dst.setBalance(dst.getBalance() + amount);

        accountRepository.save(src);
        accountRepository.save(dst);

        Transaction txn = new Transaction(nextTxnId(), "TRANSFER", amount, "SUCCESS", fromAcc, toAcc);
        return transactionRepository.save(txn);
    }
//gets all transction history for account
    public List<Transaction> getTransactionsForAccount(String accountNumber) {
        return transactionRepository.findBySourceAccountOrDestinationAccount(accountNumber, accountNumber);
    }
}
