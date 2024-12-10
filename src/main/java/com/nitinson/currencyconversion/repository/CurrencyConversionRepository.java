package com.nitinson.currencyconversion.repository;

import com.nitinson.currencyconversion.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyConversionRepository extends JpaRepository<ExchangeRate, String> {
}

