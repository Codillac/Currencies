package com.streamsoft.currencies.service;

import java.time.LocalDate;
import java.util.List;
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
	
	public void getAndSaveMillionCurrencyRates() {
		LocalDate topDate = LocalDate.now();
		LocalDate downDate = topDate.minusDays(93);
		LocalDate earliestPossibleTopDate = LocalDate.of(2002, 1, 2);
		while(dbService.countAllCurrencyRatesInDb() < 1000000 && topDate.isAfter(earliestPossibleTopDate)) {
			List<CurrencyRate> presentRates = getCurrencyRatesService.getCurrencyRatesFromTablePeriod("A", downDate, topDate);
//			presentRates.addAll(getCurrencyRatesService.getCurrencyRatesFromTablePeriod("B", downDate, topDate));
//			presentRates.addAll(getCurrencyRatesService.getCurrencyRatesFromTablePeriod("C", downDate, topDate));
			for(CurrencyRate tempRate : presentRates) {
				dbService.saveOrUpdateCurrencyRateToDb(tempRate);
			}
			topDate = topDate.minusDays(93);
			downDate = topDate.minusDays(93);
		}
	}
}
