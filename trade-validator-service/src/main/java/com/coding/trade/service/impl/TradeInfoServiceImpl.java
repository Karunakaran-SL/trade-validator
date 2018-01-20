package com.coding.trade.service.impl;

import com.coding.trade.service.TradeInfoService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TradeInfoServiceImpl implements TradeInfoService{

    private String[] customers = new String[]{"PLUTO1", "PLUTO2"};

    @Override
    public boolean isValidCounterPart(String customer) {
        return Arrays.asList(customers).contains(customer);
    }
}
