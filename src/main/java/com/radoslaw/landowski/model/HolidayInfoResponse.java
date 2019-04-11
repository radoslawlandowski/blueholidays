package com.radoslaw.landowski.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HolidayInfoResponse {
    private HolidayInfo holidayInfo;
}
