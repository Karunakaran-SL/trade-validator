package com.coding.trade.validator;

import com.coding.trade.model.Trade;
import com.coding.trade.service.HolidayService;
import com.coding.trade.service.impl.HolidayServiceImpl;
import com.coding.trade.type.CcyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public abstract class BaseValidator implements Validator{

    //TODO calling this is faling
    private HolidayService holidayService = new HolidayServiceImpl();

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //TODO Assumption is for Europe. Based on currenty this value needs to change.
    private DayOfWeek[] weekEnd = new DayOfWeek[]{DayOfWeek.SATURDAY,DayOfWeek.SUNDAY};

    @Override
    public boolean supports(Class<?> aClass) {
        return Trade.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Trade trade = (Trade) obj;
        LocalDate valueDate = LocalDate.from(dateTimeFormatter.parse(trade.getValueDate()));
        LocalDate tradeDate = LocalDate.from(dateTimeFormatter.parse(trade.getTradeDate()));

        //Rule 1: value date cannot be before trade date
        checkRuleValueDateBeforeTradeDate(valueDate,tradeDate, errors);
        //Rule 2: value date cannot fall on weekend or non-working day for currency
        checkRuleValueDateShouldBeOnWorkingDay(valueDate,trade.getCcyPair(),errors);
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
        return Arrays.asList(weekEnd).contains(valueDate.getDayOfWeek());
    }

    private void checkRuleValueDateBeforeTradeDate(LocalDate valueDate, LocalDate tradeDate, Errors errors){
        if(valueDate.isBefore(tradeDate)){
            errors.rejectValue("valueDate", "Value date cannot be before trade date");
        }
    }
}
