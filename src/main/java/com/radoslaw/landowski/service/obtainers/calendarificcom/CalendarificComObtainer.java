package com.radoslaw.landowski.service.obtainers.calendarificcom;

import com.radoslaw.landowski.exceptions.HolidayObtainingRuntimeException;
import com.radoslaw.landowski.model.HolidayInfo;
import com.radoslaw.landowski.service.obtainers.HolidayInfoObtainer;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComApiResponse;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComHoliday;
import com.radoslaw.landowski.service.obtainers.calendarificcom.service.CalendarificComHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service that uses calendarific.com as holiday info source. Calendarific returns holidays for a given country
 */
@Service("CalendarificComObtainer")
@Qualifier("CalendarificComObtainer")
public class CalendarificComObtainer implements HolidayInfoObtainer {
    private final static Logger LOGGER = LoggerFactory.getLogger(CalendarificComObtainer.class);

    private CalendarificComHttpClient client;

    public CalendarificComObtainer(CalendarificComHttpClient client) {
        this.client = client;
    }

    @Override
    public HolidayInfo get(String firstCountryCode, String secondCountryCode, LocalDate date) throws HolidayObtainingRuntimeException {
        List<List<CalendarificComHoliday>> holidays = new ArrayList<>();

        Stream.of(firstCountryCode, secondCountryCode).parallel().forEachOrdered((code) -> {
            CalendarificComApiResponse holidaysResponse = client.getHolidayResponse(code, date);
            holidays.add(getSortedHolidaysAfter(holidaysResponse, date));
        });

        return findNearestCommonHolidays(holidays.get(0), holidays.get(1)).orElse(null);
    }

    private Optional<HolidayInfo> findNearestCommonHolidays(List<CalendarificComHoliday> firstCountryHolidays, List<CalendarificComHoliday> secondCountryHolidays) {
        for (int i = 0 ; i < firstCountryHolidays.size(); i++) {
            for (int j = 0 ; j < secondCountryHolidays.size(); j++) {
                if(secondCountryHolidays.get(j).getDate().getIso().isAfter(firstCountryHolidays.get(i).getDate().getIso())) {
                    break; // If the second date is already after the first one there is no point to look for equality with further dates.
                }
                if(firstCountryHolidays.get(i).getDate().getIso().isEqual(secondCountryHolidays.get(j).getDate().getIso())) {
                    CalendarificComHoliday firstCountryHoliday = firstCountryHolidays.get(i);
                    CalendarificComHoliday secondCountryHoliday = secondCountryHolidays.get(j);

                    return Optional.of(new HolidayInfo(firstCountryHoliday.getDate().getIso(), firstCountryHoliday.getName(), secondCountryHoliday.getName()));
                }
            }
        }

        return Optional.empty();
    }

    private List<CalendarificComHoliday> getSortedHolidaysAfter(CalendarificComApiResponse response, LocalDate date) {
        LOGGER.info("Getting sorted holidays occurring after {}", date);

        return response.getResponse().getHolidays()
                .stream()
                .filter(holiday -> holiday.getDate().getIso().isAfter(date))
                .sorted(Comparator.comparing(h -> h.getDate().getIso()))
                .collect(Collectors.toList());
    }
}
