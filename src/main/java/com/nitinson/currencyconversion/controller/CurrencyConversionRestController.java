package com.nitinson.currencyconversion.controller;

import com.nitinson.currencyconversion.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/currency")
public class CurrencyConversionRestController {

    private final CurrencyConversionService conversionService;

    @Autowired
    public CurrencyConversionRestController(CurrencyConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping("/convert")
    public BigDecimal convert(@RequestParam String from, @RequestParam String to, @RequestParam BigDecimal amount) {
        return conversionService.convertCurrency(from, to, amount);
    }

}
