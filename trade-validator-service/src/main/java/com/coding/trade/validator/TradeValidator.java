package com.coding.trade.validator;

import com.coding.trade.model.Trade;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TradeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Trade.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        System.out.println("Hello");
    }
}
