package com.radoslaw.landowski.controllers;

import com.radoslaw.landowski.config.BlueHolidaysConfig;
import com.radoslaw.landowski.model.HolidayInfo;
import com.radoslaw.landowski.service.obtainers.HolidayInfoObtainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@RestController
public class BlueHolidaysController {
    private final static Logger LOGGER = LoggerFactory.getLogger(BlueHolidaysController.class);

    private HolidayInfoObtainer holidayInfoObtainer;

    public BlueHolidaysController(@Qualifier("CalendarificComObtainer") HolidayInfoObtainer holidayInfoObtainer) {
        this.holidayInfoObtainer = holidayInfoObtainer;
    }

    @RequestMapping("/")
    public HolidayInfo getHolidayInfo(@RequestParam("firstCountryCode") String firstCountryCode,
                             @RequestParam("secondCountryCode") String secondCountryCode,
                             @RequestParam @DateTimeFormat(pattern=BlueHolidaysConfig.INPUT_DATE_FORMAT) LocalDate date) {
        LOGGER.info("Requested for obtaining holiday info with: 'firstCountryCode': {}, secondCountryCode: {}, date: {}",
                firstCountryCode,
                secondCountryCode,
                date.toString());

        return this.holidayInfoObtainer.get(firstCountryCode, secondCountryCode, date);
    }
}