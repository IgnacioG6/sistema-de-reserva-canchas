package com.example.reserva_canchas.infrastructure.repository;

import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.model.enums.TypeField;
import com.example.reserva_canchas.domain.port.out.FieldRepositoryPort;
import com.example.reserva_canchas.infrastructure.entity.FieldEntity;
import com.example.reserva_canchas.infrastructure.mapper.FieldMapper;
import com.example.reserva_canchas.infrastructure.repository.interfaces.IFieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FieldRepositoryAdapter implements FieldRepositoryPort {

    private final IFieldRepository fieldRepository;

    @Override
    public Field save(Field field) {
        FieldEntity saved = fieldRepository.save(FieldMapper.toEntity(field));
        return FieldMapper.toDomain(saved);
    }

    public Optional<Field> findById(Long id) {
        return fieldRepository.findById(id)
                .map(FieldMapper::toDomain);
    }

    @Override
    public List<Field> findAll() {
        return fieldRepository.findAll().stream().map(FieldMapper::toDomain).toList();    }

    @Override
    public List<Field> findByType(TypeField type) {
        return fieldRepository.findByType(type).stream()
                .map(FieldMapper::toDomain)
                .toList();
    }
}
