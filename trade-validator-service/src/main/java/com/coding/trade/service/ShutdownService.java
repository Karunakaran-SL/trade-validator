package com.coding.trade.service;

public interface ShutdownService {
    boolean canAcceptRequest();
    String shutdown();
}
