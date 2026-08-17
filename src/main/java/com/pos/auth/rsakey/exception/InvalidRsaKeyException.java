package com.pos.auth.rsakey.exception;

public class InvalidRsaKeyException extends RuntimeException {
    public InvalidRsaKeyException(String message) {
        super(message);
    }

    public InvalidRsaKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
