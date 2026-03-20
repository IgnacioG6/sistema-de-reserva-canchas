package com.example.reserva_canchas.domain.exception;

public class FieldWithAssignedIdException extends RuntimeException {
    public FieldWithAssignedIdException(String message) {
        super(message);
    }
}
