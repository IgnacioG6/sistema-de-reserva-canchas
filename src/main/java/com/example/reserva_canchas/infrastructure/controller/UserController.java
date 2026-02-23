package com.example.reserva_canchas.infrastructure.controller;

import com.example.reserva_canchas.domain.port.in.user.ChangePasswordUseCase;
import com.example.reserva_canchas.domain.port.in.user.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final ChangePasswordUseCase  changePasswordUseCase;
    private final CreateUserUseCase  createUserUseCase;


}
