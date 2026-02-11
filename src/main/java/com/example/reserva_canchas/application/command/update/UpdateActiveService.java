package com.example.reserva_canchas.application.command.update;

import com.example.reserva_canchas.domain.exception.FieldNotFoundException;
import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.port.in.field.UpdateActiveUseCase;
import com.example.reserva_canchas.domain.port.out.FieldRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class UpdateActiveService implements UpdateActiveUseCase {

    private final FieldRepositoryPort fieldRepositoryPort;

    public UpdateActiveService(FieldRepositoryPort fieldRepositoryPort) {
        this.fieldRepositoryPort = fieldRepositoryPort;
    }

    @Override
    public void updateActive(Long id, boolean active) {

        Field field =  fieldRepositoryPort.findById(id)
                .orElseThrow(()-> new FieldNotFoundException(id));

        if (field.isActive() == active) {
            throw new IllegalStateException("Field is already " + (active ? "active" : "inactive"));
        }

        field.setActive(active);
        fieldRepositoryPort.save(field);

    }
}
