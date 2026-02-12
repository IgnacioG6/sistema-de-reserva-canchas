package com.example.reserva_canchas.infrastructure.repository.interfaces;

import com.example.reserva_canchas.infrastructure.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILocationRepository extends JpaRepository<LocationEntity, Long> {
}
