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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {

        List<ReservationResponseDTO> reservations = getReservation.getReservations()
                .stream()
                .map(ReservationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable Long id) {
        Reservation reservation = getReservation.getReservationById(id);
        return ResponseEntity.ok(ReservationMapper.toResponse(reservation));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationByUserId(@Valid @PathVariable Long id) {

        List<ReservationResponseDTO> reservations = getReservation.getReservationsByUserId(id)
                .stream()
                .map(ReservationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
            ) {

        List<ReservationResponseDTO> reservations = getReservation.getReservationsByDate(date)
                .stream()
                .map(ReservationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/field/{id}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByFieldAndDate(
            @PathVariable Long fieldId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ){
        List<ReservationResponseDTO> reservations = getReservation.getReservationsByFieldAndDate(fieldId, date).stream()
                .map(ReservationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(reservations);

    }


    @GetMapping("/location/{id}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByLocation(@PathVariable Long id){
        List<ReservationResponseDTO> reservations = getReservation.getReservationsByLocationId(id).stream()
                .map(ReservationMapper::toResponse)
                .toList();

        return  ResponseEntity.ok(reservations);
    }



    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id){
        cancelReservation.cancelReservation(id);
        return ResponseEntity.ok().build();
    }


}
