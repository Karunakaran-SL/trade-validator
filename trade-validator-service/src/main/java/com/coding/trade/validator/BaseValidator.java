package com.coding.trade.validator;

import com.coding.trade.model.Trade;
import com.coding.trade.service.HolidayService;
import com.coding.trade.service.TradeInfoService;
import com.coding.trade.service.impl.HolidayServiceImpl;
import com.coding.trade.type.CcyPair;
import com.coding.trade.type.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Currency;

@Component
public class BaseValidator implements Validator{

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private TradeInfoService tradeInfoService;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean supports(Class<?> aClass) {
        return Trade.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Trade trade = (Trade) obj;
        if(trade.getType() != TradeType.VanillaOption) {
            LocalDate valueDate = LocalDate.from(dateTimeFormatter.parse(trade.getValueDate()));
            LocalDate tradeDate = LocalDate.from(dateTimeFormatter.parse(trade.getTradeDate()));
            //Rule 1: value date cannot be before trade date
            checkRuleValueDateBeforeTradeDate(valueDate, tradeDate, errors);
            //Rule 2: value date cannot fall on weekend or non-working day for currency
            checkRuleValueDateShouldBeOnWorkingDay(valueDate, trade.getCcyPair(), errors);
        }
        //Rule2: if the counterparty is one of the supported ones
        checkValidCounterParts(trade,errors);
        //Rule 4: validate currencies if they are valid ISO codes (ISO 4217)
        validateCurrency(trade,errors);
        //Rule 5: Legal Entity
        validateLegalEntity(trade,errors);
    }

    private void validateLegalEntity(Trade trade, Errors errors) {
        if(!tradeInfoService.isValidLegalEntity(trade.getLegalEntity())){
            errors.rejectValue("legalEntity", "Invalid legal entity");
        }
    }

    private void validateCurrency(Trade trade, Errors errors) {
        try {
            Currency.getInstance(trade.getCcyPair().name().substring(0, 3));
            Currency.getInstance(trade.getCcyPair().name().substring(3));
        } catch (IllegalArgumentException e) {
            errors.rejectValue("ccyPair", "Invalid currencies codes");
        }
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
