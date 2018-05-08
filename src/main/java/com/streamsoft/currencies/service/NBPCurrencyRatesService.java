package com.streamsoft.currencies.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.streamsoft.currencies.domain.CurrencyRate;

@Service
public class NBPCurrencyRatesService {
	@Autowired
	NBPGetCurrencyRatesService getCurrencyRatesService;
	
	@Autowired
	NBPCurrencyRatesDBService dbService;
	
	public CurrencyRate getCurrencyRate(String code, LocalDate date) {
		Optional<CurrencyRate> currencyRate = dbService.getCurrencyRateFromDbByDateAndCurrencyCode(date, code);
		if(currencyRate.isPresent()) {
			return currencyRate.get();
		}
		currencyRate = Optional.of(getCurrencyRatesService.getCurrencyRatesForCurrencyDate("A", code, date));
		dbService.saveOrUpdateCurrencyRateToDb(currencyRate.get());
		return currencyRate.get();
	}
}
