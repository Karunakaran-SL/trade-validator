package com.coding.trade.service.impl;

import com.coding.trade.exception.TradeValidationException;
import com.coding.trade.model.Trade;
import com.coding.trade.service.TradeValidatorService;
import org.springframework.stereotype.Service;

@Service
public class TradeValidatorServiceImpl implements TradeValidatorService{
    @Override
    public String validate(Trade trade) throws TradeValidationException {
        return null;
    }
}
