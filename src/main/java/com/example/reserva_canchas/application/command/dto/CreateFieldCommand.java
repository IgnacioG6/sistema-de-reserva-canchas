package com.example.reserva_canchas.application.command.dto;

import java.math.BigDecimal;

public record CreateFieldCommand(
        String name,
        BigDecimal price
) {
}
