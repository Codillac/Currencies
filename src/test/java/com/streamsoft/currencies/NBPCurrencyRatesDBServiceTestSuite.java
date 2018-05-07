package com.streamsoft.currencies;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.streamsoft.currencies.repository.CurrencyRateDao;
import com.streamsoft.currencies.service.NBPCurrencyRatesDBService;
import com.streamsoft.currencies.service.NBPGetCurrencyRatesService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NBPCurrencyRatesDBServiceTestSuite {
	@Autowired
	NBPCurrencyRatesDBService service;
	
	@Autowired
	NBPGetCurrencyRatesService getCurrencyRatesFromNBPService;
	
	@Autowired
	CurrencyRateDao currencyRateDao;
	
	@Autowired
	CountryDao countryDao;
	
	@Test
	public void testSaveCurrencyRatesToDB() {
		//Given
		List<CurrencyRate> currencyRates = getCurrencyRatesFromNBPService.getCurrencyRatesFromTableTopCount("A", 67);
		//When
		for(CurrencyRate tempCurrencyRate : currencyRates) {
			service.saveCurrencyRateToDb(tempCurrencyRate);
		}
		//Then
		Assert.assertEquals(currencyRateDao.count(), 2345);
	}
	
	@Test
	public void testgetCurrencyRateFromDbByDateAndCurrencyCode(){
		//Given
		LocalDate dateOfRequestedCurrencyRate = LocalDate.of(2018, 4, 25);
		String codeOfRequestedCurrencyRate = "USD";
		//When
		Optional<CurrencyRate> searchResult = service.getCurrencyRateFromDbByDateAndCurrencyCode(dateOfRequestedCurrencyRate, codeOfRequestedCurrencyRate);
		Assert.assertEquals(BigDecimal.valueOf(3.45), searchResult.get().getMid());
	}
	
	@Test
	public void testFindMinimumCurrencyMidRateValueInPeriod(){
		//Given
		String currencyCode = "USD";
		LocalDate from = LocalDate.of(2018, 4, 20);
		LocalDate to = LocalDate.of(2018, 4, 25);
		//When
		BigDecimal result = service.findMinimumCurrencyMidRateValueInPeriod(currencyCode, from, to);
		//Then
		Assert.assertEquals(BigDecimal.valueOf(3.39), result);
	}
	
	@Test
	public void testFindMaximumCurrencyMidRateValueInPeriod(){
		//Given
		String currencyCode = "USD";
		LocalDate from = LocalDate.of(2018, 4, 20);
		LocalDate to = LocalDate.of(2018, 4, 25);
		//When
		BigDecimal result = service.findMaximumCurrencyMidRateValueInPeriod(currencyCode, from, to);
		//Then
		Assert.assertEquals(BigDecimal.valueOf(3.45), result);
	}
	
	@Test
	public void testFindTopLowestCurrencyRatesForTheCurrency(){
		//Given
		String currencyCode = "USD";
		int topCount = 5;
		//When
		List<CurrencyRate> resultListOfCurrencyRates = service.findTopLowestCurrencyRatesForTheCurrency(currencyCode, topCount);
		//Then
		Assert.assertEquals(topCount, resultListOfCurrencyRates.size());
	}
	
	@Test
	public void testFindTopHighestCurrencyRatesForTheCurrency(){
		//Given
		String currencyCode = "USD";
		int topCount = 5;
		//When
		List<CurrencyRate> resultListOfCurrencyRates = service.findTopHighestCurrencyRatesForTheCurrency(currencyCode, topCount);
		//Then
		Assert.assertEquals(topCount, resultListOfCurrencyRates.size());
	}
	
	@Test
	public void testSaveCountriesToDB(){
		List<Country> countries = prepareTestCountries();
		Set<Currency> currencies = prepareTestCurrenciesFromCountries(countries);
		for(Currency tempCurrency : currencies) {
			//
		}
		for(Country tempCountry : countries){
			countryDao.save(tempCountry);
		}
	}
	
	@Test
	public void testFindCountriesWithAtLeastTwoCurrencies(){
		//Given&When
		List<Country> resultCountries = service.findCountriesWithAtLeastTwoCurrencies();
		//Then
		Assert.assertEquals(7, resultCountries.size());
	}
	
	@Test
	public void testFindCurrenciesWithMinimumRateDifferenceInPeriod(){
		//Given
		LocalDate from = LocalDate.of(2018, 4, 20);
		LocalDate to = LocalDate.of(2018, 4, 25);
		//When
		List<Currency> resultCurrencies = service.findCurrenciesWithMinimumRateDifferenceInPeriod(from, to);
		//Then
		Assert.assertEquals(14, resultCurrencies.size());
	}
	
	@Test
	public void testFindCurrenciesWithMaximumRateDifferenceInPeriod(){
		//Given
		LocalDate from = LocalDate.of(2018, 4, 20);
		LocalDate to = LocalDate.of(2018, 4, 25);
		//When
		List<Currency> resultCurrencies = service.findCurrenciesWithMaximumRateDifferenceInPeriod(from, to);
		//Then
		Assert.assertEquals(1, resultCurrencies.size());
		Assert.assertEquals("XDR", resultCurrencies.get(0).getCode());
	}
	
	private List<Country> prepareTestCountries(){
		List<CurrencyRate> currencyRates = getCurrencyRatesFromNBPService.getCurrencyRatesFromTable("A");
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
