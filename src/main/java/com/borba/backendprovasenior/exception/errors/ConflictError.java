package com.borba.backendprovasenior.exception.errors;

public class ConflictError extends RuntimeException {
    public ConflictError(String message) {
        super(message);
    }
}
