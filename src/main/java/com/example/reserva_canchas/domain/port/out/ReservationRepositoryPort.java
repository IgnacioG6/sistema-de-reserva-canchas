package com.example.reserva_canchas.domain.port.out;

import com.example.reserva_canchas.domain.model.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepositoryPort {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long id);
    List<Reservation> findAll();
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByDate(LocalDate date);
    List<Reservation> findByFieldIdAndDate(Long fieldId, LocalDate date);
}

