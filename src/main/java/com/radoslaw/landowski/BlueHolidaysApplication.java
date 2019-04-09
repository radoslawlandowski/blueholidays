package com.radoslaw.landowski;

import com.radoslaw.landowski.config.BlueHolidaysConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BlueHolidaysConfig.class)
public class BlueHolidaysApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlueHolidaysApplication.class, args);
	}

}
