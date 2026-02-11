package com.example.reserva_canchas.application.query;

import com.example.reserva_canchas.domain.exception.LocationNotFoundException;
import com.example.reserva_canchas.domain.model.Location;
import com.example.reserva_canchas.domain.port.in.location.GetLocationUseCase;
import com.example.reserva_canchas.domain.port.out.LocationRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetLocationService implements GetLocationUseCase {

    private final LocationRepositoryPort  locationRepositoryPort;

    public GetLocationService(LocationRepositoryPort locationRepositoryPort) {
        this.locationRepositoryPort = locationRepositoryPort;
    }

    @Override
    public List<Location> getLocations() {
        return locationRepositoryPort.findAll();
    }

    @Override
    public Location getLocationById(Long id) {
        return locationRepositoryPort.findById(id)
                .orElseThrow(()-> new LocationNotFoundException(id));
    }
}
