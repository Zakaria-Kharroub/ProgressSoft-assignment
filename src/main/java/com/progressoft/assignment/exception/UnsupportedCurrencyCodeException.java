package com.progressoft.assignment.exception;

public class UnsupportedCurrencyCodeException extends RuntimeException {
    public UnsupportedCurrencyCodeException(String message) {
        super(message);
    }
}