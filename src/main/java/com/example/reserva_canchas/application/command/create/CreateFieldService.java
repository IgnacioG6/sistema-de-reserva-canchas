package com.example.reserva_canchas.application.command.create;

import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.model.enums.TypeField;
import com.example.reserva_canchas.domain.port.in.field.CreateFieldUseCase;
import com.example.reserva_canchas.domain.port.out.FieldRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateFieldService implements CreateFieldUseCase {

    private final FieldRepositoryPort fieldRepository;

    public CreateFieldService(FieldRepositoryPort fieldRepository) {
        this.fieldRepository = fieldRepository;
    }


    @Override
    public Field create(String name, TypeField type, BigDecimal price) {

        Field field = new Field(name,type,price, true);

        return fieldRepository.save(field);

    }
}
