package com.nitinson.currencyconversion.model;

import lombok.Data;

import java.util.Map;

@Data
public class CurrencyRate {

    private String baseCurrency;
    private Map<String, Double> rates;

}
