package com.coding.trade.service.impl;

import com.coding.trade.service.ShutdownService;
import com.coding.trade.service.StatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ShutdownServiceImpl implements ShutdownService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownServiceImpl.class);

    private volatile boolean runApp = true;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    StatsService statsService;

    @Override
    public boolean canAcceptRequest() {
        return runApp;
    }

    @Override
    public String shutdown() {
        runApp = false;
        while(statsService.hasLiveRequest()){
            LOGGER.warn("Few service are in progress.. Waiting for shutdown");
        }
        LOGGER.warn("Shuting Down ...");
        Thread thread = new Thread(new ShutdownThread(this));
        thread.start();
        return "Shutdown successfully.";
    }

    public void sleep(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.error("Error while running in Shutdown sleep");
        }
    }

    public void initiateShutdown(int returnCode){
        sleep();
        SpringApplication.exit(appContext, () -> returnCode);
    }
}
