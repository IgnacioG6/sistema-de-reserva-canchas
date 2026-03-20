package com.example.reserva_canchas.domain.model;

import com.example.reserva_canchas.domain.exception.*;
import com.example.reserva_canchas.domain.model.enums.ReservationDuration;
import com.example.reserva_canchas.domain.model.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private Long id;
    private User user;
    private Field field;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private ReservationStatus status;
    private BigDecimal priceTotal;
    private LocalDateTime dateCreation;
    private ReservationDuration duration;

    public Reservation(User user, Field field, LocalDate date,
                       LocalTime startTime, BigDecimal priceTotal,
                       ReservationDuration duration) {
        Objects.requireNonNull(user, "user es requerido");
        Objects.requireNonNull(field, "field es requerido");
        Objects.requireNonNull(date, "date es requerido");
        Objects.requireNonNull(startTime, "startTime es requerido");
        Objects.requireNonNull(priceTotal, "priceTotal es requerido");
        Objects.requireNonNull(duration, "duration es requerido");

        if (date.isBefore(LocalDate.now())) {
            throw new ReservationPastDate("No se puede reservar una fecha pasada");
        }

        this.user = user;
        this.field = field;
        this.date = date;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(duration.getMinutes());
        this.status = ReservationStatus.PENDING;
        this.priceTotal = priceTotal;
        this.dateCreation = LocalDateTime.now();
        this.duration = duration;

    }

    public void cancel() {
        if (this.status != ReservationStatus.CONFIRMED) {
            throw new InvalidReservationStateException("Solo se puede cancelar una reserva CONFIRMADA");
        }

        validateDate(this.date, this.startTime);

        this.status = ReservationStatus.CANCELLED;
    }

    public void confirm() {
        if (this.status != ReservationStatus.PENDING) {
            throw new InvalidReservationStateException("Solo se puede confirmar una reserva PENDIENTE");
        }

        validateDate(this.date, this.startTime);

        this.status = ReservationStatus.CONFIRMED;
    }


    private void validateDate(LocalDate date, LocalTime startTime) {
        LocalDateTime dateReservation = LocalDateTime.of(date, startTime);

        if (dateReservation.isBefore(LocalDateTime.now())) {
            throw new ReservationPastDate("No se puede modificar una reserva pasada");
        }
    }

    public void assignId(Long id) {
        if (this.id != null) {
            throw new ReservationWithAssignedIdException("La reserva ya tiene un id asignado");
        }
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Field getField() {
        return field;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }


    public LocalTime getEndTime() {
        return endTime;
    }


    public ReservationStatus getStatus() {
        return status;
    }


    public BigDecimal getPriceTotal() {
        return priceTotal;
    }


    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public ReservationDuration getDuration() {
        return duration;
    }

}
