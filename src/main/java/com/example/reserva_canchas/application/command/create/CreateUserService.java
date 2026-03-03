package com.example.reserva_canchas.application.command.create;

import com.example.reserva_canchas.domain.exception.EmailDuplicateException;
import com.example.reserva_canchas.domain.exception.InvalidPasswordException;
import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.model.enums.Role;
import com.example.reserva_canchas.domain.port.in.user.CreateUserUseCase;
import com.example.reserva_canchas.domain.port.out.PasswordEncoderPort;
import com.example.reserva_canchas.domain.port.out.UserRepositoryPort;
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
    public User create(String email, String password, String name, String telephone, String address) {

        validateData(email, password);

        String hashedPassword = passwordEncoderPort.encode(password);

        User user = new User(email, hashedPassword, name, telephone, address, Role.CLIENT);

        return userRepositoryPort.save(user);
    }

    private void validateData(String email, String password) {
        if (userRepositoryPort.existsByEmail(email)) {
            throw new EmailDuplicateException("Email already exists");
        }
        if (password.length() < 6) {
            throw new InvalidPasswordException("Password too short");
        }
    }
}

