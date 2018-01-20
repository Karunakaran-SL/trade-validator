package com.coding.trade.type;

import com.coding.trade.validator.ForwardValidator;
import com.coding.trade.validator.OptionValidator;
import com.coding.trade.validator.SpotValidator;
import org.springframework.validation.Validator;

public enum TradeType {
    Spot(new SpotValidator()),
    Forward(new ForwardValidator()),
    VanillaOption(new OptionValidator());

    private final Validator validator;
    private TradeType(Validator validator){
        this.validator = validator;
    }

    public Validator getValidator(){
        return validator;
    }
}
