package com.example.reserva_canchas.domain.model;

public class Location {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String province;
    private String telphone;
    private String email;

    public Location(Long id, String name, String address, String city, String province, String telphone, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.province = province;
        this.telphone = telphone;
        this.email = email;
    }

    public Location(String name, String address, String city, String province, String telphone, String email) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.province = province;
        this.telphone = telphone;
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
