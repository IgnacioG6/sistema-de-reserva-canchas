package com.example.reserva_canchas.domain.port.out;

import com.example.reserva_canchas.domain.model.Field;

import java.util.List;
import java.util.Optional;

public interface FieldRepositoryPort {
    void save(Field field);
    Optional<Field> findById(Long id);
    List<Field> findAll();
}
