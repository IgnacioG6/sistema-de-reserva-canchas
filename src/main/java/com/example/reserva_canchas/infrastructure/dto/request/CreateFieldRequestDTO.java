package com.example.reserva_canchas.infrastructure.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateFieldRequestDTO(
        @NotBlank
        String name,

        @NotNull
        @DecimalMin("50")
        BigDecimal price

) {
}
