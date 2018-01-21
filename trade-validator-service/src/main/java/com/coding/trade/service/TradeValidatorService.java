package com.coding.trade.service;

import com.coding.trade.exception.TradeValidationException;
import com.coding.trade.model.Trade;
import com.coding.trade.model.ValidationResult;
import com.coding.trade.type.TradeType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.List;

public interface TradeValidatorService {

    List<String> validate(Trade trade, BindingResult bindingResult) throws TradeValidationException;

    List<ValidationResult> validate(List<Trade> trade) throws TradeValidationException;

    Validator getValidator(TradeType tradeType);

}
