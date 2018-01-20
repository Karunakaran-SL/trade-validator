package com.coding.trade;

import com.coding.trade.controller.TradeValidationController;
import com.coding.trade.exception.TradeValidationException;
import com.coding.trade.model.Trade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindingResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TradeValidatorServiceApplicationTests {

	private static List<Trade> testData;

	@Autowired
	private MockMvc mockMvc;

	@BeforeClass
	public static void setUp(){
		try {
			ObjectMapper mapper = new ObjectMapper();
			File jsonInput = new File(TradeValidatorServiceApplicationTests.class.getResource("/testData.json").getFile());
			testData = mapper.readValue(jsonInput,
                    mapper.getTypeFactory().constructCollectionType(List.class, Trade.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Autowired
	TradeValidationController tradeValidationController;

	//@Test
	public void testControllerInjected(){
		assertNotNull(tradeValidationController);
	}

	@Test
	public void testValidateForWrongValueDate() throws Exception {

		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(0)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Value date cannot be before trade date\"]",content);
	}

	@Test
	public void testValidateForWeekendValueDate() throws Exception {

		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(1)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Value date cannot fall on weekend\"]",content);
	}

	@Test
	public void testValidateForHolidayValueDate() throws Exception {

		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(2)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Value date cannot fall on non-working day for currency\"]",content);
	}

	private String getAsJson(Trade trade) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(trade);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

}
