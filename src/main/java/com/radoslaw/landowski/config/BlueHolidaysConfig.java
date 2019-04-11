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
    /*
    * !REVIEW: This field is intentionally put here instead of application.properties. It makes it easier to pass to Constraint Validator (DateTimeFormat)
    * in BlueHolidaysController. Although it is possible to set static field from application.properties I find it unclear
    * and hacky. Considering the time given for the task I find it a good solution.
    */
    public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";

    private String applicationName;
}