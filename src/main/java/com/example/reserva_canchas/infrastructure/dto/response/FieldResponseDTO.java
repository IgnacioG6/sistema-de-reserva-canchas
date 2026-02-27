package com.example.reserva_canchas.infrastructure.dto.response;


import java.math.BigDecimal;

public record FieldResponseDTO(

        Long id,
        String name,
        String type,
        BigDecimal price

) {
}
