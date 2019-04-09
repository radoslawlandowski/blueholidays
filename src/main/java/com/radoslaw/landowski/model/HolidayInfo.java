package com.radoslaw.landowski.model;

import java.util.Date;

public class HolidayInfo {
    private Date date;
    private String firstCountryHolidayName;
    private String secondCountryHolidayName;

    public HolidayInfo(Date date, String firstCountryHolidayName, String secondCountryHolidayName) {
        this.date = date;
        this.firstCountryHolidayName = firstCountryHolidayName;
        this.secondCountryHolidayName = secondCountryHolidayName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
