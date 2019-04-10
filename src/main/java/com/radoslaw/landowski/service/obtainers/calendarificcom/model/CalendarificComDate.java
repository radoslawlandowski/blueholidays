package com.radoslaw.landowski.service.obtainers.calendarificcom.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalendarificComDate {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private String iso;
    private CalendarificComDateTime dateTime;

    public CalendarificComDate() { }

    public LocalDate getIso() {
        return LocalDate.parse(this.iso, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public CalendarificComDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(CalendarificComDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
