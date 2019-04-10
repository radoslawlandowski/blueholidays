package com.radoslaw.landowski.service.obtainers.calendarificcom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalendarificComDateTime {
    private int year;
    private int month;
    private int day;
}
