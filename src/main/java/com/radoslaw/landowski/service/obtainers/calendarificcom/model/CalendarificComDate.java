package com.radoslaw.landowski.service.obtainers.calendarificcom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarificComDate {
    /* !REVIEW: The date format is intentionally put in this model and conversion happens here. I did not exclude it into
     * a CalendarificComConfig because that could suggest that this value is somehow configurable, which it is not.
    */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @NotNull
    private LocalDate iso;

    private CalendarificComDateTime dateTime;

    public LocalDate getIso() {
        return this.iso;
    }

    /* !REVIEW: Although these model classes are supposed to be as logicless as possible I simply find it easy, convenient
    * and comfortable to convert the dates in this setter. It makes the life easier and code cleaner.
    * THe input string must be substringed because API sometimes returns date as: "yyyy-MM-dd" and sometimes "yyyy-MM-dd:hh:mm:ss"
    */
    public void setIso(String iso) {
        this.iso = LocalDate.parse(iso.substring(0, DATE_FORMAT.length()), DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public CalendarificComDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(CalendarificComDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
