package com.streamsoft.currencies;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.streamsoft.currencies.domain.Country;
import com.streamsoft.currencies.domain.Currency;
import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.domain.RateSession;
import com.streamsoft.currencies.repository.CountryDao;
import com.streamsoft.currencies.repository.CurrencyDao;
import com.streamsoft.currencies.repository.CurrencyRateDao;
import com.streamsoft.currencies.repository.RateSessionDao;
import com.streamsoft.currencies.service.NBPGetCurrencyRatesService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NBPCurrencyRatesRepositoryTestSuite {
	@Autowired
	CountryDao countryDao;
	
	@Autowired
	CurrencyDao currencyDao;
	
	@Autowired
	RateSessionDao rateSessionDao;
	
	@Autowired
	CurrencyRateDao currencyRateDao;
	
	@Autowired
	NBPGetCurrencyRatesService getCurrenciesService;
	
	@Test
	public void testSaveAllDownloadedCurrencyRates() {
		//Given
		Country country = new Country("Stany zjednoczone", "USA");
		List<CurrencyRate> currencyRates = getCurrenciesService.getCurrencyRatesForCurrency("A", "USD");
		List<RateSession> rateSessions = new ArrayList<>();
		List<Currency> currencies = new ArrayList<>();
		for(CurrencyRate tempCurrencyRate : currencyRates) {
			rateSessions.add(tempCurrencyRate.getRateSession());
			currencies.add(tempCurrencyRate.getCurrency());
			country.getCurrencies().add(tempCurrencyRate.getCurrency());
		}
		countryDao.save(country);
		currencyDao.saveAll(currencies);
		rateSessionDao.saveAll(rateSessions);
		//When
		currencyRateDao.saveAll(currencyRates);
	}
}
