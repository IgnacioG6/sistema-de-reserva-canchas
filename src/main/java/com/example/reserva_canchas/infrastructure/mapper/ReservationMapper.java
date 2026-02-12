package com.example.reserva_canchas.infrastructure.mapper;

import com.example.reserva_canchas.domain.model.Reservation;
import com.example.reserva_canchas.infrastructure.entity.ReservationEntity;

public class ReservationMapper {

    public static ReservationEntity toEntity(Reservation reservation) {

        if(reservation == null) return null;


        return new ReservationEntity(
                reservation.getId(),
                UserMapper.toEntity(reservation.getUser()),
                FieldMapper.toEntity(reservation.getField()),
                reservation.getDate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getStatus(),
                reservation.getPriceTotal(),
                reservation.getDateCreation());
    }

    public static Reservation toEntity(ReservationEntity entity) {

        if(entity == null) return null;


        return new Reservation(
                entity.getId(),
                UserMapper.toDomain(entity.getUser()),
                FieldMapper.toDomain(entity.getField()),
                entity.getDate(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getStatus(),
                entity.getPriceTotal(),
                entity.getDateCreation());
    }

}
