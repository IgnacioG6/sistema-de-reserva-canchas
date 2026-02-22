package com.example.reserva_canchas.application.query;

import com.example.reserva_canchas.domain.exception.ReservationNotFoundException;
import com.example.reserva_canchas.domain.model.Reservation;
import com.example.reserva_canchas.domain.port.in.reservation.GetReservationsUseCase;
import com.example.reserva_canchas.domain.port.out.ReservationRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetReservationService implements GetReservationsUseCase {
    private final ReservationRepositoryPort reservationRepositoryPort;

    public GetReservationService(ReservationRepositoryPort reservationRepositoryPort) {
        this.reservationRepositoryPort = reservationRepositoryPort;
    }

    @Override
    public List<Reservation> getReservations() {
        return reservationRepositoryPort.findAll();
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepositoryPort.findById(id)
                .orElseThrow(()-> new ReservationNotFoundException(id));
    }

    @Override
    public List<Reservation> getReservationsByUserId(Long id) {
        return reservationRepositoryPort.findByUserId(id);
    }

    @Override
    public List<Reservation> getReservationsByDate(LocalDate date) {
        return reservationRepositoryPort.findByDate(date);
    }

    @Override
    public List<Reservation> getReservationsByFieldAndDate(Long fieldId, LocalDate date) {
        return reservationRepositoryPort.findByFieldIdAndDate(fieldId, date);
    }

    @Override
    public List<Reservation> getReservationsByLocationId(Long locationId) {
        return reservationRepositoryPort.getReservationsByLocationId(locationId);
    }
}
