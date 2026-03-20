package com.example.reserva_canchas.domain.port.in.field;

import com.example.reserva_canchas.application.command.dto.CreateFieldCommand;
import com.example.reserva_canchas.domain.model.Field;

import java.math.BigDecimal;

public interface CreateFieldUseCase {

    Field createField(CreateFieldCommand command);
}
