package com.nitinson.currencyconversion.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CacheAccessTracker {

    private final AtomicInteger accessCount = new AtomicInteger();

    public void incrementAccessCount() {
        accessCount.incrementAndGet();
    }

    public int getAccessCount() {
        return accessCount.get();
    }

    public void resetAccessCount() {
        accessCount.set(0);
    }
}

