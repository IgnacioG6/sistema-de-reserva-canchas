package com.example.reserva_canchas.application.command.update;

import com.example.reserva_canchas.domain.exception.ReservationNotFoundException;
import com.example.reserva_canchas.domain.model.Reservation;
import com.example.reserva_canchas.domain.port.in.reservation.CancelReservationUseCase;
import com.example.reserva_canchas.domain.port.out.ReservationRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class CancelReservationService implements CancelReservationUseCase {
    private final ReservationRepositoryPort reservationRepositoryPort;

    public CancelReservationService(ReservationRepositoryPort reservationRepositoryPort) {
        this.reservationRepositoryPort = reservationRepositoryPort;
    }

    @Override
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepositoryPort.findById(id)
                .orElseThrow(()-> new ReservationNotFoundException(id));

        reservation.cancel();

        reservationRepositoryPort.save(reservation);
    }
}
