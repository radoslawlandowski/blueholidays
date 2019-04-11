package com.radoslaw.landowski.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class BlueHolidaysConfig {

    public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";

    private String applicationName;

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationName() {
        return applicationName;
    }
}