package com.radoslaw.landowski;

import com.radoslaw.landowski.config.BlueHolidaysConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableConfigurationProperties(BlueHolidaysConfig.class)
public class BlueHolidaysApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BlueHolidaysApplication.class, args);
	}

}
