package com.example.reserva_canchas.infrastructure.dto.response;

public record LocationResponseDTO(
        Long id,
        String name,
        String address,
        String city,
        String province,
        String telephone,
        String email
) {
}
