package com.example.reserva_canchas.domain.port.in.field;

import com.example.reserva_canchas.domain.model.Field;

import java.util.List;

public interface GetFieldUseCase {

    List<Field> getFields();
    Field getFieldById(Long id);

}
