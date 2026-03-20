package com.example.reserva_canchas.domain.port.in.user;

import com.example.reserva_canchas.domain.model.User;

public interface UpdateUserUseCase {

    User update(Long id, String email);
}
