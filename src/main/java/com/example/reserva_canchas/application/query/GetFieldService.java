package com.example.reserva_canchas.application.query;

import com.example.reserva_canchas.domain.exception.FieldNotFoundException;
import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.model.enums.TypeField;
import com.example.reserva_canchas.domain.port.in.field.GetFieldUseCase;
import com.example.reserva_canchas.domain.port.out.FieldRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetFieldService implements GetFieldUseCase {

    private final FieldRepositoryPort fieldRepositoryPort;
    public GetFieldService(FieldRepositoryPort fieldRepositoryPort) {
        this.fieldRepositoryPort = fieldRepositoryPort;
    }

    @Override
    public List<Field> getFields() {
        return fieldRepositoryPort.findAll();
    }

    @Override
    public Field getFieldById(Long id) {
        return fieldRepositoryPort.findById(id)
                .orElseThrow(()-> new FieldNotFoundException(id));
    }

    @Override
    public List<Field> getFieldsByType(TypeField type) {
        List<Field> byType = fieldRepositoryPort.findByType(type);
        return byType;
    }
}
