package com.example.reserva_canchas.domain.port.in.location;

import com.example.reserva_canchas.domain.model.Location;

import java.util.List;

public interface GetLocationUseCase {

    List<Location> getLocations();
    Location getLocationById(Long id);


}
