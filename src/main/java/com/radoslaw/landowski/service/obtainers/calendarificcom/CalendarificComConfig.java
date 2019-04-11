package com.radoslaw.landowski.service.obtainers.calendarificcom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "app.obtainers.calendarificcom")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalendarificComConfig {
    private String apiKey;
    private String baseUrl;
}
