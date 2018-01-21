package com.coding.trade.controller;

import com.coding.trade.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TradeStatsController {

    @Autowired
    StatsService statsService;

    @GetMapping("/api/stats")
    public Map<String,Long> getStats(){
        return statsService.getStatsDetails();
    }
}
