package com.example.reserva_canchas.application.command.create;

import com.example.reserva_canchas.application.command.dto.CreateFieldCommand;
import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.port.in.field.CreateFieldUseCase;
import com.example.reserva_canchas.domain.port.out.FieldRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateFieldService implements CreateFieldUseCase {

    private final FieldRepositoryPort fieldRepository;

    public CreateFieldService(FieldRepositoryPort fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    @Transactional
    public Field createField(CreateFieldCommand command) {

        Field field = new Field(command.name(), command.price());

        return fieldRepository.save(field);

    }
}
