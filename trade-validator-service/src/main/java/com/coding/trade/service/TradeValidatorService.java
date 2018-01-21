package com.coding.trade.service;

import com.coding.trade.model.Trade;
import com.coding.trade.model.ValidationResult;
import com.coding.trade.type.TradeType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.List;

public interface TradeValidatorService {

    List<String> validate(Trade trade, BindingResult bindingResult);

    List<ValidationResult> validate(List<Trade> trade);

    Validator getValidator(TradeType tradeType);

}
