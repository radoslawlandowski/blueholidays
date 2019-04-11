package com.radoslaw.landowski.service.obtainers.holidayapipl;

import com.radoslaw.landowski.model.HolidayInfo;
import com.radoslaw.landowski.service.obtainers.HolidayInfoObtainer;
import com.radoslaw.landowski.service.obtainers.holidayapipl.model.HolidayApiPlResponse;
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

/**
 * Service that uses holidayapi.pl as holiday info source. This is a secondary implementation made for showing the flexibility of
 * the general app mechanism. Although it works, it's deprecated due to necesity of making multiple requests. The
 * holidayapi.pl is only showing the next closest holidays instead of all of them for the given year
 */
@Deprecated
@Service("HolidayApiPlObtainer")
@Qualifier("HolidayApiPlObtainer")
public class HolidayApiPlObtainer implements HolidayInfoObtainer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HolidayApiPlObtainer.class);
    private final static String BASE_URL = "https://holidayapi.pl/v1/holidays";

    private RestTemplate restTemplate;
    private HttpEntity httpEntity;

    public HolidayApiPlObtainer(RestTemplate restTemplate) {
        LOGGER.debug("Initializing {}", HolidayApiPlObtainer.class);
        this.restTemplate = restTemplate;
        this.httpEntity = this.buildHttpEntity();
    }

    @Override
    public HolidayInfo get(String firstCountryCode, String secondCountryCode, LocalDate date) {
        HolidayApiPlResponse firstCountryHolidays = null;
        HolidayApiPlResponse secondCountryHolidays = null;

        LocalDate dateOfHolidays = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
        boolean commonHolidayFound = false;
        while (!commonHolidayFound) {
            LOGGER.info("Looking for holidays starting from: {}", dateOfHolidays);
            firstCountryHolidays = getHolidayInfo(BASE_URL, firstCountryCode, dateOfHolidays, true);
            LocalDate firstCountryHolidaysDate = firstCountryHolidays.getHolidays().get(0).getDate();
            LOGGER.info("Found holidays for: {} on: {}", firstCountryCode, firstCountryHolidaysDate.toString());

            secondCountryHolidays = getHolidayInfo(BASE_URL, secondCountryCode, firstCountryHolidaysDate, false);
            if (secondCountryHolidays.getHolidays().size() != 0) {
                commonHolidayFound = true;
                LOGGER.info("Found holidays for: {} on: {}", secondCountryCode, firstCountryHolidaysDate.toString());
            } else {
                dateOfHolidays = firstCountryHolidaysDate;
                LOGGER.info("Did not find holidays for: {} on: {}", secondCountryCode, firstCountryHolidaysDate.toString());
            }
        }

        return new HolidayInfo(firstCountryHolidays.getHolidays().get(0).getDate(),
                firstCountryHolidays.getHolidays().get(0).getName(),
                secondCountryHolidays.getHolidays().get(0).getName());
    }

    private HolidayApiPlResponse getHolidayInfo(String url, String countryCode, LocalDate date, boolean upcoming) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("country", countryCode)
                .queryParam("year", date.getYear())
                .queryParam("month", date.getMonthValue())
                .queryParam("day", date.getDayOfMonth());

        if (upcoming) {
            builder.queryParam("upcoming", upcoming); // The 'upcoming' param cannot be chained with others above due to HolidayApi.pl bug
        }

        String uriString = builder.toUriString();

        LOGGER.debug("Executing HTTP request with URI: {}", uriString);

        return restTemplate.exchange(
                uriString,
                HttpMethod.GET,
                this.httpEntity,
                HolidayApiPlResponse.class).getBody();
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
