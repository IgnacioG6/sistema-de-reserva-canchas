package com.example.reserva_canchas.domain.model;

import com.example.reserva_canchas.domain.model.enums.TypeField;

import java.math.BigDecimal;


public class Field {

    private Long id;
    private String name;
    private TypeField type;
    private Location location;
    private BigDecimal price;
    private boolean active;

    public Field(Long id, String name, TypeField type, Location location, BigDecimal price, boolean active) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.price = price;
        this.active = active;
    }

    public Field(String name, TypeField type, Location location, BigDecimal price, boolean active) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.price = price;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeField getType() {
        return type;
    }

    public void setType(TypeField type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
