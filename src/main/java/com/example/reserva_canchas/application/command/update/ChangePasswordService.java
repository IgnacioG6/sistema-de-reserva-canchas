package com.example.reserva_canchas.application.command.update;

import com.example.reserva_canchas.domain.exception.PasswordMatchException;
import com.example.reserva_canchas.domain.exception.PasswordNotMatch;
import com.example.reserva_canchas.domain.exception.UserNotFoundException;
import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.port.in.user.ChangePasswordUseCase;
import com.example.reserva_canchas.domain.port.out.PasswordEncoderPort;
import com.example.reserva_canchas.domain.port.out.UserRepositoryPort;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!passwordEncoderPort.matches(oldPassword, user.getPassword())) {
            throw new PasswordNotMatch("La contraseñas no coinciden");
        }

        if (passwordEncoderPort.matches(newPassword, user.getPassword())) {
            throw new PasswordMatchException("La contraseña no puede ser igual a la anterior");
        }

        String hashedPassword = passwordEncoderPort.encode(newPassword);

        user.updatePassword(hashedPassword);
        userRepositoryPort.save(user);
    }


}
