package com.example.github.model;

public class User {

    private String email;
    private String password;
    private String contact;
    private String address;
    private String lat;
    private String lang;
    private String city;

    public User(){}

    public User(String email, String password, String contact, String address, String lat, String lang, String city) {
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.address = address;
        this.lat = lat;
        this.lang = lang;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", lat='" + lat + '\'' +
                ", lang='" + lang + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
