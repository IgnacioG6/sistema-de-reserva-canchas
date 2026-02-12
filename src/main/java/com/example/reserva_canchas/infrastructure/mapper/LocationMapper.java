package com.example.reserva_canchas.infrastructure.mapper;

import com.example.reserva_canchas.domain.model.Location;
import com.example.reserva_canchas.infrastructure.entity.LocationEntity;

public class LocationMapper {
    public static LocationEntity toEntity(Location location) {

        if(location == null) return null;

        return new LocationEntity(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getCity(),
                location.getProvince(),
                location.getTelephone(),
                location.getEmail()
        );
    }

    public static Location toDomain(LocationEntity entity) {

        if(entity == null) return null;

        return new Location(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getCity(),
                entity.getProvince(),
                entity.getTelephone(),
                entity.getEmail()
        );


    }
}
