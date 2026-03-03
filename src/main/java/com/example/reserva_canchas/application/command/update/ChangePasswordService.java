package com.example.reserva_canchas.application.command.update;

import com.example.reserva_canchas.domain.exception.UserNotFoundException;
import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.port.in.user.ChangePasswordUseCase;
import com.example.reserva_canchas.domain.port.out.PasswordEncoderPort;
import com.example.reserva_canchas.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordService implements ChangePasswordUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public ChangePasswordService(UserRepositoryPort userRepositoryPort,
                                 PasswordEncoderPort passwordEncoderPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        validatePassword(user, oldPassword, newPassword);

        user.setPassword(passwordEncoderPort.encode(newPassword));
        userRepositoryPort.save(user);
    }

    private void validatePassword(User user, String oldPassword, String newPassword) {
        if (!passwordEncoderPort.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("The passwords don't match.");
        }

        if (passwordEncoderPort.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("The password cannot be the same as the previous one.");
        }

        if (newPassword.length() < 6) {
            throw new IllegalArgumentException("The password cannot be less than 6 characters.");
        }
    }

}
