package com.streamsoft.currencies;

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
	public void testSaveSingleCurrencyRateForCurrency() {
		//Given
		Country country = new Country("Stany zjednoczone", "USA");
		List<CurrencyRate> currencyRates = getCurrenciesService.getCurrencyRatesForCurrency("A", "USD");
		//When
		for(CurrencyRate tempCurrencyRate : currencyRates) {
			RateSession rateSession = tempCurrencyRate.getRateSession();
			Currency currency = tempCurrencyRate.getCurrency();
			rateSession.getCurrencyRates().add(tempCurrencyRate);
			currency.getCurrencyRates().add(tempCurrencyRate);
			currencyDao.save(currency);
			rateSessionDao.save(rateSession);
//			country.getCurrencies().add(tempCurrencyRate.getCurrency());
//			countryDao.save(country);
			currencyRateDao.save(tempCurrencyRate);
		}
	}
}
