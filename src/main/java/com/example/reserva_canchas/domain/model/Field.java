package com.example.reserva_canchas.domain.model;

import com.example.reserva_canchas.domain.exception.FieldWithAssignedIdException;
import com.example.reserva_canchas.domain.exception.PriceInvalid;

import java.math.BigDecimal;
import java.util.Objects;


public class Field {

    private Long id;
    private String name;
    private BigDecimal price;
    private boolean active;


    public Field(String name, BigDecimal price) {
        Objects.requireNonNull(name, "name es requerido");
        Objects.requireNonNull(price, "price es requerido");
        this.name = name;
        this.price = price;
        this.active = true;
    }

    public void assignId(Long id) {
        if (this.id != null) {
            throw new FieldWithAssignedIdException("La cancha ya tiene un id asignado");
        }
        this.id = id;
    }

    public void changePrice(BigDecimal newPrice) {
        if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PriceInvalid("El precio no puede ser menor o igual a 0");
        }

        if (price.equals(newPrice)) {
            throw new PriceInvalid("El precio no puede ser igual al anterior");
        }

        this.price = newPrice;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }

}
