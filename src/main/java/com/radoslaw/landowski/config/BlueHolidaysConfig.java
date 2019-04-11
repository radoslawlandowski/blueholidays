package com.radoslaw.landowski.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlueHolidaysConfig {
    public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";

    private String applicationName;
}