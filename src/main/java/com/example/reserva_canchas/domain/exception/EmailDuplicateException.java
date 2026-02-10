package com.example.reserva_canchas.domain.exception;

public class EmailDuplicateException extends RuntimeException {
    public EmailDuplicateException(String message) {
        super(message);
    }
}
