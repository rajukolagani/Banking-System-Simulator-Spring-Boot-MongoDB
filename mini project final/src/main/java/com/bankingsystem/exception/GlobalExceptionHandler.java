package com.bankingsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice             //Global error
public class GlobalExceptionHandler {
                                    //Input Validation + Custom Exception Handling
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleAccountNotFound(AccountNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("error", "NOT_FOUND");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler({InvalidAmountException.class, InsufficientBalanceException.class})
    public ResponseEntity<?> handleBadRequest(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());               //Validates balance
        body.put("error", "BAD_REQUEST");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("error", "INTERNAL_ERROR");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
