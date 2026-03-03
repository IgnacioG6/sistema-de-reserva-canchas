package com.example.reserva_canchas.infrastructure.security;

import com.example.reserva_canchas.domain.model.Reservation;
import com.example.reserva_canchas.domain.port.in.reservation.GetReservationsUseCase;
import org.springframework.stereotype.Service;

@Service("reservationSecurity")
public class ReservationSecurityService {

    private final GetReservationsUseCase getReservationsUseCase;

    public ReservationSecurityService(GetReservationsUseCase getReservationsUseCase) {
        this.getReservationsUseCase = getReservationsUseCase;
    }

    public boolean isOwner(Long reservationId, Long userId) {
        Reservation reservation = getReservationsUseCase.getReservationById(reservationId);
        return reservation.getUser().getId().equals(userId);
    }
}