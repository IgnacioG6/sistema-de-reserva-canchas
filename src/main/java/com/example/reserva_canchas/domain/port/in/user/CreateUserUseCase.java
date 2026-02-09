package com.example.reserva_canchas.domain.port.in.user;

import com.example.reserva_canchas.domain.model.User;

public interface CreateUserUseCase {

    User create(User user);

}
