package com.coding.trade;

import com.coding.trade.model.Trade;
import com.coding.trade.model.ValidationResult;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TradeValidatorBulkControllerTDDTest {

	private static String testData;

	@Autowired
	private MockMvc mockMvc;

	@BeforeClass
	public static void setUp(){
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(TradeValidatorBulkControllerTDDTest.class.getResource("/testDataActual.json").getFile()));
			testData = new String(encoded, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testValidateBulk() throws Exception {
		MvcResult result = mockMvc.perform(
				post("/api/trade/bulk-valid").contentType(
						MediaType.APPLICATION_JSON).content(testData)).andExpect(
				status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		List<ValidationResult> results = mapper.readValue(content,
				mapper.getTypeFactory().constructCollectionType(List.class, ValidationResult.class));
		assertEquals(15,results.size());
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
