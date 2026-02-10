package com.example.reserva_canchas.domain.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Long id) {
        super("Reservation not found with id: " + id);
    }
}
