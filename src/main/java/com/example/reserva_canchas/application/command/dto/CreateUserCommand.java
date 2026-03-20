package com.example.reserva_canchas.application.command.dto;

public record CreateUserCommand(
        String email,
        String password,
        String name
) {
}
