package com.radoslaw.landowski.service.obtainers.calendarificcom;

import com.radoslaw.landowski.exceptions.HolidayObtainingRuntimeException;
import com.radoslaw.landowski.model.HolidayInfo;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComApiResponse;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComDate;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComHoliday;
import com.radoslaw.landowski.service.obtainers.calendarificcom.model.CalendarificComResponse;
import com.radoslaw.landowski.service.obtainers.calendarificcom.service.CalendarificComHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CalendarificComObtainerTests {

    @Mock
    CalendarificComHttpClient calendarificComHttpClient;

    CalendarificComObtainer obtainer;

    @Before
    public void before() {
        obtainer = new CalendarificComObtainer(calendarificComHttpClient);
    }

    @Test
    public void holidayInfoShouldBeReturnedForExistingHolidays() throws HolidayObtainingRuntimeException {
        int year = 2012;
        String countryCode = "PL";
        String holidayName = "Holiday name";
        LocalDate date = LocalDate.of(year, 1, 1);
        LocalDate expectedHolidayDate = LocalDate.of(year, 1, 5);
        HolidayInfo expectedInfo = new HolidayInfo(expectedHolidayDate, holidayName, holidayName);
        CalendarificComApiResponse apiResponse = getPrebuiltBasicApiResponse(expectedHolidayDate, holidayName);

        Mockito.when(calendarificComHttpClient.getHolidayResponse(countryCode, date)).thenReturn(apiResponse);

        HolidayInfo actualInfo = obtainer.get(countryCode, countryCode, date);

        assertEquals(expectedInfo, actualInfo);
    }

    @Test
    public void nullShouldBeReturnedForNonExistingHolidays() throws HolidayObtainingRuntimeException {
        int year = 2012;
        String countryCode = "PL";
        HolidayInfo expectedInfo = null;
        LocalDate date = LocalDate.of(year, 1, 1);
        CalendarificComApiResponse apiResponse = getPrebuiltBasicApiResponse(null, null);

        Mockito.when(calendarificComHttpClient.getHolidayResponse(countryCode, date)).thenReturn(apiResponse);

        HolidayInfo actualInfo = obtainer.get(countryCode, countryCode, date);

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
