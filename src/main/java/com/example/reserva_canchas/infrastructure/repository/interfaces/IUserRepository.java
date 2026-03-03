package com.example.reserva_canchas.infrastructure.repository.interfaces;

import com.example.reserva_canchas.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    List<UserEntity> findByActiveTrue();
    Optional<UserEntity> findByEmail(String email);
}
