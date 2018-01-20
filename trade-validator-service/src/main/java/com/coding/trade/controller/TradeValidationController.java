package com.coding.trade.controller;

import com.coding.trade.exception.TradeValidationException;
import com.coding.trade.model.Trade;
import com.coding.trade.service.TradeValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/api/trade/validator/")
public class TradeValidationController {

    @Autowired
    private TradeValidatorService tradeValidatorService;


    @PostMapping("valid")
    public String validate(@Valid @RequestBody Trade trade) throws TradeValidationException{
        return tradeValidatorService.validate(trade);
    }

    @ExceptionHandler({ TradeValidationException.class })
    public void handleException(TradeValidationException e) {
        System.out.println("TODO Move to log"+e.getMessage());
    }

}
