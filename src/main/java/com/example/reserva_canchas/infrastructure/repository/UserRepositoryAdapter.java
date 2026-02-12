package com.example.reserva_canchas.infrastructure.repository;

import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.port.out.UserRepositoryPort;
import com.example.reserva_canchas.infrastructure.entity.UserEntity;
import com.example.reserva_canchas.infrastructure.mapper.UserMapper;
import com.example.reserva_canchas.infrastructure.repository.interfaces.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final IUserRepository userRepository;

    @Override
    public User save(User user) {
        UserEntity saved = userRepository.save(UserMapper.toEntity(user));
        return UserMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> findByActive() {
        return userRepository.findByActiveTrue().stream()
                .map(UserMapper::toDomain)
                .toList();
    }
}
