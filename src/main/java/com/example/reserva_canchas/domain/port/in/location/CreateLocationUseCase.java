package com.example.reserva_canchas.domain.port.in.location;

import com.example.reserva_canchas.domain.model.Location;

public interface CreateLocationUseCase {

    Location create(String name, String address, String city, String province, String telephone, String email);
}
