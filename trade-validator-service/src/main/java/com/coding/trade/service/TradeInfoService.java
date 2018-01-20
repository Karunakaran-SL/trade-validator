package com.coding.trade.service;

public interface TradeInfoService {

    boolean isValidCounterPart(String customer);

    boolean isValidLegalEntity(String legalEntity);
}
