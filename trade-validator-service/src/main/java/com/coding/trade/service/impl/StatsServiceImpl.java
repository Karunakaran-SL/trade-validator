package com.coding.trade.service.impl;

import com.coding.trade.service.StatsService;
import com.coding.trade.type.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StatsServiceImpl implements StatsService{

    @Autowired
    CounterService counterService;

    private static final int MAX_STATS_ALLOWED_PER_FIELD = 100;

    private Map<RequestType,Deque<Long>> statsMap = new ConcurrentHashMap<>(2);

    private volatile AtomicInteger liveRequest = new AtomicInteger();

    @Override
    public void increamentRequestCount() {
        liveRequest.incrementAndGet();
        counterService.increment("live.request");
    }

    @Override
    public void decreamentRequestCount() {
        liveRequest.decrementAndGet();
        counterService.decrement("live.request");
    }

    @Override
    public boolean hasLiveRequest() {
        return liveRequest.get() > 0;
    }

    @Override
    public void logTimeTaken(RequestType requestType, long timeTaken) {
        if(statsMap.containsKey(requestType)){
            Deque<Long> timeQueue = statsMap.get(requestType);
            if(timeQueue.size() >= MAX_STATS_ALLOWED_PER_FIELD){
                timeQueue.removeFirst();
            }
            timeQueue.addLast(timeTaken);
        }else{
            Deque<Long> longDeque = new ConcurrentLinkedDeque<>();
            longDeque.addLast(timeTaken);
            statsMap.put(requestType,longDeque);
        }
    }

    @Override
    public Map<String, Long> getStatsDetails() {
        Map<String,Long> statsResult = new HashMap<>();
        statsMap.entrySet().forEach(entry -> {
            String key = entry.getKey().name();
            long max = entry.getValue().stream().max(Long::compare).get();
            long min = entry.getValue().stream().min(Long::compare).get();
            long avg = Math.round(entry.getValue().stream().mapToLong(t -> t).average().getAsDouble());
            statsResult.put(key+".max",max);
            statsResult.put(key+".min",min);
            statsResult.put(key+".avg",avg);
        });
        return statsResult;
    }
}
