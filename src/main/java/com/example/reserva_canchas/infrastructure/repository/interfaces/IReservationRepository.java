package com.example.reserva_canchas.infrastructure.repository.interfaces;

import com.example.reserva_canchas.infrastructure.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservationRepository extends JpaRepository<ReservationEntity, Long> {
}
