package com.example.reserva_canchas.infrastructure.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponseDTO(

        String name,
        String fieldName,
        String typeField,
        LocalTime startTime,
        LocalDate date,
        BigDecimal price


) {
}
