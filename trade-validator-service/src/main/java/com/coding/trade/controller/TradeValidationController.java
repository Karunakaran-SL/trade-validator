package com.coding.trade.controller;

import com.coding.trade.exception.TradeValidationException;
import com.coding.trade.model.Trade;
import com.coding.trade.service.TradeValidatorService;
import com.coding.trade.validator.TradeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class TradeValidationController {

    @Autowired
    private TradeValidatorService tradeValidatorService;

    @PostMapping("/api/trade/valid")
    public List<String> validate(@Valid @RequestBody Trade trade, BindingResult bindingResult){
        try {
            return tradeValidatorService.validate(trade,bindingResult);
        } catch (TradeValidationException e) {
            return Arrays.asList(e.getMessage());
        }
    }
}
