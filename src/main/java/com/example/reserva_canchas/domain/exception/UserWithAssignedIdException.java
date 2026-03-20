package com.example.reserva_canchas.domain.exception;

public class UserWithAssignedIdException extends RuntimeException {
    public UserWithAssignedIdException(String message) {
        super(message);
    }
}
