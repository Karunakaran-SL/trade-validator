package com.coding.trade.service.impl;

public class ShutdownThread implements Runnable{
    private ShutdownServiceImpl shutdownService;

    public ShutdownThread(ShutdownServiceImpl shutdownService){
        this.shutdownService = shutdownService;
    }

    @Override
    public void run() {
        shutdownService.initiateShutdown(1);
    }
}
