package com.coding.trade.service.impl;

import com.coding.trade.exception.TradeValidationException;
import com.coding.trade.model.Trade;
import com.coding.trade.model.ValidationResult;
import com.coding.trade.service.StatsService;
import com.coding.trade.service.TradeValidatorService;
import com.coding.trade.type.RequestType;
import com.coding.trade.type.TradeType;
import com.coding.trade.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeValidatorServiceImpl implements TradeValidatorService{

    @Autowired
    SpotValidator spotValidator;

    @Autowired
    BaseValidator baseValidator;

    @Autowired
    ForwardValidator forwardValidator;

    @Autowired
    OptionValidator optionValidator;

    @Autowired
    StatsService statsService;

    @Autowired
    private TradeValidator tradeValidator;

    @Override
    public List<String> validate(Trade trade, BindingResult bindingResult) throws TradeValidationException {
        long startTime = System.currentTimeMillis();
        statsService.increamentRequestCount();
        tradeValidator.validate(trade,bindingResult);
        List<String> errors = new ArrayList<>();
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().stream().forEach(error -> {
                errors.add(error.getCode());
            });
        }else{
            errors.add("Valid Trade Entry");
        }
        statsService.decreamentRequestCount();
        statsService.logTimeTaken(RequestType.VALIDATE,System.currentTimeMillis()-startTime);
        return errors;
    }

    @Override
    public List<ValidationResult> validate(List<Trade> trades) throws TradeValidationException {
        long startTime = System.currentTimeMillis();
        statsService.increamentRequestCount();
        List<ValidationResult> validationResults = new ArrayList<>();
        trades.forEach(trade -> {
            BeanPropertyBindingResult errors = new BeanPropertyBindingResult(trade, Trade.class.getName());
            tradeValidator.validate(trade,errors);
            ValidationResult validationResult = new ValidationResult();
            validationResult.setTrade(trade);
            if(errors.hasErrors()){
                validationResult.setValid(false);
                List<String> err = new ArrayList<>();
                errors.getAllErrors().stream().forEach(error -> {
                    err.add(error.getCode());
                });
                validationResult.setErrors(err);
            }else{
                validationResult.setValid(true);
            }
            validationResults.add(validationResult);
        });
        statsService.decreamentRequestCount();
        statsService.logTimeTaken(RequestType.BULK_VALIDATE,System.currentTimeMillis()-startTime);
        return validationResults;
    }

    @Override
    public Validator getValidator(TradeType tradeType) {
        Validator validator = baseValidator;
        if(tradeType == TradeType.ALL){
            validator = baseValidator;
        }
        if(tradeType == TradeType.Spot){
            validator = spotValidator;
        }
        if(tradeType == TradeType.Forward){
            validator = forwardValidator;
        }
        if(tradeType == TradeType.VanillaOption){
            validator = optionValidator;
        }
        return validator;
    }
}
