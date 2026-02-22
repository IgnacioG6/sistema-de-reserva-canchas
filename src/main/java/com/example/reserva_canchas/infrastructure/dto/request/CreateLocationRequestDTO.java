package com.example.reserva_canchas.infrastructure.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateLocationRequestDTO(

        @NotBlank
        String name,
        @NotBlank
        String address,
        @NotBlank
        String city,
        @NotBlank
        String province,
        @NotBlank
        String telephone,

        @Email
        String email
) {
}
