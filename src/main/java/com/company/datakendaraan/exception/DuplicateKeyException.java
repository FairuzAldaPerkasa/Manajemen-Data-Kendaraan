package com.company.datakendaraan.exception;

public class DuplicateKeyException extends RuntimeException {
    public DuplicateKeyException(String message) {
        super(message);
    }
}
