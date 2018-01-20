package com.coding.trade.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class BaseValidator implements Validator{
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
