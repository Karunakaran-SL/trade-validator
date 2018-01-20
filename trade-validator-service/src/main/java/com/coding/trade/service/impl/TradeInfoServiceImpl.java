package com.coding.trade.service.impl;

import com.coding.trade.service.TradeInfoService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TradeInfoServiceImpl implements TradeInfoService{

    //Fetch these details from DB
    private String[] customers = new String[]{"PLUTO1", "PLUTO2"};
    private String[] legalEntities = new String[]{"CS Zurich"};

    @Override
    public boolean isValidCounterPart(String customer) {
        return Arrays.asList(customers).contains(customer);
    }

    @Override
    public boolean isValidLegalEntity(String legalEntity) {
        return Arrays.asList(legalEntities).contains(legalEntity);
    }
}
