package com.example.reserva_canchas.infrastructure.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequestDTO(
        @NotBlank(message = "Your current password is required.")
        String oldPassword,

        @NotBlank(message = "The new password is required")
        @Size(min = 6, message = "The new password must be at least 6 characters long")
        String newPassword
) {}