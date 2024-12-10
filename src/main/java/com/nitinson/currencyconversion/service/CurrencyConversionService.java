package com.nitinson.currencyconversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyConversionService {


    private final ExchangeRateService rateService;

    @Autowired
    public CurrencyConversionService(ExchangeRateService rateService) {
        this.rateService = rateService;
    }

    public BigDecimal convertCurrency(String from, String to, BigDecimal amount) {
        BigDecimal rate = rateService.getExchangeRate(from, to);
        return amount.multiply(rate);
    }
}

