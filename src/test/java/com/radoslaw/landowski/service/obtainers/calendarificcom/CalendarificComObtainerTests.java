package com.radoslaw.landowski.service.obtainers.calendarificcom;

import com.radoslaw.landowski.exceptions.HolidayObtainingRuntimeException;
import com.radoslaw.landowski.model.HolidayInfo;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComApiResponse;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComDate;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComHoliday;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class CalendarificComObtainerTests {

    public static final String API_KEY = "my-api-key";
    public static final String BASE_URL = "http://my-base-url";


    @Mock()
    RestTemplate restTemplate;

    @Mock
    CalendarificComConfig calendarificComConfig;

    CalendarificComObtainer obtainer;

    @Before
    public void before() {
        Mockito.when(calendarificComConfig.getApiKey()).thenReturn(API_KEY);
        Mockito.when(calendarificComConfig.getBaseUrl()).thenReturn(BASE_URL);

        obtainer = new CalendarificComObtainer(restTemplate, calendarificComConfig);
    }

    @Test
    public void holidayInfoShouldBeReturnedForExistingHolidays() throws HolidayObtainingRuntimeException {
        int year = 2012;
        String countryCode = "PL";
        String holidayName = "Holiday name";
        String expectedUrl = String.format("%s?country=%s&year=%d&api_key=%s", BASE_URL, countryCode, year, API_KEY);
        LocalDate expectedHolidayDate = LocalDate.of(year, 1, 5);
        HolidayInfo expectedInfo = new HolidayInfo(expectedHolidayDate, holidayName, holidayName);
        CalendarificComApiResponse apiResponse = getPrebuiltBasicApiResponse(expectedHolidayDate, holidayName);

        Mockito.when(restTemplate.exchange(eq(expectedUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(CalendarificComApiResponse.class)))
                .thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));

        HolidayInfo actualInfo = obtainer.get(countryCode, countryCode, LocalDate.of(year, 1, 1));

        assertEquals(expectedInfo, actualInfo);
    }

    @Test
    public void nullShouldBeReturnedForNonExistingHolidays() throws HolidayObtainingRuntimeException {
        int year = 2012;
        String countryCode = "PL";
        String expectedUrl = String.format("%s?country=%s&year=%d&api_key=%s", BASE_URL, countryCode, year, API_KEY);
        HolidayInfo expectedInfo = null;
        CalendarificComApiResponse apiResponse = getPrebuiltBasicApiResponse(null, null);

        Mockito.when(restTemplate.exchange(eq(expectedUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(CalendarificComApiResponse.class)))
                .thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));

        HolidayInfo actualInfo = obtainer.get(countryCode, countryCode, LocalDate.of(year, 1, 1));

        assertEquals(expectedInfo, actualInfo);
    }

    private CalendarificComApiResponse getPrebuiltBasicApiResponse(LocalDate date, String name) throws HolidayObtainingRuntimeException {
        CalendarificComApiResponse apiResponse = CalendarificComApiResponse.builder()
                .response(CalendarificComResponse.builder().build()).build();

        List<CalendarificComHoliday> holidays = new ArrayList<>();
        if (date != null) {
            holidays = Arrays.asList(getHoliday(date, name));
        }

        apiResponse.getResponse().setHolidays(holidays);

        return apiResponse;
    }

    private CalendarificComHoliday getHoliday(LocalDate date, String name) {
        return CalendarificComHoliday.builder().name(name).date(CalendarificComDate.builder().iso(date).build()).build();
    }
}
