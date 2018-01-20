package com.coding.trade.validator;

import com.coding.trade.model.Trade;
import com.coding.trade.type.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TradeValidator implements Validator {

    @Autowired
    SpotValidator spotValidator;

    @Autowired
    BaseValidator baseValidator;

    @Autowired
    ForwardValidator forwardValidator;

    @Autowired
    OptionValidator optionValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return Trade.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Trade trade = (Trade) obj;
        baseValidator.validate(trade,errors);
        if(!errors.hasErrors()){
            if(trade.getType() == TradeType.Spot){
                spotValidator.validate(trade,errors);
            }else if(trade.getType() == TradeType.Forward){
                forwardValidator.validate(trade,errors);
            }else if(trade.getType() == TradeType.VanillaOption){
                optionValidator.validate(trade,errors);
            }
        }
    }
}
