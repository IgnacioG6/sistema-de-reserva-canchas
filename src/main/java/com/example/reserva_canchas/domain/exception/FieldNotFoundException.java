package com.example.reserva_canchas.domain.exception;

public class FieldNotFoundException extends RuntimeException {
    public FieldNotFoundException(Long id) {
        super("Field not found with id: " + id);
    }
}
