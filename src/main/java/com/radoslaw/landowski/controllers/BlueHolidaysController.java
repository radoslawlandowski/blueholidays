package com.radoslaw.landowski.controllers;

import com.radoslaw.landowski.config.BlueHolidaysConfig;
import com.radoslaw.landowski.exceptions.HolidayObtainingRuntimeException;
import com.radoslaw.landowski.model.HolidayInfoResponse;
import com.radoslaw.landowski.service.obtainers.HolidayInfoObtainer;
import com.radoslaw.landowski.validators.ISOCountryCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Validated
@RestController
public class BlueHolidaysController {
    private final static Logger LOGGER = LoggerFactory.getLogger(BlueHolidaysController.class);

    private HolidayInfoObtainer holidayInfoObtainer;

    public BlueHolidaysController(@Qualifier("CalendarificComObtainer") HolidayInfoObtainer holidayInfoObtainer) {
        this.holidayInfoObtainer = holidayInfoObtainer;
    }

    /**
     * @param firstCountryCode ISO-3166 country code to get common holidays for
     * @param secondCountryCode ISO-3166 country code to get common holidays for
     * @param date the date from which common holiday search starts
     * @return searches within the year taken from 'date'
 *             returns the nearest holiday names that happen in both countries in the same day past the given 'date'.
     *         returns 'null' if no commmon holidays are found for the given year in 'date'.
     * @throws HolidayObtainingRuntimeException
     */
    @RequestMapping("/")
    public HolidayInfoResponse getHolidayInfo(@RequestParam("firstCountryCode") @ISOCountryCode String firstCountryCode,
                                              @RequestParam("secondCountryCode") @ISOCountryCode String secondCountryCode,
                                              @RequestParam @NotNull @DateTimeFormat(pattern=BlueHolidaysConfig.INPUT_DATE_FORMAT) LocalDate date)
    throws HolidayObtainingRuntimeException {

        LOGGER.info("Requested for obtaining holiday info with: 'firstCountryCode': {}, secondCountryCode: {}, date: {}",
                firstCountryCode,
                secondCountryCode,
                date.toString());

        return HolidayInfoResponse.builder().holidayInfo(holidayInfoObtainer.get(firstCountryCode, secondCountryCode, date)).build();
    }
}