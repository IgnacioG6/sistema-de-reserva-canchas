package com.example.reserva_canchas.domain.port.in.reservation;

import com.example.reserva_canchas.application.command.dto.CreateReservationCommand;
import com.example.reserva_canchas.application.command.dto.CreateReservationResult;


public interface CreateReservationUseCase {

    CreateReservationResult createReservation(CreateReservationCommand command);

}