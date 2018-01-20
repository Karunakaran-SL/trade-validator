package com.coding.trade.service.impl;

import com.coding.trade.exception.TradeValidationException;
import com.coding.trade.model.Trade;
import com.coding.trade.service.TradeValidatorService;
import com.coding.trade.validator.TradeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeValidatorServiceImpl implements TradeValidatorService{

    @Autowired
    private TradeValidator tradeValidator;

    @Override
    public List<String> validate(Trade trade, BindingResult bindingResult) throws TradeValidationException {
        tradeValidator.validate(trade,bindingResult);
        List<String> errors = new ArrayList<>();
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().stream().forEach(error -> {
                errors.add(error.getCode());
            });
        }else{
            errors.add("Valid Trade Entry");
        }
        return errors;
    }
}
