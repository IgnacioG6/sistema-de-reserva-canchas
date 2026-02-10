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

        if (userRepositoryPort.existsByEmail(email) && !user.getEmail().equals(email)) {
            throw new EmailDuplicateException("Email duplicated");
        }

        user.setEmail(email);
        user.setTelephone(telephone);

        return userRepositoryPort.save(user);

    }
}
