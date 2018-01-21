package com.coding.trade.controller;

import com.coding.trade.service.ShutdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradeShutdownController {

    @Autowired
    ShutdownService shutdownService;

    @PostMapping("/api/shutdown")
    public String shutDown(){
        return shutdownService.shutdown();
    }
}
