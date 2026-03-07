package com.example.reserva_canchas.infrastructure.repository;

import com.example.reserva_canchas.domain.model.Reservation;
import com.example.reserva_canchas.domain.port.out.PaymentPort;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MercadoPagoAdapter implements PaymentPort {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    @Override
    public String createPayment(Reservation reservation) {
        try {
            MercadoPagoConfig.setAccessToken(accessToken);

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title("Reserva cancha - " + reservation.getId())
                    .quantity(1)
                    .unitPrice(reservation.getPriceTotal())
                    .currencyId("ARS")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(List.of(item))
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            return preference.getInitPoint();

        } catch (Exception e) {
            throw new RuntimeException("Error al crear el pago en MercadoPago", e);
        }
    }
}