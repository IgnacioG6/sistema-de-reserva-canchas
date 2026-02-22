package com.example.reserva_canchas.infrastructure.controller;

import com.example.reserva_canchas.application.command.create.CreateLocationService;
import com.example.reserva_canchas.application.query.GetLocationService;
import com.example.reserva_canchas.domain.model.Location;
import com.example.reserva_canchas.infrastructure.dto.request.CreateLocationRequestDTO;
import com.example.reserva_canchas.infrastructure.dto.response.LocationResponseDTO;
import com.example.reserva_canchas.infrastructure.mapper.LocationMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LocationController {

    private final CreateLocationService createLocationUseCase;
    private final GetLocationService getLocationUseCase;


    @PostMapping
    public ResponseEntity<LocationResponseDTO> createLocation(@Valid @RequestBody CreateLocationRequestDTO createLocationRequestDTO){

        Location location = createLocationUseCase.create(
                createLocationRequestDTO.name(),
                createLocationRequestDTO.address(),
                createLocationRequestDTO.city(),
                createLocationRequestDTO.province(),
                createLocationRequestDTO.telephone(),
                createLocationRequestDTO.email()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(LocationMapper.toResponse(location));

    }



}
