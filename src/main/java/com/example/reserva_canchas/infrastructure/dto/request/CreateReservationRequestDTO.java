package com.example.reserva_canchas.infrastructure.dto.request;

import com.example.reserva_canchas.domain.model.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record CreateReservationRequestDTO(

    Long userId,
    Long fieldId,
    LocalDate date,
    LocalTime startTime,
    LocalTime endTime,
    BigDecimal priceTotal

) {
}
