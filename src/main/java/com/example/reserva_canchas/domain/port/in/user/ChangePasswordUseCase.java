package com.example.reserva_canchas.domain.port.in.user;

public interface ChangePasswordUseCase {

    void changePassword(String oldPassword, String newPassword);

}
