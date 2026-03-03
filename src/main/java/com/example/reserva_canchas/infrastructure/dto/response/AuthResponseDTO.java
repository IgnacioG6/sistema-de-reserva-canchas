package com.example.reserva_canchas.infrastructure.dto.response;

public record AuthResponseDTO(
        String token,
        String email,
        String role
) {}