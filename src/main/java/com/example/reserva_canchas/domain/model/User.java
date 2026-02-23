package com.example.reserva_canchas.domain.model;

public class User {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String telephone;
    private String address;
    private boolean active;

    public User(Long id, String email, String password,
                String name, String telephone, String address, boolean active) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.active = active;

    }

    public User(String email, String password, String name,
                String telephone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.active = true;
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
}

