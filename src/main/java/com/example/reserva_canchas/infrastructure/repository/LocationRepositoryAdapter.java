package com.example.reserva_canchas.infrastructure.repository;

import com.example.reserva_canchas.domain.model.Location;
import com.example.reserva_canchas.domain.port.out.LocationRepositoryPort;
import com.example.reserva_canchas.infrastructure.entity.LocationEntity;
import com.example.reserva_canchas.infrastructure.mapper.LocationMapper;
import com.example.reserva_canchas.infrastructure.repository.interfaces.ILocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LocationRepositoryAdapter implements LocationRepositoryPort {

    private final ILocationRepository  locationRepository;


    @Override
    public Location save(Location location) {
        LocationEntity saved = locationRepository.save(LocationMapper.toEntity(location));
        return LocationMapper.toDomain(saved);
    }

    @Override
    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id)
                .map(LocationMapper::toDomain);
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll().stream().map(LocationMapper::toDomain).toList();
    }
}
