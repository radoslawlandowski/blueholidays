package com.radoslaw.landowski.service.obtainers.holidayapipl.model;

import java.util.List;

public class HolidayApiPlResponse {
    public List<Holiday> holidays;
    public int status;

    public HolidayApiPlResponse() {
    }

    public HolidayApiPlResponse(List<Holiday> holidays, int status) {
        this.holidays = holidays;
        this.status = status;
    }

    public List<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
