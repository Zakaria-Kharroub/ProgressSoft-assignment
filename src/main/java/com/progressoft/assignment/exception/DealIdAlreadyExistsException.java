package com.progressoft.assignment.exception;

public class DealIdAlreadyExistsException extends RuntimeException {
    public DealIdAlreadyExistsException(String message) {
        super(message);
    }
}