package com.coding.trade.validator;

import com.coding.trade.model.Trade;
import com.coding.trade.service.TradeValidatorService;
import com.coding.trade.type.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TradeValidator extends AbstractValidator {

    @Autowired
    TradeValidatorService tradeValidatorService;

    @Override
    public void validate(Object obj, Errors errors) {
        Trade trade = (Trade) obj;
        tradeValidatorService.getValidator(TradeType.ALL).validate(trade,errors);
        if(!errors.hasErrors()){
            tradeValidatorService.getValidator(trade.getType()).validate(trade,errors);
        }
    }
}
