package com.nitinson.currencyconversion.service;

import com.nitinson.currencyconversion.model.ExchangeRate;
import com.nitinson.currencyconversion.repository.CurrencyConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {

    private final CurrencyConversionRepository repository;

    private final CacheManager cacheManager;

    @Autowired
    public ExchangeRateService(CurrencyConversionRepository repository, CacheManager cacheManager) {
        this.repository = repository;
        this.cacheManager = cacheManager;
    }

    //@Cacheable("rate")
    public BigDecimal getExchangeRate(String from, String to) {
        String currencyPair = from + "_" + to;

        Map<String, BigDecimal> cachedRates = getAllExchangeRatesFromCache();
        if (cachedRates != null) {
            return cachedRates.get(currencyPair);
        }
        // Fallback to fetch all rates and retrieve the specific currency
        System.out.println("Fetching exchange rate from API...");
        Optional<ExchangeRate> exchangeRate = repository.findById(currencyPair);

        return exchangeRate.map(ExchangeRate::getRate)
                .orElseThrow(() -> new RuntimeException("Exchange rate not available"));
    }

    private Map<String, BigDecimal> getAllExchangeRatesFromCache() {
        // Retrieve the map directly from the cache
        return (Map<String, BigDecimal>) cacheManager.getCache("exchangeRates")
                .get("rates")
                .get();
    }

    @CacheEvict(value = "exchangeRates", allEntries = true)
    public void updateExchangeRates(Map<String, BigDecimal> rates) {
        List<ExchangeRate> rateEntities = rates.entrySet().stream()
                .map(entry -> {
                    ExchangeRate rate = new ExchangeRate();
                    rate.setCurrencyPair(entry.getKey());
                    rate.setRate(entry.getValue());
                    rate.setTimestamp(LocalDateTime.now());
                    return rate;
                }).toList();

        repository.saveAll(rateEntities);

        if (cacheManager.getCache("exchangeRates") != null) {
            cacheManager.getCache("exchangeRates").put("rates", rates);
            System.out.println("Cache updated with new rates.");
        }
    }

    @Cacheable(value = "exchangeRates", key = "'rates'", unless = "#result == null")
    public Map<String, BigDecimal> getAllExchangeRates() {
        System.out.println("Fetching exchange rates from API...");
        List<ExchangeRate> rateEntities = repository.findAll();
        return rateEntities.stream().collect(Collectors.toMap(ExchangeRate::getCurrencyPair, ExchangeRate::getRate));
    }
}
