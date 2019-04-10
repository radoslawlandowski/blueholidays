package com.radoslaw.landowski.service.obtainers.calendarificcom.model;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalendarificComApiResponse {
    private ObjectNode meta;
    private CalendarificComResponse response;
}
