package com.example.reserva_canchas.domain.model;

import com.example.reserva_canchas.domain.model.enums.Role;

public class User {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String telephone;
    private String address;
    private boolean active;
    private Role role;

    public User(Long id, String email, String password,
                String name, String telephone, String address, boolean active, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.active = active;
        this.role = role;

    }

    public User(String email, String password, String name,
                String telephone, String address, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.active = true;
        this.role = role;

    }

    public void desactivate() {
        if (!this.active) {
            throw new IllegalStateException("This user is now deactivated");
        }

        this.active = false;

    }

    public void updatePassword(String oldPasswordProvided, String newPassword) {
        if(oldPasswordProvided.equals(newPassword)) {
            throw new IllegalArgumentException("The password cannot be the same as the previous one.");
        }

        if(newPassword.length() < 6) {
            throw new IllegalArgumentException("The password cannot be less than 6 characters.");
        }

        if (!password.equals(oldPasswordProvided)) {
            throw new IllegalArgumentException("The passwords don't match.");
        }
        this.password = newPassword;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

