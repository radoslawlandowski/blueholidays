package com.radoslaw.landowski.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HolidayInfo {
    private LocalDate date;
    private String firstCountryHolidayName;
    private String secondCountryHolidayName;
}
