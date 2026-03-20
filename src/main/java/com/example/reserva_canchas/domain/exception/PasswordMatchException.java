package com.example.reserva_canchas.domain.exception;

public class PasswordMatchException extends RuntimeException {
    public PasswordMatchException(String message) {
        super(message);
    }
}
