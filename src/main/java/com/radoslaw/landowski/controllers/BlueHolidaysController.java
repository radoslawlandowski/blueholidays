package com.radoslaw.landowski.controllers;

import com.radoslaw.landowski.config.BlueHolidaysConfig;
import com.radoslaw.landowski.model.HolidayInfo;
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

    @RequestMapping("/")
    public HolidayInfo getHolidayInfo(@RequestParam("firstCountryCode") @ISOCountryCode String firstCountryCode,
                                      @RequestParam("secondCountryCode") @ISOCountryCode String secondCountryCode,
                                      @RequestParam @NotNull @DateTimeFormat(pattern=BlueHolidaysConfig.INPUT_DATE_FORMAT) LocalDate date) {

        LOGGER.info("Requested for obtaining holiday info with: 'firstCountryCode': {}, secondCountryCode: {}, date: {}",
                firstCountryCode,
                secondCountryCode,
                date.toString());

        return this.holidayInfoObtainer.get(firstCountryCode, secondCountryCode, date);
    }
}