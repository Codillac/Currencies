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
	public void testSaveCurrencyRatesForCurrencyFromSeveralRateSessions() {
		//Given
		Country country1 = new Country("Stany zjednoczone", "USA");
		Country country2 = new Country("Republika chinska", "ROC");
		Currency currency1 = new Currency("Dolar amerykański", "USD");
		country1.getCurrencies().add(currency1);
		currency1.getCountries().add(country1);
		currencyDao.save(currency1);
		countryDao.save(country1);
		List<CurrencyRate> currencyRates = getCurrenciesService.getCurrencyRatesForCurrencyTopCount("A", "USD", 10);
		//When
		
		for(CurrencyRate tempCurrencyRate : currencyRates) {
			RateSession rateSession = tempCurrencyRate.getRateSession();
			if(!rateSessionDao.findByNumber(rateSession.getNumber()).isPresent()) {
				rateSessionDao.save(rateSession);
			} else {
				tempCurrencyRate.setRateSession(rateSessionDao.findByNumber(rateSession.getNumber()).get());
				rateSessionDao.save(tempCurrencyRate.getRateSession());
			}
			Currency currency = tempCurrencyRate.getCurrency();
			if(!currencyDao.findByCode(currency.getCode()).isPresent()) {
				currencyDao.save(currency);
			} else {
				tempCurrencyRate.setCurrency(currencyDao.findByCode(currency.getCode()).get());
				currencyDao.save(tempCurrencyRate.getCurrency());
			}
			if(!currencyRateDao.findByRateSessionAndCurrency(tempCurrencyRate.getRateSession(), tempCurrencyRate.getCurrency()).isPresent()) {
				currencyRateDao.save(tempCurrencyRate);
			}
		}
	}
	
	@Test
	public void testSaveCurrencyRatesFromTableFromSeveralRateSessions() {
		//Given
//		Country country1 = new Country("Stany zjednoczone", "USA");
//		Country country2 = new Country("Republika chinska", "ROC");
//		Currency currency1 = new Currency("Dolar amerykanski", "USD");
//		country1.getCurrencies().add(currency1);
//		currency1.getCountries().add(country1);
//		countryDao.save(country1);
//		currencyDao.save(currency1);
		List<CurrencyRate> currencyRates = getCurrenciesService.getCurrencyRatesFromTableTopCount("A", 10);
		//When
		
		for(CurrencyRate tempCurrencyRate : currencyRates) {
			RateSession rateSession = tempCurrencyRate.getRateSession();
			if(!rateSessionDao.findByNumber(rateSession.getNumber()).isPresent()) {
				rateSessionDao.save(rateSession);
			} else {
				tempCurrencyRate.setRateSession(rateSessionDao.findByNumber(rateSession.getNumber()).get());
			}
			Currency currency = tempCurrencyRate.getCurrency();
			if(!currencyDao.findByCode(currency.getCode()).isPresent()) {
				currencyDao.save(currency);
			} else {
				tempCurrencyRate.setCurrency(currencyDao.findByCode(currency.getCode()).get());
			}
			if(!currencyRateDao.findByRateSessionAndCurrency(tempCurrencyRate.getRateSession(), tempCurrencyRate.getCurrency()).isPresent()) {
				currencyRateDao.save(tempCurrencyRate);
			}
		}
	}
}
