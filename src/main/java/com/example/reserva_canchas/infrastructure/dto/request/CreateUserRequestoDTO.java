package com.example.reserva_canchas.infrastructure.dto.request;

public record CreateUserRequestoDTO(
        String email,
        String password,
        String name,
        String telephone,
        String address


) {
}
