package com.example.reserva_canchas.domain.port.in.field;

import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.model.enums.TypeField;

import java.util.List;

public interface GetFieldUseCase {

    List<Field> getFields();
    Field getFieldById(String id);
    List<Field> getFieldsByType(TypeField type);

}
