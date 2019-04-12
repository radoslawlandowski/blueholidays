package com.radoslaw.landowski.service.obtainers.calendarificcom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalendarificComResponse {
    @NotNull
    private List<CalendarificComHoliday> holidays;
}
