package com.streamsoft.currencies.service;

import java.util.List;

import com.streamsoft.currencies.controller.CurrencyExchangeRateQueryParam;
import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.exceptions.NoCurrencyRatesException;

public interface GetCurrencyRatesService {
	List<CurrencyRate> getCurrencyRates(CurrencyExchangeRateQueryParam queryParam) throws NoCurrencyRatesException;
}