package com.coding.trade;

import com.coding.trade.model.Trade;
import com.coding.trade.model.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)   // 1
@SpringApplicationConfiguration(classes = TradeValidatorServiceApplication.class)   // 2
@WebAppConfiguration   // 3
@IntegrationTest("server.port:9090")
public class ShutdownTest {

    private static List<Trade> testData;

    @BeforeClass
    public static void setUp(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            File jsonInput = new File(TradeValidatorControllerTestTDD.class.getResource("/testData.json").getFile());
            testData = mapper.readValue(jsonInput,
                    mapper.getTypeFactory().constructCollectionType(List.class, Trade.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void testGarceFullShutdownUsingSpringActuator() throws Exception{
        ContinousPoll continousPoll = new ContinousPoll();
        Thread thread = new Thread(continousPoll);
        thread.start();
        Thread.sleep(1000);
        //call Shutdown
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<List<Trade>> request = new HttpEntity<>(testData);
        ResponseEntity<String> response  = restTemplate.exchange("http://localhost:9090/shutdown", HttpMethod.POST,null, String.class);
        assertEquals(response.getBody(),"{\"message\":\"Shutting down, bye...\"}");
    }

    @Test
    public void testGarceFullShutdownWithoutUsingSpringActuator() throws Exception{
        ContinousPoll continousPoll = new ContinousPoll();
        Thread thread = new Thread(continousPoll);
        thread.start();
        Thread.sleep(1000);
        //call Shutdown
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<List<Trade>> request = new HttpEntity<>(testData);
        ResponseEntity<String> response  = restTemplate.exchange("http://localhost:9090/api/shutdown", HttpMethod.POST,null, String.class);
        assertEquals("Shutdown successfully.",response.getBody());
        Thread.sleep(1000);
    }

    class ContinousPoll implements Runnable{
        @Setter
        private volatile boolean runpolling = true;

        @Override
        public void run() {
            while(runpolling){
                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<List<Trade>> request = new HttpEntity<>(testData);
                ResponseEntity< List < ValidationResult >> response  = restTemplate.exchange("http://localhost:9090/api/trade/bulk-valid", HttpMethod.POST,request,new ParameterizedTypeReference<List<ValidationResult>>() {});
                System.out.println(response);
            }
        }
    }

}
