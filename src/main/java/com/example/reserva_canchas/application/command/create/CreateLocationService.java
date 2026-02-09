package com.example.reserva_canchas.application.command.create;

import com.example.reserva_canchas.domain.model.Location;
import com.example.reserva_canchas.domain.port.in.location.CreateLocationUseCase;
import com.example.reserva_canchas.domain.port.out.LocationRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class CreateLocationService implements CreateLocationUseCase {

    private final LocationRepositoryPort locationRepository;


    public CreateLocationService(LocationRepositoryPort locationRepository) {
        this.locationRepository = locationRepository;
    }


    @Override
    public Location create(String name, String address, String city, String province, String telephone, String email) {

        Location location =  new Location(name, address, city, province, telephone, email);

        locationRepository.save(location);

        return location;

    }
}
