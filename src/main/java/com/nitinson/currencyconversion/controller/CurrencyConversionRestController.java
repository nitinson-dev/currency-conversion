package com.nitinson.currencyconversion.controller;

import com.nitinson.currencyconversion.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/currency")
public class CurrencyConversionRestController {

    private final CurrencyConversionService currencyConversionService;

    @Autowired
    public CurrencyConversionRestController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }

    @GetMapping("/convert")
    public double convertCurrency(@RequestParam String fromCurrency,
                                  @RequestParam String toCurrency,
                                  @RequestParam double amount) {
        return currencyConversionService.convertCurrency(fromCurrency, toCurrency, amount);
    }

    @GetMapping("/rates")
    public Map<String, Double> getExchangeRates(@RequestParam String baseCurrency) {
        return currencyConversionService.getExchangeRates(baseCurrency).getRates();
    }

}
