package com.example.reserva_canchas.application.query;

import com.example.reserva_canchas.domain.exception.UserNotFoundException;
import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.port.in.user.GetUserUseCase;
import com.example.reserva_canchas.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUserService implements GetUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public GetUserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public List<User> getUsers() {
        return userRepositoryPort.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepositoryPort.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    @Override
    public List<User> getUserActive() {
        return userRepositoryPort.findByActive();
    }
}
