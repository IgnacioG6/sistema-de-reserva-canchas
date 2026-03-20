package com.example.reserva_canchas.domain.exception;

public class ReservationOnlyChangeWithPendingStatus extends RuntimeException {
    public ReservationOnlyChangeWithPendingStatus(String message) {
        super(message);
    }
}
