package com.example.reserva_canchas.domain.port.out;

import com.example.reserva_canchas.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    void save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void delete(Long id);
}
