package com.coding.trade.validator;

import com.coding.trade.model.Trade;
import com.coding.trade.type.Style;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class OptionValidator extends AbstractValidator {

    @Override
    public void validate(Object obj, Errors errors) {
        Trade trade = (Trade) obj;
        //Rule1: the style can be either American or European
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "style", "style can be either American or European");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expiryDate", "For Option expiry date is mandatory");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "premiumDate", "For Option premium date is mandatory");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryDate", "For Option delivery date is mandatory");
        if(!errors.hasErrors()) {
            validateExcersiceStartDate(trade,errors);
            validateDeliveryDate(trade,errors);
        }
    }

    private void validateDeliveryDate(Trade trade, Errors errors) {
        LocalDate deliveryDate = LocalDate.from(dateTimeFormatter.parse(trade.getDeliveryDate()));
        LocalDate expiryDate = LocalDate.from(dateTimeFormatter.parse(trade.getExpiryDate()));
        LocalDate premiumDate = LocalDate.from(dateTimeFormatter.parse(trade.getPremiumDate()));

        if(deliveryDate.isBefore(expiryDate) || deliveryDate.isBefore(premiumDate)){
            errors.rejectValue("deliveryDate","Expiry date and premium date shall be before delivery date");
        }
    }

    private void validateExcersiceStartDate(Trade trade, Errors errors) {
        if(trade.getStyle() == Style.AMERICAN) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "excerciseStartDate", "For Option excerciseStartDate is mandatory");
            if (!errors.hasErrors()) {
                LocalDate excerciseStartDate = LocalDate.from(dateTimeFormatter.parse(trade.getExcerciseStartDate()));
                LocalDate expiryDate = LocalDate.from(dateTimeFormatter.parse(trade.getExpiryDate()));
                LocalDate tradeDate = LocalDate.from(dateTimeFormatter.parse(trade.getTradeDate()));
                if (excerciseStartDate.isBefore(tradeDate) || excerciseStartDate.isAfter(expiryDate)) {
                    errors.rejectValue("excerciseStartDate", "ExcerciseStartDate should be after the trade date\n" +
                            "but before the expiry date");
                }
            }
        }
    }
}
