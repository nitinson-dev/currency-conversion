package com.nitinson.currencyconversion.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyConversion {

    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
}
