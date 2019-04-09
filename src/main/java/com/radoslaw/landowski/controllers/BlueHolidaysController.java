package com.radoslaw.landowski.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class BlueHolidaysController {

    private final static Logger logger = LoggerFactory.getLogger(BlueHolidaysController.class);

    @Value("${app.application-name}")
    private String applicationName;

    @RequestMapping("/")
    public String index() {
        String message = applicationName + " works!";
        logger.info(message);
        return message;
    }
}