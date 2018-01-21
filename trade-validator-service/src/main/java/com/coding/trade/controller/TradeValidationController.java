package com.coding.trade.controller;

import com.coding.trade.exception.ShutdownInProgressException;
import com.coding.trade.model.Trade;
import com.coding.trade.model.ValidationResult;
import com.coding.trade.service.ShutdownService;
import com.coding.trade.service.TradeValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
            return tradeValidatorService.validate(trade, bindingResult);
        }
        throw new ShutdownInProgressException();
    }

    @PostMapping("/api/trade/bulk-valid")
    public List<ValidationResult> validate(@Valid @RequestBody List<Trade> trades){
        if(shutdownService.canAcceptRequest()) {
            return tradeValidatorService.validate(trades);
        }
        throw new ShutdownInProgressException();
    }
}
