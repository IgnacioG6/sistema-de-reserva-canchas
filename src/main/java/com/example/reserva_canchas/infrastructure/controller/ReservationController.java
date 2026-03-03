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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final CancelReservationUseCase cancelReservation;
    private final CreateReservationUseCase createReservation;
    private final GetReservationsUseCase getReservation;


    @PostMapping
    public ResponseEntity<ReservationResponseDTO> create(@Valid @RequestBody CreateReservationRequestDTO request) {

        Reservation reservation = createReservation.create(
                request.userId(),
                request.fieldId(),
                request.date(),
                request.startTime(),
                request.endTime()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ReservationMapper.toResponse(reservation));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {

        return ResponseEntity.ok(getReservation.getReservations()
                .stream()
                .map(ReservationMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @reservationSecurity.isOwner(#id, authentication.principal.user.id)")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable Long id) {
        Reservation reservation = getReservation.getReservationById(id);
        return ResponseEntity.ok(ReservationMapper.toResponse(reservation));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.user.id")
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

        return ResponseEntity.ok(getReservation.getReservationsByDate(date)
                .stream()
                .map(ReservationMapper::toResponse)
                .toList());
    }

    @GetMapping("/field/{id}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByFieldAndDate(
            @PathVariable Long fieldId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(getReservation.getReservationsByFieldAndDate(fieldId, date).stream()
                .map(ReservationMapper::toResponse)
                .toList());

    }


    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ADMIN') or @reservationSecurity.isOwner(#id, authentication.principal.user.id)")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id){
        cancelReservation.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }


}
