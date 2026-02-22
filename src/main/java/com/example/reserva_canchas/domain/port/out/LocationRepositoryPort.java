package com.example.reserva_canchas.domain.port.out;

import com.example.reserva_canchas.domain.model.Location;
import com.example.reserva_canchas.domain.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface LocationRepositoryPort {
    Location save(Location location);
    Optional<Location> findById(Long id);
    List<Location> findAll();
}
