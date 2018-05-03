package com.streamsoft.currencies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.streamsoft.currencies.domain.Country;
import com.streamsoft.currencies.domain.Currency;
import com.streamsoft.currencies.domain.CurrencyRate;
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
		List<CurrencyRate> currencyRates = getCurrenciesService.getCurrencyRatesForCurrencyTopCount("A", "USD", 17);
		//When
		for(CurrencyRate tempCurrencyRate : currencyRates) {
			if(rateSessionDao.findByNumber(tempCurrencyRate.getRateSession().getNumber()).isPresent()) {
				tempCurrencyRate.setRateSession(rateSessionDao.findByNumber(tempCurrencyRate.getRateSession().getNumber()).get());
			}
			rateSessionDao.save(tempCurrencyRate.getRateSession());
			if(currencyDao.findByCode(tempCurrencyRate.getCurrency().getCode()).isPresent()) {
				tempCurrencyRate.setCurrency(currencyDao.findByCode(tempCurrencyRate.getCurrency().getCode()).get());
			}
			currencyDao.save(tempCurrencyRate.getCurrency());
			Optional<CurrencyRate> existingCurrencyRate = currencyRateDao.findByRateSessionAndCurrency(tempCurrencyRate.getRateSession(), tempCurrencyRate.getCurrency());
			if(existingCurrencyRate.isPresent()) {
				tempCurrencyRate.setId(existingCurrencyRate.get().getId());
			}
			currencyRateDao.save(tempCurrencyRate);
		}
		long numberOfCurrencies = currencyDao.count();
		long numberOfSessionRates = rateSessionDao.count();
		long numberOfCurrencyRates = currencyRateDao.count();
		//Then
		Assert.assertEquals(1, numberOfCurrencies);
		Assert.assertEquals(17, numberOfSessionRates);
		Assert.assertEquals(17, numberOfCurrencyRates);
	}
	
	@Test
	public void testSaveCurrencyRatesFromTableFromSeveralRateSessions() {
		//Given
		List<CurrencyRate> currencyRates = getCurrenciesService.getCurrencyRatesFromTableTopCount("A", 10);
		List<Country> countries = prepareTestCountries();
		Set<Currency> currencies = prepareTestCurrenciesFromCountries(countries);
		//When
		for(Currency tempCurrency : currencies){
			currencyDao.save(tempCurrency);
		}
		for(Country tempCountry : countries){
			countryDao.save(tempCountry);
		}
		for(CurrencyRate tempCurrencyRate : currencyRates) {
			if(rateSessionDao.findByNumber(tempCurrencyRate.getRateSession().getNumber()).isPresent()) {
				tempCurrencyRate.setRateSession(rateSessionDao.findByNumber(tempCurrencyRate.getRateSession().getNumber()).get());
			}
			rateSessionDao.save(tempCurrencyRate.getRateSession());
			if(currencyDao.findByCode(tempCurrencyRate.getCurrency().getCode()).isPresent()) {
				tempCurrencyRate.setCurrency(currencyDao.findByCode(tempCurrencyRate.getCurrency().getCode()).get());
			}
			currencyDao.save(tempCurrencyRate.getCurrency());
			Optional<CurrencyRate> existingCurrencyRate = currencyRateDao.findByRateSessionAndCurrency(tempCurrencyRate.getRateSession(), tempCurrencyRate.getCurrency());
			if(existingCurrencyRate.isPresent()) {
				tempCurrencyRate.setId(existingCurrencyRate.get().getId());
			}
			currencyRateDao.save(tempCurrencyRate);
		}
	}
	
	private List<Country> prepareTestCountries(){
		List<CurrencyRate> currencyRates = getCurrenciesService.getCurrencyRatesFromTable("A");
		List<Country> listOfCountries = new ArrayList<>();
		listOfCountries.add(new Country("Stany Zjednoczone", "US"));
		listOfCountries.add(new Country("Niemcy", "DE"));
		listOfCountries.add(new Country("Francja", "FR"));
		listOfCountries.add(new Country("Kanada", "CA"));
		listOfCountries.add(new Country("Australia", "AU"));
		listOfCountries.add(new Country("Czechy", "CZ"));
		listOfCountries.add(new Country("Szwajcaria", "CH"));
		listOfCountries.add(new Country("Chiny", "CN"));
		listOfCountries.add(new Country("Ukraina", "UA"));
		listOfCountries.add(new Country("Rosja", "RU"));
		for(CurrencyRate tempCurrencyRate : currencyRates){
			Currency tempCurrency = tempCurrencyRate.getCurrency();
			String tempCode = tempCurrency.getCode();
			if(tempCode.equals("USD")){
				listOfCountries.get(0).getCurrencies().add(tempCurrency);
				listOfCountries.get(3).getCurrencies().add(tempCurrency);
				listOfCountries.get(4).getCurrencies().add(tempCurrency);
				listOfCountries.get(7).getCurrencies().add(tempCurrency);
			} else if(tempCode.equals("EUR")){
				listOfCountries.get(0).getCurrencies().add(tempCurrency);
				listOfCountries.get(1).getCurrencies().add(tempCurrency);
				listOfCountries.get(2).getCurrencies().add(tempCurrency);
				listOfCountries.get(5).getCurrencies().add(tempCurrency);
				listOfCountries.get(6).getCurrencies().add(tempCurrency);
			} else if(tempCode.equals("RUB")){
				listOfCountries.get(8).getCurrencies().add(tempCurrency);
				listOfCountries.get(9).getCurrencies().add(tempCurrency);
			} else if(tempCode.equals("CAD")){
				listOfCountries.get(3).getCurrencies().add(tempCurrency);
			} else if(tempCode.equals("AUD")){
				listOfCountries.get(4).getCurrencies().add(tempCurrency);
			} else if(tempCode.equals("CZK")){
				listOfCountries.get(5).getCurrencies().add(tempCurrency);
			} else if(tempCode.equals("CHF")){
				listOfCountries.get(0).getCurrencies().add(tempCurrency);
				listOfCountries.get(6).getCurrencies().add(tempCurrency);
			} else if(tempCode.equals("CNY")){
				listOfCountries.get(7).getCurrencies().add(tempCurrency);
			} else if(tempCode.equals("UAH")){
				listOfCountries.get(8).getCurrencies().add(tempCurrency);
			}
		}
		return listOfCountries;
	}
	private Set<Currency> prepareTestCurrenciesFromCountries(List<Country> countries){
		Set<Currency> currencies = new HashSet<>();
		for(Country tempCountry : countries){
			Set<Currency> tempSet = tempCountry.getCurrencies();
			for(Currency tempCurrency : tempSet){
				currencies.add(tempCurrency);
			}
		}
		return currencies;
	}
}