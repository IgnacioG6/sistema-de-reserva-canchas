package com.example.reserva_canchas.domain.exception;

public class PriceInvalid extends RuntimeException {
    public PriceInvalid(String message) {
        super(message);
    }
}
