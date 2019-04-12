package com.radoslaw.landowski.service.obtainers.calendarificcom.service;

import com.radoslaw.landowski.exceptions.HolidayObtainingRuntimeException;
import com.radoslaw.landowski.service.obtainers.calendarificcom.CalendarificComConfig;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Service
public class CalendarificComHttpClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(CalendarificComHttpClient.class);
    private RestTemplate restTemplate;

    private String baseUrl;
    private char[] apiKey;
    private HttpEntity httpEntity;

    public CalendarificComHttpClient(RestTemplate restTemplate, CalendarificComConfig config) {
        this.restTemplate = restTemplate;

        this.baseUrl = config.getBaseUrl();
        this.apiKey = config.getApiKey().toCharArray();

        this.httpEntity = this.buildHttpEntity();
    }

    public CalendarificComApiResponse getHolidayResponse(String countryCode, LocalDate date) throws HolidayObtainingRuntimeException {
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
