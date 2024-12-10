package com.nitinson.currencyconversion.service;

import com.nitinson.currencyconversion.model.ExchangeRate;
import com.nitinson.currencyconversion.repository.CurrencyConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExchangeRateService {

    private final CurrencyConversionRepository repository;

    @Autowired
    public ExchangeRateService(CurrencyConversionRepository repository) {
        this.repository = repository;
    }

    @Cacheable("rates")
    public BigDecimal getExchangeRate(String from, String to) {
        String currencyPair = from + "_" + to;
        Optional<ExchangeRate> exchangeRate = repository.findById(currencyPair);

        return exchangeRate.map(ExchangeRate::getRate)
                .orElseThrow(() -> new RuntimeException("Exchange rate not available"));
    }

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
    }
}
