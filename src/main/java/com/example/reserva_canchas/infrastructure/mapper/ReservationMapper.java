package com.example.reserva_canchas.infrastructure.mapper;

import com.example.reserva_canchas.domain.model.Reservation;
import com.example.reserva_canchas.infrastructure.dto.response.ReservationResponseDTO;
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
                reservation.getDuration(),
                reservation.getStatus(),
                reservation.getPriceTotal(),
                reservation.getDateCreation());
    }

    public static Reservation toDomain(ReservationEntity entity) {

        if(entity == null) return null;

        Reservation reservation = new Reservation(UserMapper.toDomain(entity.getUser()),
                FieldMapper.toDomain(entity.getField()), entity.getDate(),
                entity.getStartTime(), entity.getPriceTotal(), entity.getDuration());
        reservation.assignId(entity.getId());

        return reservation;
    }

    public static ReservationResponseDTO  toResponse(Reservation reservation) {
        if(reservation == null) return null;

        return new ReservationResponseDTO(
                reservation.getId(),
                reservation.getUser().getName(),
                reservation.getField().getName(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getDuration().toString(),
                reservation.getDate(),
                reservation.getPriceTotal(),
                reservation.getStatus().toString()
        );
    }


}
