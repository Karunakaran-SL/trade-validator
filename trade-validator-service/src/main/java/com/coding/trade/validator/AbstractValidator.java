package com.coding.trade.validator;

import com.coding.trade.model.Trade;
import org.springframework.validation.Validator;

import java.time.format.DateTimeFormatter;

public abstract class AbstractValidator implements Validator{
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean supports(Class<?> aClass) {
        return Trade.class.equals(aClass);
    }
}
