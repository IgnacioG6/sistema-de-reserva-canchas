package com.example.reserva_canchas.infrastructure.repository.interfaces;

import com.example.reserva_canchas.infrastructure.entity.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFieldRepository extends JpaRepository<FieldEntity, Long> {
}
