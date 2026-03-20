package com.example.reserva_canchas.infrastructure.controller;

import com.example.reserva_canchas.application.command.dto.CreateReservationCommand;
import com.example.reserva_canchas.application.command.dto.CreateReservationResult;
import com.example.reserva_canchas.domain.model.Reservation;
import com.example.reserva_canchas.domain.model.enums.Role;
import com.example.reserva_canchas.domain.port.in.reservation.CancelReservationUseCase;
import com.example.reserva_canchas.domain.port.in.reservation.CreateReservationUseCase;
import com.example.reserva_canchas.domain.port.in.reservation.GetReservationsUseCase;
import com.example.reserva_canchas.domain.port.out.PaymentPort;
import com.example.reserva_canchas.infrastructure.dto.request.CreateReservationRequestDTO;
import com.example.reserva_canchas.infrastructure.dto.response.CreateReservationResponseDTO;
import com.example.reserva_canchas.infrastructure.dto.response.ReservationResponseDTO;
import com.example.reserva_canchas.infrastructure.mapper.ReservationMapper;
import com.example.reserva_canchas.infrastructure.security.UserDetailsAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Endpoints para gestión de reservas de canchas")

public class ReservationController {

    private final CancelReservationUseCase cancelReservation;
    private final CreateReservationUseCase createReservation;
    private final GetReservationsUseCase getReservation;
    private final PaymentPort paymentPort;


    @PostMapping
    @Operation(summary = "Crear reserva", description = "Crea una nueva reserva para una cancha")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflicto de horario")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or #request.userId() == authentication.principal.user.id")
    public ResponseEntity<CreateReservationResponseDTO> create(@Valid @RequestBody CreateReservationRequestDTO request, Authentication authentication) {

        UserDetailsAdapter principal = (UserDetailsAdapter) authentication.getPrincipal();
        Long authenticatedUserId = principal.getUser().getId();

        Long userId = principal.getUser().getRole().equals(Role.ADMIN)
                ? request.userId()
                : authenticatedUserId;

        CreateReservationCommand command = new CreateReservationCommand(
                userId,
                request.fieldId(),
                request.date(),
                request.startTime(),
                request.duration()
        );

        CreateReservationResult result = createReservation.createReservation(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateReservationResponseDTO(
                        ReservationMapper.toResponse(result.reservation()),
                        result.paymentUrl()
                ));
    }

    @GetMapping
    @Operation(summary = "Listar reservas", description = "Retorna todas las reservas. Solo ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {

        return ResponseEntity.ok(getReservation.getReservations()
                .stream()
                .map(ReservationMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @reservationSecurity.isOwner(#id, authentication.principal.user.id)")
    @Operation(summary = "Obtener reserva por ID", description = "CLIENT solo puede ver la suya")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable Long id) {
        Reservation reservation = getReservation.getReservationById(id);
        return ResponseEntity.ok(ReservationMapper.toResponse(reservation));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.user.id")
    @Operation(summary = "Listar reservas por usuario", description = "CLIENT solo puede ver las suyas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationByUserId(@Valid @PathVariable Long id) {

        List<ReservationResponseDTO> reservations = getReservation.getReservationsByUserId(id)
                .stream()
                .map(ReservationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/date/{date}")
    @Operation(summary = "Listar reservas por fecha", description = "Retorna reservas de una fecha. Solo ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {

        return ResponseEntity.ok(getReservation.getReservationsByDate(date)
                .stream()
                .map(ReservationMapper::toResponse)
                .toList());
    }

    @GetMapping("/field/{fieldId}")
    @Operation(summary = "Listar reservas por cancha", description = "Retorna reservas de una cancha por fecha")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @SecurityRequirement(name = "bearerAuth")
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
    @Operation(summary = "Cancelar reserva", description = "CLIENT solo puede cancelar la suya")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva cancelada"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id){
        cancelReservation.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }


}
