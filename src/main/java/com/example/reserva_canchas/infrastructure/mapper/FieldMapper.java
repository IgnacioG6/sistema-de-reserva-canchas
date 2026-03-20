package com.example.reserva_canchas.infrastructure.mapper;

import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.infrastructure.dto.response.FieldResponseDTO;
import com.example.reserva_canchas.infrastructure.entity.FieldEntity;

public class FieldMapper {

    public static FieldEntity toEntity(Field field) {

        if(field == null) return null;

        return new FieldEntity(
                field.getId(),
                field.getName(),
                field.getPrice());
    }

    public static Field toDomain(FieldEntity entity) {

        if(entity == null) return null;

        Field field = new Field(entity.getName(), entity.getPrice());
        field.assignId(entity.getId());
        return field;
    }


    public static FieldResponseDTO toResponse(Field field) {

        if(field == null) return null;

        return new FieldResponseDTO(
                field.getId(),
                field.getName(),
                field.getPrice()
                );

    }

}
