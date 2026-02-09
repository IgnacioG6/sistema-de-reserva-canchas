package com.example.reserva_canchas.domain.port.in.field;

import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.model.enums.TypeField;

import java.math.BigDecimal;

public interface CreateFieldUseCase {

    Field create(String name, TypeField type, Long locationId, BigDecimal price);
}
