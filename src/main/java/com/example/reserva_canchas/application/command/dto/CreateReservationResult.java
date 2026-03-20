package com.example.reserva_canchas.application.command.dto;

import com.example.reserva_canchas.domain.model.Reservation;

public record CreateReservationResult(
        Reservation reservation,
        String paymentUrl
) {}