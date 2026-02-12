package com.example.reserva_canchas.infrastructure.controller;

import com.example.reserva_canchas.domain.model.Reservation;
import com.example.reserva_canchas.domain.port.in.reservation.CancelReservationUseCase;
import com.example.reserva_canchas.domain.port.in.reservation.CreateReservationUseCase;
import com.example.reserva_canchas.domain.port.in.reservation.GetReservationsUseCase;
import com.example.reserva_canchas.infrastructure.dto.request.CreateReservationRequestDTO;
import com.example.reserva_canchas.infrastructure.dto.response.ReservationResponseDTO;
import com.example.reserva_canchas.infrastructure.mapper.ReservationMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final CancelReservationUseCase  cancelReservation;
    private final CreateReservationUseCase createReservation;
    private final GetReservationsUseCase  getReservation;


    @PostMapping
    public ResponseEntity<ReservationResponseDTO> create(@Valid @RequestBody CreateReservationRequestDTO request) {

        Reservation reservation = createReservation.create(
                request.userId(),
                request.fieldId(),
                request.date(),
                request.startTime(),
                request.endTime(),
                request.priceTotal()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ReservationMapper.toResponse(reservation));
    }


}
