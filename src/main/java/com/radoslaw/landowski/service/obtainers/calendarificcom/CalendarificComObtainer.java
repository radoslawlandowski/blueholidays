package com.radoslaw.landowski.service.obtainers.calendarificcom;

import com.radoslaw.landowski.exceptions.HolidayObtainingRuntimeException;
import com.radoslaw.landowski.model.HolidayInfo;
import com.radoslaw.landowski.service.obtainers.HolidayInfoObtainer;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComApiResponse;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComHoliday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
    private String baseUrl;
    private char[] apiKey;

    private RestTemplate restTemplate;
    private HttpEntity httpEntity;

    public CalendarificComObtainer(RestTemplate restTemplate, CalendarificComConfig config) {
        this.baseUrl = config.getBaseUrl();
        this.apiKey = config.getApiKey().toCharArray();
        this.restTemplate = restTemplate;
        this.httpEntity = this.buildHttpEntity();
    }

    @Override
    public HolidayInfo get(String firstCountryCode, String secondCountryCode, LocalDate date) throws HolidayObtainingRuntimeException {
        List<List<CalendarificComHoliday>> holidays = Stream.of(firstCountryCode, secondCountryCode).parallel().map((code) -> {
            CalendarificComApiResponse holidaysResponse = getHolidayResponse(code, date);
            return getSortedHolidaysAfter(holidaysResponse, date);
        }).collect(Collectors.toList());

        return findNearestCommonHolidays(holidays.get(0), holidays.get(1)).orElse(null);
    }

    private Optional<HolidayInfo> findNearestCommonHolidays(List<CalendarificComHoliday> firstCountryHolidays, List<CalendarificComHoliday> secondCountryHolidays) {
        for (int i = 0 ; i < firstCountryHolidays.size(); i++) {
            for (int j = 0 ; j < secondCountryHolidays.size(); j++) {
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

    private CalendarificComApiResponse getHolidayResponse(String countryCode, LocalDate date) throws HolidayObtainingRuntimeException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.baseUrl)
                .queryParam("country", countryCode)
                .queryParam("year", date.getYear())
                .queryParam("api_key", String.valueOf(this.apiKey));

        String uriString = builder.toUriString();

        LOGGER.info("Executing HTTP request with URI: {}", uriString);

        CalendarificComApiResponse response;
        try {
            response = restTemplate.exchange(
                    uriString,
                    HttpMethod.GET,
                    this.httpEntity,
                    CalendarificComApiResponse.class).getBody();
        } catch(Exception e) {
            throw new HolidayObtainingRuntimeException(e);
        }

        return response;
    }

    private HttpEntity buildHttpEntity() {
        LOGGER.info("Building http headers and http entity");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("User-Agent", "rl");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        return entity;
    }
}
