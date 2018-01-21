package com.coding.trade.validator;

import com.coding.trade.model.Trade;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class SpotValidator extends AbstractValidator{

    @Override
    public void validate(Object obj, Errors errors) {
        //Value Date should be two dates after the trade date
        Trade trade = (Trade) obj;
        LocalDate valueDate = LocalDate.from(dateTimeFormatter.parse(trade.getValueDate()));
        LocalDate todayDate = LocalDate.from(dateTimeFormatter.parse("2016-09-10"));
        if (DAYS.between(todayDate, valueDate) != 2) {
            errors.rejectValue("valueDate","As per SPOT Value Date should be two dates after the trade date");
        }
    }
}
