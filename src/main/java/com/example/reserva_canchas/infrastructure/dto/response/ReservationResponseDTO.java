package com.example.reserva_canchas.infrastructure.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponseDTO(

        Long id,
        String name,
        String fieldName,
        LocalTime startTime,
        LocalTime endTime,
        String duration,
        LocalDate date,
        BigDecimal price,
        String status


) {
}
