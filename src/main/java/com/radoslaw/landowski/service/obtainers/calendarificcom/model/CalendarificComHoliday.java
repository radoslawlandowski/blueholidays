package com.radoslaw.landowski.service.obtainers.calendarificcom.model;

public class CalendarificComHoliday {
    private String name;
    private String description;
    private CalendarificComDate date;

    public CalendarificComHoliday() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CalendarificComDate getDate() {
        return date;
    }

    public void setDate(CalendarificComDate date) {
        this.date = date;
    }
}
