package com.coding.trade.validator;

import com.coding.trade.model.Trade;
import com.coding.trade.service.HolidayService;
import com.coding.trade.service.TradeInfoService;
import com.coding.trade.type.CcyPair;
import com.coding.trade.type.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.util.Currency;

@Component
public class BaseValidator extends AbstractValidator{

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private TradeInfoService tradeInfoService;

    @Override
    public void validate(Object obj, Errors errors) {
        Trade trade = (Trade) obj;
        checkForBasicValidation(trade,errors);
        checkForCurrencyValidation(trade,errors);
        if(!errors.hasErrors()) {
            if (trade.getType() != TradeType.VanillaOption) {
                LocalDate valueDate = LocalDate.from(dateTimeFormatter.parse(trade.getValueDate()));
                LocalDate tradeDate = LocalDate.from(dateTimeFormatter.parse(trade.getTradeDate()));
                //Rule 1: value date cannot be before trade date
                checkRuleValueDateBeforeTradeDate(valueDate, tradeDate, errors);
                //Rule 2: value date cannot fall on weekend or non-working day for currency
                checkRuleValueDateShouldBeOnWorkingDay(valueDate, trade.getCcyPair(), errors);
            }
            //Rule2: if the counterparty is one of the supported ones
            checkValidCounterParts(trade, errors);
            //Rule 4: validate currencies if they are valid ISO codes (ISO 4217)
            validateCurrency(trade, errors);
            //Rule 5: Legal Entity
            validateLegalEntity(trade, errors);
        }
    }

    private void checkForCurrencyValidation(Trade trade, Errors errors) {
        if(!isValidCurrency(trade.getPayCcy())){
            errors.rejectValue("payCcy","Invalid payCcy format");
        }
        if(!isValidCurrency(trade.getPremiumCcy())){
            errors.rejectValue("premiumCcy","Invalid premiumCcy format");
        }
    }

    private void checkForBasicValidation(Trade trade, Errors errors) {
        if(checkDateFormat(trade.getTradeDate())){
            errors.rejectValue("tradeDate","Invalid Trade date format");
        }
        if(checkDateFormat(trade.getValueDate())){
            errors.rejectValue("valueDate","Invalid valueDate format");
        }
        if(checkDateFormat(trade.getExpiryDate())){
            errors.rejectValue("expiryDate","Invalid expiryDate format");
        }
        if(checkDateFormat(trade.getDeliveryDate())){
            errors.rejectValue("deliveryDate","Invalid deliveryDate format");
        }
        if(checkDateFormat(trade.getPremiumDate())){
            errors.rejectValue("premiumDate","Invalid premiumDate format");
        }
        if(checkDateFormat(trade.getExcerciseStartDate())){
            errors.rejectValue("excerciseStartDate","Invalid excerciseStartDate format");
        }
    }

    private boolean checkDateFormat(String date) {
        if(date!=null){
            try {
                dateTimeFormatter.parse(date);
            } catch (Exception e) {
                return true;
            }
        }
        return false;
    }

    private void validateLegalEntity(Trade trade, Errors errors) {
        if(!tradeInfoService.isValidLegalEntity(trade.getLegalEntity())){
            errors.rejectValue("legalEntity", "Invalid legal entity");
        }
    }

    private void validateCurrency(Trade trade, Errors errors) {
        if(!isValidCurrency(trade.getCcyPair().name().substring(0, 3)) ||
                !isValidCurrency(trade.getCcyPair().name().substring(3))){
            errors.rejectValue("ccyPair", "Invalid currencies codes ccyPair");
        }
    }

    private boolean isValidCurrency(String currency){
        try {
            if(currency!=null) {
                Currency.getInstance(currency);
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    private void checkValidCounterParts(Trade trade, Errors errors) {
        if(!tradeInfoService.isValidCounterPart(trade.getCustomer())){
            errors.rejectValue("customer", "Unsupported counterparties");
        }
    }

    private void checkRuleValueDateShouldBeOnWorkingDay(LocalDate valueDate, CcyPair ccyPair, Errors errors) {
        if(isWeekEnd(valueDate,ccyPair)){
            errors.rejectValue("valueDate", "Value date cannot fall on weekend");
        }
        if(isHoliday(valueDate,ccyPair)){
            errors.rejectValue("valueDate", "Value date cannot fall on non-working day for currency");
        }
    }

    private boolean isHoliday(LocalDate valueDate, CcyPair ccyPair) {
        return holidayService.isHoliday(ccyPair,valueDate);
    }

    private boolean isWeekEnd(LocalDate valueDate,CcyPair ccyPair) {
        return holidayService.isWeekEnd(ccyPair,valueDate);
    }

    private void checkRuleValueDateBeforeTradeDate(LocalDate valueDate, LocalDate tradeDate, Errors errors){
        if(valueDate.isBefore(tradeDate)){
            errors.rejectValue("valueDate", "Value date cannot be before trade date");
        }
    }
}
