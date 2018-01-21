package com.coding.trade.service;

import com.coding.trade.type.RequestType;

import java.util.Map;

public interface StatsService {
    void increamentRequestCount();
    void decreamentRequestCount();
    boolean hasLiveRequest();
    void logTimeTaken(RequestType requestType,long timeTaken);
    Map<String,Long> getStatsDetails();
}
