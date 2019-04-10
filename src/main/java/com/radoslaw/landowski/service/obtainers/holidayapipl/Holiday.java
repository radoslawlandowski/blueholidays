package com.radoslaw.landowski.service.obtainers.holidayapipl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Holiday {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private String name;
    private String country;
    private String date;

    public Holiday() {
    }

    public Holiday(String name, String country, String date) {
        this.name = name;
        this.country = country;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getDate() {
        return parseDate(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}