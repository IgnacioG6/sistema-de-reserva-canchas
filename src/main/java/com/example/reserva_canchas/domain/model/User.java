package com.example.reserva_canchas.domain.model;

import com.example.reserva_canchas.domain.exception.*;
import com.example.reserva_canchas.domain.model.enums.Role;

import java.util.Objects;

public class User {

    private Long id;
    private String email;
    private String password;
    private String name;
    private boolean active;
    private Role role;


    public User(String email, String password, String name) {

        Objects.requireNonNull(email, "email es requerido");
        Objects.requireNonNull(password, "contraseña es requerido");
        Objects.requireNonNull(name, "name es requerido");

        if (password.length() < 6) {
            throw new InvalidPasswordException("Password too short");
        }

        this.email = email;
        this.password = password;
        this.name = name;
        this.active = true;
        this.role = Role.CLIENT;

    }


    public static User reconstruct(Long id, String email, String password,
                                   String name, Role role) {
        User user = new User(email, password, name);
        user.assignId(id);
        user.role = role;
        return user;
    }


    public void assignId(Long id) {
        if (this.id != null) {
            throw new UserWithAssignedIdException("El usuario ya tiene un id asignado");
        }
        this.id = id;
    }


    public void desactivate() {
        if (!this.active) {
            throw new UserDisabledException("This user is deactivated");
        }

        this.active = false;

    }

    public void updatePassword(String encodedNewPassword) {
        this.password = encodedNewPassword;
    }

    public void changeEmail(String email){
        if (this.email.equals(email)) {
            throw new EmailInvalidException("El correo electrónico no puede ser el mismo que el anterior.");
        }
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public Role getRole() {
        return role;
    }

}

