package com.radoslaw.landowski.service.obtainers.calendarificcom.model;

import java.util.List;

public class CalendarificComResponse {
    List<CalendarificComHoliday> holidays;

    public CalendarificComResponse() {
    }

    public List<CalendarificComHoliday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<CalendarificComHoliday> holidays) {
        this.holidays = holidays;
    }
}
