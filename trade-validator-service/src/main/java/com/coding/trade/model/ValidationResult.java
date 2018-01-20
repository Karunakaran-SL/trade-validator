package com.coding.trade.model;

import lombok.Data;

import java.util.List;

@Data
public class ValidationResult {

    private Trade trade;

    private List<String> errors;

    private boolean valid;
}
