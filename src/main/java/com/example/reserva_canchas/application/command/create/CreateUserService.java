package com.example.reserva_canchas.application.command.create;

import com.example.reserva_canchas.application.command.dto.CreateUserCommand;
import com.example.reserva_canchas.domain.exception.EmailDuplicateException;
import com.example.reserva_canchas.domain.exception.InvalidPasswordException;
import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.model.enums.Role;
import com.example.reserva_canchas.domain.port.in.user.CreateUserUseCase;
import com.example.reserva_canchas.domain.port.out.PasswordEncoderPort;
import com.example.reserva_canchas.domain.port.out.UserRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public CreateUserService(UserRepositoryPort userRepositoryPort,
                             PasswordEncoderPort passwordEncoderPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    @Transactional
    public User createUser(CreateUserCommand command) {

        validateData(command.email());

        String hashedPassword = passwordEncoderPort.encode(command.password());

        User user = new User(command.email(), hashedPassword, command.name());

        return userRepositoryPort.save(user);
    }

    private void validateData(String email) {
        if (userRepositoryPort.existsByEmail(email)) {
            throw new EmailDuplicateException("Email already exists");
        }
    }
}

