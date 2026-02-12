package com.example.reserva_canchas.infrastructure.mapper;

import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.infrastructure.entity.FieldEntity;

public class FieldMapper {

    public static FieldEntity toEntity(Field field) {

        if(field == null) return null;


        return new FieldEntity(
                field.getId(),
                field.getName(),
                field.getType(),
                LocationMapper.toEntity(field.getLocation()),
                field.getPrice(),
                field.isActive());
    }

    public static Field toDomain(FieldEntity entity) {

        if(entity == null) return null;

        return new Field(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                LocationMapper.toDomain(entity.getLocation()),
                entity.getPrice(),
                entity.isActive());

    }


}
