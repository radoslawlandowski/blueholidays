package com.radoslaw.landowski.service.obtainers.calendarificcom;

import com.radoslaw.landowski.model.HolidayInfo;
import com.radoslaw.landowski.service.obtainers.HolidayInfoObtainer;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComApiResponse;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComHoliday;
import com.radoslaw.landowski.service.obtainers.holidayapipl.HolidayApiPlResponse;
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

/**
 * Service that uses calendarific.com as holiday info source.
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
        LOGGER.debug("Initializing {}", CalendarificComObtainer.class);
        this.baseUrl = config.getBaseUrl();
        this.apiKey = config.getApiKey().toCharArray();
        this.restTemplate = restTemplate;
        this.httpEntity = this.buildHttpEntity();
    }

    @Override
    public HolidayInfo get(String firstCountryCode, String secondCountryCode, LocalDate date) {
        CalendarificComApiResponse firstCountryHolidaysInfo = getHolidayInfo(firstCountryCode, date);
        List<CalendarificComHoliday> firstCountryHolidays = firstCountryHolidaysInfo.getResponse().getHolidays()
                .stream()
                .filter(holiday -> holiday.getDate().getIso().isAfter(date))
                .sorted(Comparator.comparing(h -> h.getDate().getIso()))
                .collect(Collectors.toList());

        CalendarificComApiResponse secondCountryHolidaysInfo = getHolidayInfo(secondCountryCode, date);
        List<CalendarificComHoliday> secondCountryHolidays = secondCountryHolidaysInfo.getResponse().getHolidays()
                .stream()
                .filter(holiday -> holiday.getDate().getIso().isAfter(date))
                .sorted(Comparator.comparing(h -> h.getDate().getIso()))
                .collect(Collectors.toList());

        HolidayInfo commonHolidays = null;

        for (int i = 0 ; i < firstCountryHolidays.size() && commonHolidays == null; i++) {
            for (int j = 0 ; j < secondCountryHolidays.size() && commonHolidays == null; j++) {
                if(firstCountryHolidays.get(i).getDate().getIso().isEqual(secondCountryHolidays.get(j).getDate().getIso())) {
                    CalendarificComHoliday firstCountryHoliday = firstCountryHolidays.get(i);
                    CalendarificComHoliday secondCountryHoliday = secondCountryHolidays.get(j);

                    commonHolidays = new HolidayInfo(firstCountryHoliday.getDate().getIso(), firstCountryHoliday.getName(), secondCountryHoliday.getName());

                    break;
                }
            }
        }

        return commonHolidays;
    }

    private CalendarificComApiResponse getHolidayInfo(String countryCode, LocalDate date) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.baseUrl)
                .queryParam("country", countryCode)
                .queryParam("year", date.getYear())
                .queryParam("api_key", String.valueOf(this.apiKey));


        String uriString = builder.toUriString();

        LOGGER.info("Executing HTTP request with URI: {}", uriString);

        return restTemplate.exchange(
                uriString,
                HttpMethod.GET,
                this.httpEntity,
                CalendarificComApiResponse.class).getBody();
    }

    private HttpEntity buildHttpEntity() {
        LOGGER.debug("Building http headers and http entity");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("User-Agent", "rl");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        return entity;
    }
}
