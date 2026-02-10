package com.example.reserva_canchas.domain.port.in.reservation;

import com.example.reserva_canchas.domain.model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public interface CreateReservationUseCase {

    Reservation create(Long userId, Long fieldId, LocalDate date, LocalTime startTime, LocalTime endTime, BigDecimal price);

}