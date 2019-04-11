package com.radoslaw.landowski.service.obtainers.holidayapipl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HolidayApiPlResponse {
    public List<Holiday> holidays;
    public int status;
}
