package com.coding.trade.model;

import com.coding.trade.type.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

@Data
public class Trade {
    
    @JsonProperty(required = true)
    @NotNull
    private String customer;

    @JsonProperty(required = true)
    @NotNull
    private CcyPair ccyPair;

    @JsonProperty(required = true)
    @NotNull
    private TradeType type;

    @JsonProperty(required = true)
    @NotNull
    private Direction direction;

    @JsonProperty(required = true)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String tradeDate;

    @JsonProperty(required = true)
    @NotNull
    private float amount1;

    @JsonProperty(required = true)
    @NotNull
    private float amount2;

    @JsonProperty(required = true)
    @NotNull
    private float rate;

    @JsonProperty(required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String valueDate;

    @JsonProperty(required = true)
    @NotNull
    private String legalEntity;

    @JsonProperty(required = true)
    @NotNull
    private String trader;

    @JsonProperty
    private Style style;

    @JsonProperty
    private Strategy strategy;

    @JsonProperty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String deliveryDate;

    @JsonProperty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String expiryDate;

    @JsonProperty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String excerciseStartDate;

    @JsonProperty
    private String payCcy;

    @JsonProperty
    private float premium;

    @JsonProperty
    private String premiumCcy;

    @JsonProperty
    private String premiumType;

    @JsonProperty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String premiumDate;
}
