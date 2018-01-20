package com.coding.trade.service;

import com.coding.trade.exception.TradeValidationException;
import com.coding.trade.model.Trade;
import com.coding.trade.model.ValidationResult;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface TradeValidatorService {

    List<String> validate(Trade trade, BindingResult bindingResult) throws TradeValidationException;

    List<ValidationResult> validate(List<Trade> trade) throws TradeValidationException;

}
