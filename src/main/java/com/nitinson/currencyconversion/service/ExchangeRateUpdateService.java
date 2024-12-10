package com.nitinson.currencyconversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Fetch and update rates
@Service
public class ExchangeRateUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateUpdateService.class);

    private final WebClient webClient;
    private final ExchangeRateService rateService;

    @Value("${ecb.api.url}")
    private String externalApiUrl;

    @Autowired
    public ExchangeRateUpdateService(WebClient webClient, ExchangeRateService rateService) {
        this.webClient = webClient;
        this.rateService = rateService;
    }

    @Scheduled(fixedRate = 3600000) // Run every hour
    public void updateRates() {
        logger.info("Starting to update exchange rates...");
        try {
            // Fetch data from the external API
            Map responseBody = webClient.get()
                    .uri(externalApiUrl)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (responseBody != null) {
                logger.debug("Fetched exchange rates successfully.");
                @SuppressWarnings("unchecked")
                Map<String, Number> rawRates = (Map<String, Number>) responseBody.get("rates");

                // Convert rates to BigDecimal
                Map<String, BigDecimal> rates = new HashMap<>();
                rawRates.forEach((currency, value) -> {
                    if (value instanceof Integer) {
                        rates.put(currency, BigDecimal.valueOf((Integer) value));
                    } else if (value instanceof Double) {
                        rates.put(currency, BigDecimal.valueOf((Double) value));
                    } else if (value instanceof BigDecimal) {
                        rates.put(currency, (BigDecimal) value);
                    }
                });

                // Update rates in the database
                rateService.updateExchangeRates(rates);
            }
        } catch (Exception e) {
            // Log the error
            logger.error("Error occurred while fetching exchange rates: {}", e.getMessage());
        }
    }
}
