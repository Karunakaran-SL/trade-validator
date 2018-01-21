package com.coding.trade.type;

import com.coding.trade.validator.ForwardValidator;
import com.coding.trade.validator.OptionValidator;
import com.coding.trade.validator.SpotValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;

public enum TradeType {
    Spot,
    Forward,
    VanillaOption,
    ALL;
}
