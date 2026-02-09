package com.example.reserva_canchas.application.command.create;

import com.example.reserva_canchas.domain.exception.LocationNotFoundException;
import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.model.Location;
import com.example.reserva_canchas.domain.model.enums.TypeField;
import com.example.reserva_canchas.domain.port.in.field.CreateFieldUseCase;
import com.example.reserva_canchas.domain.port.out.FieldRepositoryPort;
import com.example.reserva_canchas.domain.port.out.LocationRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateFieldService implements CreateFieldUseCase {

    private final FieldRepositoryPort fieldRepository;
    private final LocationRepositoryPort locationRepository;

    public CreateFieldService(FieldRepositoryPort fieldRepository, LocationRepositoryPort locationRepository) {
        this.fieldRepository = fieldRepository;
        this.locationRepository = locationRepository;
    }


    @Override
    public Field create(String name, TypeField type, Long locationId, BigDecimal price) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationNotFoundException(locationId));

        Field field = new Field(name,type,location,price, true);

        fieldRepository.save(field);

        return field;

    }
}
