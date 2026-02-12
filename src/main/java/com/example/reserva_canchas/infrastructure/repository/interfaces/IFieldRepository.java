package com.example.reserva_canchas.infrastructure.repository.interfaces;

import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.model.enums.TypeField;
import com.example.reserva_canchas.infrastructure.entity.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFieldRepository extends JpaRepository<FieldEntity, Long> {
    List<FieldEntity> findByType(TypeField type);
}
