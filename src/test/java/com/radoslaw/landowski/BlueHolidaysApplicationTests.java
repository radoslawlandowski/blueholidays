package com.radoslaw.landowski;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radoslaw.landowski.exceptionhandlers.ExceptionHandlers;
import com.radoslaw.landowski.model.ErrorResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void gettingHolidayInfoWithValidArgumentsReturnsProperBody() throws Exception {
		this.mvc.perform(get("/?firstCountryCode=PL&secondCountryCode=DE&date=2012-11-01"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("{\"holidayInfo\":{\"date\":\"2012-11-11\",\"firstCountryHolidayName\":\"Independence Day\",\"secondCountryHolidayName\":\"St. Martin's Day\"}}"));
	}

	@Test
	public void gettingHolidayInfoWithValidArgumentsButNoHolidaysFoundReturnsProperBody() throws Exception {
		this.mvc.perform(get("/?firstCountryCode=PL&secondCountryCode=PL&date=2012-12-31"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("{\"holidayInfo\":null}"));
	}

	@Test
	public void gettingHolidayInfoWithInValidSecondCountryCodeReturns4xx() throws Exception {
		String unsupportedCountryCode = "BROKEN!!!!CODE";
		String response = this.mvc.perform(get(String.format("/?firstCountryCode=PL&secondCountryCode=%s&date=2012-11-01", unsupportedCountryCode)))
				.andDo(print())
				.andExpect(status().is4xxClientError()).andReturn().getResponse().getContentAsString();
        ErrorResponse errorResponse = mapper.readValue(response, ErrorResponse.class);

        assertEquals(ExceptionHandlers.CONSTRAINT_VILOATION_DESCRIPTION, errorResponse.getDescription());
        List<ErrorResponse.Problem> problems = errorResponse.getProblems();
        assertEquals(1, problems.size());
        assertEquals(unsupportedCountryCode, problems.get(0).getValue());
	}

	@Test
	public void gettingHolidayInfoWithInValidFirstCountryCodeReturns4xx() throws Exception {
		String unsupportedCountryCode = "BROKEN!!!!CODE";
		String response = this.mvc.perform(get(String.format("/?firstCountryCode=%s&secondCountryCode=PL&date=2012-11-01", unsupportedCountryCode)))
				.andDo(print())
				.andExpect(status().is4xxClientError()).andReturn().getResponse().getContentAsString();
		ErrorResponse errorResponse = mapper.readValue(response, ErrorResponse.class);

		assertEquals(ExceptionHandlers.CONSTRAINT_VILOATION_DESCRIPTION, errorResponse.getDescription());
		List<ErrorResponse.Problem> problems = errorResponse.getProblems();
		assertEquals(1, problems.size());
		assertEquals(unsupportedCountryCode, problems.get(0).getValue());
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
