package com.example.reserva_canchas.infrastructure.controller;

import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.port.in.user.*;
import com.example.reserva_canchas.infrastructure.dto.request.CreateUserRequestoDTO;
import com.example.reserva_canchas.infrastructure.dto.request.UpdateUserRequestDTO;
import com.example.reserva_canchas.infrastructure.dto.response.UserResponseDTO;
import com.example.reserva_canchas.infrastructure.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final ChangePasswordUseCase  changePasswordUseCase;
    private final CreateUserUseCase  createUserUseCase;
    private final DesactivateUserUseCase  desactivateUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetUserUseCase getUserUseCase;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserRequestoDTO userDto){

        User user = createUserUseCase.create(
                userDto.email(),
                userDto.password(),
                userDto.name(),
                userDto.telephone(),
                userDto.address()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponse(user));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable  Long id){
        User user = getUserUseCase.getUserById(id);
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers(
            @RequestParam(required = false) Boolean active) {

        if (active != null && active) {
            return ResponseEntity.ok(getUserUseCase.getUserActive().stream().map(UserMapper::toResponse).toList());
        }

        return ResponseEntity.ok(getUserUseCase.getUsers().stream().map(UserMapper::toResponse).toList());
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UpdateUserRequestDTO userDto){



    }





}
