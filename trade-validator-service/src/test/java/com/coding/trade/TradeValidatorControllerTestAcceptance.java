package com.coding.trade;

import com.coding.trade.model.Trade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TradeValidatorControllerTestAcceptance {

	private static List<Trade> testData;

	@Autowired
	private MockMvc mockMvc;

	@BeforeClass
	public static void setUp(){
		try {
			ObjectMapper mapper = new ObjectMapper();
			File jsonInput = new File(TradeValidatorControllerTestAcceptance.class.getResource("/testDataActual.json").getFile());
			testData = mapper.readValue(jsonInput,
                    mapper.getTypeFactory().constructCollectionType(List.class, Trade.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSpotNegative() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(0)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"As per SPOT Value Date should be two dates after the trade date\"]",content);
	}

	@Test
	public void testSpotSellNegative() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(1)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"As per SPOT Value Date should be two dates after the trade date\"]",content);
	}

	@Test
	public void testForwardSellNegative() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(2)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"As per Forward Value Date should be more than two dates after the trade date\"]",content);
	}

	@Test
	public void testForwardValueDateOnWeekEnd() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(3)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Value date cannot fall on weekend\"]",content);
	}

	@Test
	public void testForwardTradeGreaterThanValueDate() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(4)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Value date cannot be before trade date\"]",content);
	}

	@Test
	public void testForwardInvalidCustomerWithValueDateError() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(5)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Value date cannot be before trade date\",\"Unsupported counterparties\"]",content);
	}

	@Test
	public void testForwardInvalidCustomer() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(6)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Unsupported counterparties\"]",content);
	}


	//7

	@Test
	public void testOptionValid() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(7)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Valid Trade Entry\"]",content);
	}

	@Test
	public void testOptionalSellValid() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(8)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Valid Trade Entry\"]",content);
	}

	@Test
	public void testOptionalInvalidDeliveryDate() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(9)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Expiry date and premium date shall be before delivery date\"]",content);
	}

	@Test
	public void testOptionalAmericalValid() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(10)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Valid Trade Entry\"]",content);
	}

	@Test
	public void testOptinalAmericaSellValid() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(11)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Valid Trade Entry\"]",content);
	}

	@Test
	public void testOptionalAmericaInvalidDeliveryDate() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(12)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Expiry date and premium date shall be before delivery date\"]",content);
	}

	@Test
	public void testOptionalAmericaInvalidExcerciseStartDate() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(13)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"ExcerciseStartDate should be after the trade date\\nbut before the expiry date\"]",content);
	}

	@Test
	public void testOptionalInvalidCustomer() throws Exception{
		MvcResult result = mockMvc.perform(
				post("/api/trade/valid").contentType(
						MediaType.APPLICATION_JSON).content(getAsJson(testData.get(14)))).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		assertEquals("[\"Unsupported counterparties\"]",content);
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
