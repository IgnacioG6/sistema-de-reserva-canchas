package com.example.reserva_canchas.domain.port.out;

import com.example.reserva_canchas.domain.model.Reservation;

public interface PaymentPort {
    String createPayment(Reservation reservation);
}
