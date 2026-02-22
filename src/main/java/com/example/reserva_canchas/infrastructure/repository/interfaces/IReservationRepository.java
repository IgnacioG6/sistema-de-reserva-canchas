package com.example.reserva_canchas.infrastructure.repository.interfaces;

import com.example.reserva_canchas.infrastructure.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findByUserId(Long userId);
    List<ReservationEntity> findByDate(LocalDate date);

    List<ReservationEntity> findByField_IdAndDate(Long fieldId, LocalDate date);

    @Query("SELECT r FROM ReservationEntity r WHERE r.field.location.id = :locationId")
    List<ReservationEntity> findAllByLocationId(@Param("locationId") Long locationId);
}
