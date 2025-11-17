package com.bankingsystem.service;

import com.bankingsystem.exception.AccountNotFoundException;
import com.bankingsystem.exception.InsufficientBalanceException;
import com.bankingsystem.exception.InvalidAmountException;
import com.bankingsystem.model.Account;
import com.bankingsystem.model.Transaction;
import com.bankingsystem.repository.AccountRepository;
import com.bankingsystem.repository.TransactionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // enable Mockito
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setup() {
        account = new Account("RAJ1234", "Raju");
        account.setCreatedAt(Instant.now());
    }

    @Test
    void testGetAccount_Success() {
        when(accountRepository.findByAccountNumber("RAJ1234"))
                .thenReturn(Optional.of(account));

        Account result = accountService.getAccount("RAJ1234");

        assertEquals("Raju", result.getHolderName());
        verify(accountRepository, times(1)).findByAccountNumber("RAJ1234");
    }

    @Test
    void testGetAccount_NotFound() {
        when(accountRepository.findByAccountNumber("XYZ0000"))
                .thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                () -> accountService.getAccount("XYZ0000"));
    }

    @Test
    void testDeposit_Success() {
        when(accountRepository.findByAccountNumber("RAJ1234"))
                .thenReturn(Optional.of(account));

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // return same Transaction object created by service
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction txn = accountService.deposit("RAJ1234", 500);

        assertEquals(500, account.getBalance());
        assertEquals("SUCCESS", txn.getStatus());
    }

    @Test
    void testDeposit_InvalidAmount() {
        assertThrows(InvalidAmountException.class,
                () -> accountService.deposit("RAJ1234", -10));
    }

    @Test
    void testWithdraw_Success() {
        account.setBalance(1000);

        when(accountRepository.findByAccountNumber("RAJ1234"))
                .thenReturn(Optional.of(account));

        //  return same Transaction object
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction txn = accountService.withdraw("RAJ1234", 300);

        assertEquals(700, account.getBalance());
        assertEquals("SUCCESS", txn.getStatus());
    }

    @Test
    void testWithdraw_InsufficientBalance() {
        account.setBalance(100);

        when(accountRepository.findByAccountNumber("RAJ1234"))
                .thenReturn(Optional.of(account));

        assertThrows(InsufficientBalanceException.class,
                () -> accountService.withdraw("RAJ1234", 200));
    }

    @Test
    void testTransfer_Success() {
        Account source = new Account("SRC1234", "Source");
        Account dest = new Account("DST4321", "Dest");

        source.setBalance(1000);
        dest.setBalance(200);

        when(accountRepository.findByAccountNumber("SRC1234"))
                .thenReturn(Optional.of(source));

        when(accountRepository.findByAccountNumber("DST4321"))
                .thenReturn(Optional.of(dest));

        // FIX: return same Transaction object created by service
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction txn = accountService.transfer("SRC1234", "DST4321", 300);

        assertEquals(700, source.getBalance());
        assertEquals(500, dest.getBalance());
        assertEquals("SUCCESS", txn.getStatus());
    }
}
