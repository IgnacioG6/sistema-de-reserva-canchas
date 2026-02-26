package com.example.reserva_canchas.infrastructure.mapper;

import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.infrastructure.dto.response.UserResponseDTO;
import com.example.reserva_canchas.infrastructure.entity.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(User user) {

        if(user == null) return null;


        return new UserEntity(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getTelephone(),
                user.getAddress(),
                user.isActive());
    }


    public static User toDomain(UserEntity entity) {

        if(entity == null) return null;


        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getName(),
                entity.getTelephone(),
                entity.getAddress(),
                entity.isActive()
        );
    }


    public static UserResponseDTO toResponse(User user) {

        if(user == null) return null;


        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }


}
