package com.example.reserva_canchas.infrastructure.repository.interfaces;

import com.example.reserva_canchas.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
}
