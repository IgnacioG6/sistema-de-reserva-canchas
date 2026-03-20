package com.example.reserva_canchas.application.command.dto;

import com.example.reserva_canchas.domain.model.enums.ReservationDuration;


import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationCommand(
        Long userId,
        Long fieldId,
        LocalDate date,
        LocalTime startTime,
        ReservationDuration duration
) {
}
