package com.example.github.model;

import java.util.List;

public class Restaurant {

    private String email;
    private String password;
    private String contact;
    private String address;
    private String lat;
    private String lang;
    private String startHour;
    private String startMinute;
    private String endHour;
    private String endMinute;
    private List<String> offDays;

    public Restaurant() {
    }

    public Restaurant(String email, String password, String contact, String address, String lat,
                      String lang, String startHour, String startMinute, String endHour,
                      String endMinute, List<String> offDays) {
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.address = address;
        this.lat = lat;
        this.lang = lang;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.offDays = offDays;
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

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(String startMinute) {
        this.startMinute = startMinute;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(String endMinute) {
        this.endMinute = endMinute;
    }

    public List<String> getOffDays() {
        return offDays;
    }

    public void setOffDays(List<String> offDays) {
        this.offDays = offDays;
    }
}
