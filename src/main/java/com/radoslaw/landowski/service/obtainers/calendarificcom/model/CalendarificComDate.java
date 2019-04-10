package com.radoslaw.landowski.service.obtainers.calendarificcom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarificComDate {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private LocalDate iso;
    private CalendarificComDateTime dateTime;

    public LocalDate getIso() {
        return this.iso;
    }

    public void setIso(String iso) {
        // TODO: Implement a proper date sanitizer/normalizer due to multiple date format returned from api
        this.iso = LocalDate.parse(iso.substring(0, 10), DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public CalendarificComDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(CalendarificComDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
