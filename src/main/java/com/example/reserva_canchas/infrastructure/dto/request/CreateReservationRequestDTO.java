package com.example.reserva_canchas.infrastructure.dto;

import java.time.LocalDate;

public record CreateReservationRequestDTO(

    Long userId,
    Long fieldId,
    LocalDate date,


) {
}
