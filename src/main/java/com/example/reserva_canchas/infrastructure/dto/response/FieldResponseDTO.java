package com.example.reserva_canchas.infrastructure.dto.response;


import java.math.BigDecimal;

public record FieldResponseDTO(

        Long id,
        String name,
        String type,
        String address,
        String city,
        String province,
        BigDecimal price

) {
}
