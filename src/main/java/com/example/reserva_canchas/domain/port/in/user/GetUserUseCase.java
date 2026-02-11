package com.example.reserva_canchas.domain.port.in.user;

import com.example.reserva_canchas.domain.model.User;

import java.util.List;

public interface GetUserUseCase {

    List<User> getUser();
    User  getUserById(Long id);
    List<User> getUserActive();

}
