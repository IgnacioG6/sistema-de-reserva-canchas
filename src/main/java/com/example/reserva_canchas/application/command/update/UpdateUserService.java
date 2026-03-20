package com.example.reserva_canchas.application.command.update;

import com.example.reserva_canchas.domain.exception.EmailDuplicateException;
import com.example.reserva_canchas.domain.exception.UserNotFoundException;
import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.port.in.user.UpdateUserUseCase;
import com.example.reserva_canchas.domain.port.out.UserRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public UpdateUserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }


    @Override
    @Transactional
    public User update(Long id, String email) {

        User user = userRepositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (userRepositoryPort.existsByEmail(email)) {
        throw new EmailDuplicateException(email);
            }

        user.changeEmail(email);

        return userRepositoryPort.save(user);

    }
}
