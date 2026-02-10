package com.example.reserva_canchas.application.command.update;

import com.example.reserva_canchas.domain.exception.UserNotFoundException;
import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.port.in.user.DesactivateUserUseCase;
import com.example.reserva_canchas.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class DesactivateUserService implements DesactivateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public DesactivateUserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public void desactivateUser(Long id) {
        User user = userRepositoryPort.findById(id).orElseThrow(()-> new UserNotFoundException(id));

        user.desactivate();

        userRepositoryPort.save(user);
    }
}
