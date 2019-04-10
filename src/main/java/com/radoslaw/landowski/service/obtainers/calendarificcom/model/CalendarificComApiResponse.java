package com.radoslaw.landowski.service.obtainers.calendarificcom.model;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class CalendarificComApiResponse {
    private ObjectNode meta;
    private CalendarificComResponse response;

    public CalendarificComApiResponse() {
    }

    public ObjectNode getMeta() {
        return meta;
    }

    public void setMeta(ObjectNode meta) {
        this.meta = meta;
    }

    public CalendarificComResponse getResponse() {
        return response;
    }

    public void setResponse(CalendarificComResponse response) {
        this.response = response;
    }
}
