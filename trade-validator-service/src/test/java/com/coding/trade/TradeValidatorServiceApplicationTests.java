package com.coding.trade;

import com.coding.trade.controller.TradeValidationController;
import com.coding.trade.model.Trade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeValidatorServiceApplicationTests {

	private static List<Trade> testData;

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

	@Test
	public void testControllerInjected(){
		assertNotNull(tradeValidationController);
	}

	@Test
	public void testValidate(){

	}

}
