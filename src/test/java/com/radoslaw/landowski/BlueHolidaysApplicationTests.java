package com.radoslaw.landowski;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BlueHolidaysApplicationTests {
	@Autowired
	private MockMvc mvc;

	@Test
	public void gettingHolidayInfoWithValidArgumentsReturnsProperBody() throws Exception {
		this.mvc.perform(get("/?firstCountryCode=PL&secondCountryCode=DE&date=2012-11-01"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("{\"date\":\"2012-11-11\",\"firstCountryHolidayName\":\"Independence Day\",\"secondCountryHolidayName\":\"St. Martin's Day\"}"));
	}

	@Test
	public void gettingHolidayInfoWithInValidCountryCodeReturns4xxCode() throws Exception {
		String unsupportedCountryCode = "BROKEN!!!!CODE";
		this.mvc.perform(get(String.format("/?firstCountryCode=PL&secondCountryCode=%s&date=2012-11-01", unsupportedCountryCode)))
				.andDo(print())
				.andExpect(status().is4xxClientError());

		this.mvc.perform(get(String.format("/?firstCountryCode=%s&secondCountryCode=PL&date=2012-11-01", unsupportedCountryCode)))
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void gettingHolidayInfoWithMissingParametersReturns4xxCode() throws Exception {
		this.mvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().is4xxClientError());

		this.mvc.perform(get("/?firstCountryCode=PL"))
				.andDo(print())
				.andExpect(status().is4xxClientError());

		this.mvc.perform(get("/?secondCountryCode=PL"))
				.andDo(print())
				.andExpect(status().is4xxClientError());

		this.mvc.perform(get("/?date=2012-11-01"))
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void gettingHolidayInfoWithEmptyParametersReturns4xxCode() throws Exception {
		this.mvc.perform(get("/?firstCountryCode=&secondCountryCode=&date=2012-11-01"))
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}
}
