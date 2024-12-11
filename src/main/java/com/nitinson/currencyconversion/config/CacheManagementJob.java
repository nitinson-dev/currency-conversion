package com.nitinson.currencyconversion.config;

import com.nitinson.currencyconversion.service.CacheAccessTracker;
import com.nitinson.currencyconversion.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class CacheManagementJob {

    private final CacheAccessTracker cacheAccessTracker;

    private final ExchangeRateService exchangeRateService;

    private final CacheManager cacheManager;

    @Autowired
    public CacheManagementJob(CacheAccessTracker cacheAccessTracker, ExchangeRateService exchangeRateService, CacheManager cacheManager) {
        this.cacheAccessTracker = cacheAccessTracker;
        this.exchangeRateService = exchangeRateService;
        this.cacheManager = cacheManager;
    }

    @Scheduled(fixedRate = 3600000) // Run every hour
    public void manageCache() {
        int accessCount = cacheAccessTracker.getAccessCount();
        cacheAccessTracker.resetAccessCount();

        if (accessCount > 10) { // Threshold for frequent access
            System.out.println("Cache accessed frequently. Preloading...");
            // Pre-load cache
            Map<String, BigDecimal> rates = exchangeRateService.getAllExchangeRates();
            cacheManager.getCache("exchangeRates").put("rates", rates);
        } else {
            System.out.println("Cache not accessed frequently. Evicting...");
            // Evict cache
            var cache = cacheManager.getCache("exchangeRates");
            if (cache != null) {
                cache.clear();
            }
        }
    }
}

