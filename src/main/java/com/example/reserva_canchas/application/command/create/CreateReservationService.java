package com.example.reserva_canchas.application.command.create;

import com.example.reserva_canchas.domain.port.out.ReservationRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class CreateReservationService {

    private final ReservationRepositoryPort reservationRepositoryPort;

    public CreateReservationService(ReservationRepositoryPort reservationRepositoryPort) {
        this.reservationRepositoryPort = reservationRepositoryPort;
    }



}
