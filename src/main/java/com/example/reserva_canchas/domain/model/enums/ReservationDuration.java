package com.example.reserva_canchas.domain.model.enums;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum ReservationDuration {
    ONE_HOUR(60),
    NINETY_MINUTES(90),
    TWO_HOURS(120);

    private final int minutes;

    ReservationDuration(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public BigDecimal calculatePrice(BigDecimal hourlyRate) {
        return hourlyRate.multiply(BigDecimal.valueOf(minutes))
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
    }
}
