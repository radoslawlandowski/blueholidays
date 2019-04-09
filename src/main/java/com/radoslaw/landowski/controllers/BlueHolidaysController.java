package com.radoslaw.landowski.controllers;

import com.radoslaw.landowski.config.BlueHolidaysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@RestController
public class BlueHolidaysController {
    private final static Logger logger = LoggerFactory.getLogger(BlueHolidaysController.class);

    @RequestMapping("/")
    public String index(@RequestParam("firstCountryCode") String firstCountryCode,
                        @RequestParam("secondCountryCode") String secondCountryCode,
                        @RequestParam @DateTimeFormat(pattern=BlueHolidaysConfig.INPUT_DATE_FORMAT) Date date) {
        logger.info(firstCountryCode);
        logger.info(secondCountryCode);
        logger.info(date.toString());

        return date.toString();
    }
}