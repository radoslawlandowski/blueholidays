package com.radoslaw.landowski.service.obtainers.calendarificcom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.obtainers.calendarificcom")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalendarificComConfig {
    private final static Logger LOGGER = LoggerFactory.getLogger(CalendarificComConfig.class);

    private String apiKey;
    private String baseUrl;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        warnIfEmpty(apiKey, "apiKey");
        checkEnvVariable(apiKey, "apiKey");

        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        warnIfEmpty(baseUrl, "baseUrl");
        this.baseUrl = baseUrl;
    }

    private void warnIfEmpty(String prop, String propName) {
        if (prop == null || prop.isEmpty()) {
            LOGGER.warn("==============================================================================");
            LOGGER.warn("The {} is null or empty, it might make the application constantly throwing ISE!" +
                    " Make sure that your configuration includes proper fields and values and environment variables are set!", propName);
            LOGGER.warn("==============================================================================");
        }
    }

    private void checkEnvVariable(String prop, String propName) {
        if (prop.contains("${")) {
            String envVarName = prop.substring(2, prop.length() - 1); // Extract pure env variable name from '${}' notation in application.properties
            String envVarValue = System.getenv(envVarName);
            warnIfEmpty(envVarValue, envVarName);
        }
    }
}
