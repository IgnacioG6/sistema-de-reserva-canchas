package com.example.reserva_canchas.domain.exception;

public class ReservationPastDate extends RuntimeException {
    public ReservationPastDate(String message) {
        super(message);
    }
}
