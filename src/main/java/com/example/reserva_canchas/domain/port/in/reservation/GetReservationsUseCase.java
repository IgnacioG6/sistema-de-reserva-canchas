package com.example.reserva_canchas.domain.port.in.reservation;

import com.example.reserva_canchas.domain.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface GetReservationsUseCase {

    List<Reservation> getReservations();
    Reservation getReservationById(Long id);
    List<Reservation>  getReservationsByUserId(Long id);
    List<Reservation> getReservationsByDate(LocalDate date);
    List<Reservation> getReservationsByFieldAndDate(Long fieldId, LocalDate date);
    List<Reservation> getReservationsByLocationId(Long locationId);

}
