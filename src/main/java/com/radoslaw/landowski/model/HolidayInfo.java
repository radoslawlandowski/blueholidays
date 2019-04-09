package com.radoslaw.landowski.model;

import java.time.LocalDate;

public class HolidayInfo {
    private LocalDate date;
    private String firstCountryHolidayName;
    private String secondCountryHolidayName;

    public HolidayInfo(LocalDate date, String firstCountryHolidayName, String secondCountryHolidayName) {
        this.date = date;
        this.firstCountryHolidayName = firstCountryHolidayName;
        this.secondCountryHolidayName = secondCountryHolidayName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getFirstCountryHolidayName() {
        return firstCountryHolidayName;
    }

    public void setFirstCountryHolidayName(String firstCountryHolidayName) {
        this.firstCountryHolidayName = firstCountryHolidayName;
    }

    public String getSecondCountryHolidayName() {
        return secondCountryHolidayName;
    }

    public void setSecondCountryHolidayName(String secondCountryHolidayName) {
        this.secondCountryHolidayName = secondCountryHolidayName;
    }
}
