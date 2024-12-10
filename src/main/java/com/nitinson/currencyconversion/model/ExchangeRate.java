package com.nitinson.currencyconversion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class ExchangeRate {

    @Id
    private String currencyPair; // Primary key

    private BigDecimal rate;
    private LocalDateTime timestamp;
}
