package com.example.reserva_canchas.domain.model;

import com.example.reserva_canchas.domain.model.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    public Reservation(Long id, User user, Field field, LocalDate date,
                       LocalTime startTime, LocalTime endTime, ReservationStatus status, BigDecimal priceTotal, LocalDateTime dateCreation) {
        this.id = id;
        this.user = user;
        this.field = field;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.priceTotal = priceTotal;
        this.dateCreation = dateCreation;
    }

    public Reservation(User user, Field field, LocalDate date,
                       LocalTime startTime, LocalTime endTime, ReservationStatus status, BigDecimal priceTotal, LocalDateTime dateCreation) {
        this.user = user;
        this.field = field;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.priceTotal = priceTotal;
        this.dateCreation = dateCreation;
    }

    public void cancel() {
        if (this.status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("The reservation has already been cancelled");
        }

        LocalDateTime dateReservation = LocalDateTime.of(date, startTime);

        if (dateReservation.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Past reservations cannot be cancelled");
        }

        setStatus(ReservationStatus.CANCELLED);

    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
