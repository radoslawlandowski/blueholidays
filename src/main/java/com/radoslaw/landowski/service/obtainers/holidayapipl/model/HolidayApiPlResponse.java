package com.radoslaw.landowski.service.obtainers.holidayapipl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HolidayApiPlResponse {
    public List<Holiday> holidays;
    public int status;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Holiday {
        public static final String DATE_FORMAT = "yyyy-MM-dd";

        private String name;
        private String country;
        private String date;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public LocalDate getDate() {
            return parseDate(date);
        }

        public void setDate(String date) {
            this.date = date;
        }

        private LocalDate parseDate(String date) {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
        }
    }

}
