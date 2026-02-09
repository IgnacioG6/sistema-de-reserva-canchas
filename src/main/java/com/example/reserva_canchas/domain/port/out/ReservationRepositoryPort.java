package com.example.reserva_canchas.domain.port.out;

import com.example.reserva_canchas.domain.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepositoryPort {
    void save(Reservation reservation);
    Optional<Reservation> findById(Long id);
    List<Reservation> findAll();
    void delete(Long id);
}

