package com.radoslaw.landowski;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
	"app.application-name=testValue",
})
public class BlueHolidaysApplicationTests {

	@Value("${app.application-name}")
	private String applicationName;

	@Autowired
	private MockMvc mvc;

	@Test
	public void calendarificWorks() throws Exception {
		this.mvc.perform(get("/?firstCountryCode=PL&secondCountryCode=DE&date=2012-11-01"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("{\"date\":\"2012-11-11\",\"firstCountryHolidayName\":\"Independence Day\",\"secondCountryHolidayName\":\"St. Martin's Day\"}"));
	}

}
