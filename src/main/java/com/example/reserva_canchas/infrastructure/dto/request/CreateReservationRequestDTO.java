package com.example.reserva_canchas.infrastructure.dto.request;

import com.example.reserva_canchas.domain.model.enums.ReservationDuration;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationRequestDTO(

    Long userId,
    Long fieldId,
    LocalDate date,
    LocalTime startTime,
    ReservationDuration duration
) {
}
