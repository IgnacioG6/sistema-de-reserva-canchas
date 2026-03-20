package com.example.reserva_canchas.domain.exception;

public class ReservationAlreadyConfirmedException extends RuntimeException {
    public ReservationAlreadyConfirmedException(String message) {
        super(message);
    }
}
