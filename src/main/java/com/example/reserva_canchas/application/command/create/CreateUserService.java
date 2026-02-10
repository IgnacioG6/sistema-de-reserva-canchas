package com.example.reserva_canchas.application.command.create;

import com.example.reserva_canchas.domain.exception.EmailDuplicateException;
import com.example.reserva_canchas.domain.exception.InvalidPasswordException;
import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.port.in.user.CreateUserUseCase;
import com.example.reserva_canchas.domain.port.out.UserRepositoryPort;

public class CreateUserService implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public CreateUserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }


    @Override
    public User create(String email, String password, String name, String telephone, String address) {

        validateData(email, password);

        User user = new User(email, password, name, telephone, address);

        return userRepositoryPort.save(user);

    }

    private void validateData(String email, String password) {
        if (userRepositoryPort.existsByEmail(email)){
            throw new EmailDuplicateException("Email already exists");
        }

        if (password.length() < 6){
            throw new InvalidPasswordException("Password too short");
        }
    }

}
