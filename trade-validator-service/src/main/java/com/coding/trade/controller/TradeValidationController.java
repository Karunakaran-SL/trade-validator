package com.coding.trade.controller;

import com.coding.trade.exception.ShutdownInProgressException;
import com.coding.trade.exception.TradeValidationException;
import com.coding.trade.model.Trade;
import com.coding.trade.model.ValidationResult;
import com.coding.trade.service.ShutdownService;
import com.coding.trade.service.StatsService;
import com.coding.trade.service.TradeValidatorService;
import com.coding.trade.validator.TradeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.management.ServiceNotFoundException;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class TradeValidationController {

    @Autowired
    private TradeValidatorService tradeValidatorService;

    @Autowired
    ShutdownService shutdownService;

    @PostMapping("/api/trade/valid")
    public List<String> validate(@Valid @RequestBody Trade trade, BindingResult bindingResult){
        if(shutdownService.canAcceptRequest()) {
            try {
                return tradeValidatorService.validate(trade, bindingResult);
            } catch (TradeValidationException e) {
                return Arrays.asList(e.getMessage());
            }
        }
        throw new ShutdownInProgressException();
    }

    @PostMapping("/api/trade/bulk-valid")
    public List<ValidationResult> validate(@Valid @RequestBody List<Trade> trades){
        if(shutdownService.canAcceptRequest()) {
            try {
                return tradeValidatorService.validate(trades);
            } catch (TradeValidationException e) {
                return Collections.emptyList();
            }
        }
        throw new ShutdownInProgressException();
    }

    /*@ExceptionHandler({ShutdownInProgressException.class})
    public void handleException() {

    }*/
}
