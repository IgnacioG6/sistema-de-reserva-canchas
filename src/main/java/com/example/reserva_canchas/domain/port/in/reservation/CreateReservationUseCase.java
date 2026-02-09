package com.example.reserva_canchas.domain.port.in.reservation;

import com.example.reserva_canchas.domain.model.Reservation;

public interface CreateReservationUseCase {

    Reservation createReservation(Reservation reservation);
}
