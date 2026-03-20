package com.example.reserva_canchas.domain.exception;

public class PasswordNotMatch extends RuntimeException {
    public PasswordNotMatch(String message) {
        super(message);
    }
}
