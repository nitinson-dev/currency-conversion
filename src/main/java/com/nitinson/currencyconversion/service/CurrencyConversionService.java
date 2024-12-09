package com.nitinson.currencyconversion.service;

import com.nitinson.currencyconversion.model.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyConversionService {

    private final RestTemplate restTemplate;

    @Value("${ecb.api.url}")
    private String ecbApiUrl;

    public CurrencyConversionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CurrencyRate getExchangeRates(String baseCurrency) {
        String url = String.format("%s?base=%s", ecbApiUrl, baseCurrency);
        return restTemplate.getForObject(url, CurrencyRate.class);
    }

    public double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        CurrencyRate rates = getExchangeRates(fromCurrency);
        Double exchangeRate = rates.getRates().get(toCurrency);

        if (exchangeRate == null) {
            throw new IllegalArgumentException("Invalid target currency");
        }

        return amount * exchangeRate;
    }
}
