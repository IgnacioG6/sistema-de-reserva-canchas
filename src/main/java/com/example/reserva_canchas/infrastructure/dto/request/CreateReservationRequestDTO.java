package com.example.reserva_canchas.infrastructure.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationRequestDTO(

    Long userId,
    Long fieldId,
    LocalDate date,
    LocalTime startTime,
    LocalTime endTime
) {
}
