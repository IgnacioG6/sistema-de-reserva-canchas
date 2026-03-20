package com.example.reserva_canchas.infrastructure.controller;

import com.example.reserva_canchas.application.command.dto.CreateUserCommand;
import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.port.in.user.*;
import com.example.reserva_canchas.infrastructure.dto.request.ChangePasswordRequestDTO;
import com.example.reserva_canchas.infrastructure.dto.request.CreateUserRequestoDTO;
import com.example.reserva_canchas.infrastructure.dto.request.UpdateUserRequestDTO;
import com.example.reserva_canchas.infrastructure.dto.response.UserResponseDTO;
import com.example.reserva_canchas.infrastructure.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Endpoint para gestión de usuarios")
public class UserController {

    private final ChangePasswordUseCase  changePasswordUseCase;
    private final CreateUserUseCase  createUserUseCase;
    private final DesactivateUserUseCase  desactivateUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetUserUseCase getUserUseCase;

    @PostMapping
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario con rol CLIENT")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Email ya registrado")
    })
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserRequestoDTO userDto){

        CreateUserCommand command = new CreateUserCommand(userDto.email(),userDto.password(),userDto.name());

        User user = createUserUseCase.createUser(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponse(user));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario. CLIENT solo puede ver el suyo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.user.id")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable  Long id){
        User user = getUserUseCase.getUserById(id);
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Retorna todos los usuarios. Solo ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<UserResponseDTO>> getUsers() {

        return ResponseEntity.ok(getUserUseCase.getUsers().stream().map(UserMapper::toResponse).toList());
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.user.id")
    @Operation(summary = "Actualizar usuario", description = "Actualiza email y teléfono. CLIENT solo puede modificar el suyo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> updateUser(@PathVariable Long id,@Valid @RequestBody UpdateUserRequestDTO userDto){

        updateUserUseCase.update(id,userDto.email());

        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}/change-password")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.user.id")
    @Operation(summary = "Cambiar contraseña", description = "CLIENT solo puede cambiar la suya")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Contraseña actualizada"),
            @ApiResponse(responseCode = "400", description = "Contraseña incorrecta"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> changePassword(@PathVariable Long id,@Valid @RequestBody ChangePasswordRequestDTO passwordDto){

        changePasswordUseCase.changePassword(id,passwordDto.oldPassword(),passwordDto.newPassword());

        return ResponseEntity.noContent().build();
    }




}
