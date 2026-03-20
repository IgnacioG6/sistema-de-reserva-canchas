package com.example.reserva_canchas.domain.exception;

public class ReservationWithAssignedIdException extends RuntimeException {
    public ReservationWithAssignedIdException(String message) {
        super(message);
    }
}
