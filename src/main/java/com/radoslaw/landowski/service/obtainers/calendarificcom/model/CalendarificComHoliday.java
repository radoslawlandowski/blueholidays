package com.radoslaw.landowski.service.obtainers.calendarificcom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalendarificComHoliday {
    @NotNull
    private String name;
    private String description;
    @NotNull
    private CalendarificComDate date;
}
