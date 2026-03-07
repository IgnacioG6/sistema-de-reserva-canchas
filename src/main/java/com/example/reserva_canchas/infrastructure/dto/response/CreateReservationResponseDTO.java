package com.example.reserva_canchas.infrastructure.dto.response;

public record CreateReservationResponseDTO(
        ReservationResponseDTO reservation,
        String paymentUrl
) {
}
