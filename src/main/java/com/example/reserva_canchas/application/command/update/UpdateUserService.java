package com.example.reserva_canchas.application.command.update;

import com.example.reserva_canchas.domain.exception.EmailDuplicateException;
import com.example.reserva_canchas.domain.exception.UserNotFoundException;
import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.port.in.user.UpdateUserUseCase;
import com.example.reserva_canchas.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public UpdateUserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }


    @Override
    public User update(Long id, String email, String telephone) {

        User user = userRepositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!user.getEmail().equals(email)) {
            if (userRepositoryPort.existsByEmail(email)) {
                throw new EmailDuplicateException(email);
            }

            user.setEmail(email);
        }

        if (!user.getTelephone().equals(telephone)) {
            user.setTelephone(telephone);
        }


        return userRepositoryPort.save(user);

    }
}
