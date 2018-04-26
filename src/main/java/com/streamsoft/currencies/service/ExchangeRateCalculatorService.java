package com.streamsoft.currencies.service;

import java.math.BigDecimal;

import com.streamsoft.currencies.controller.CurrencyExchangeRateQueryParam;

public interface ExchangeRateCalculatorService {
	BigDecimal calculateExchangeRate(CurrencyExchangeRateQueryParam query);
}