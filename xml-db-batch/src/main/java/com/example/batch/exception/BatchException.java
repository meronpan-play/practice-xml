package com.example.batch.exception;

public class BatchException extends Exception {

    public BatchException(String message) {
        super(message);
    }

    public BatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
