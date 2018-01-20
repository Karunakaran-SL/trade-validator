package com.coding.trade.service;

import com.coding.trade.exception.TradeValidationException;
import com.coding.trade.model.Trade;

public interface TradeValidatorService {

    String validate(Trade trade) throws TradeValidationException;

}
