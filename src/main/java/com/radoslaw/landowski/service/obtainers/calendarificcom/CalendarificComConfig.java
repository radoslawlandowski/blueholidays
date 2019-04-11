package com.radoslaw.landowski.service.obtainers.calendarificcom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.obtainers.calendarificcom")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalendarificComConfig {
    private String apiKey;
    private String baseUrl;
}
