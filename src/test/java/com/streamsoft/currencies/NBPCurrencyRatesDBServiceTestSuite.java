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
	private static final int RATE_SESSIONS_TOPCOUNT = 67;
	private static final int NO_OF_CURRENCIES_IN_TEST_TABLE = 35;
	private static final int EXPECTED_CURRENCY_RATES_TOPCOUNT = RATE_SESSIONS_TOPCOUNT * NO_OF_CURRENCIES_IN_TEST_TABLE;
	private static final LocalDate TEST_FROM_DATE = LocalDate.of(2018, 4, 20);
	private static final LocalDate TEST_TO_DATE = LocalDate.of(2018, 4, 25);
	private static final LocalDate TTEST_DATE = LocalDate.of(2018, 4, 25);
	private static final String CURRENCY_CODE = "USD";
	private static final String TABLE = "A";
	private static final int DECIMAL_SCALE_OF_CURRENCY_RATE = CurrencyRate.getScaleOfCurrencyRate();
	private static final int TOP_MIN_OR_MAX_VALUES = 5;
	
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
		List<CurrencyRate> currencyRates = getCurrencyRatesFromNBPService.getCurrencyRatesFromTableTopCount(TABLE, RATE_SESSIONS_TOPCOUNT);
		//When
		for(CurrencyRate tempCurrencyRate : currencyRates) {
			service.saveOrUpdateCurrencyRateToDb(tempCurrencyRate);
		}
		//Then
		Assert.assertEquals(EXPECTED_CURRENCY_RATES_TOPCOUNT, currencyRateDao.count());
	}
	
	@Test
	public void testGetCurrencyRateFromDbByDateAndCurrencyCode(){
		//Given
		BigDecimal existingCurrencyRate = BigDecimal.valueOf(3.454800).setScale(DECIMAL_SCALE_OF_CURRENCY_RATE);
		//When
		Optional<CurrencyRate> searchResult = service.getCurrencyRateFromDbByDateAndCurrencyCode(TTEST_DATE, CURRENCY_CODE);
		Assert.assertEquals(existingCurrencyRate, searchResult.get().getMid());
	}
	
	@Test
	public void testFindMinimumCurrencyMidRateValueInPeriod(){
		//Given
		BigDecimal existingCurrencyRate = BigDecimal.valueOf(3.388100).setScale(DECIMAL_SCALE_OF_CURRENCY_RATE);
		//When
		BigDecimal result = service.findMinimumCurrencyMidRateValueInPeriod(CURRENCY_CODE, TEST_FROM_DATE, TEST_TO_DATE);
		//Then
		Assert.assertEquals(existingCurrencyRate, result);
	}
	
	@Test
	public void testFindMaximumCurrencyMidRateValueInPeriod(){
		//Given
		BigDecimal existingCurrencyRate = BigDecimal.valueOf(3.454800).setScale(DECIMAL_SCALE_OF_CURRENCY_RATE);
		//When
		BigDecimal result = service.findMaximumCurrencyMidRateValueInPeriod(CURRENCY_CODE, TEST_FROM_DATE, TEST_TO_DATE);
		//Then
		Assert.assertEquals(existingCurrencyRate, result);
	}
	
	@Test
	public void testFindTopLowestCurrencyRatesForTheCurrency(){
		//Given&When
		List<CurrencyRate> resultListOfCurrencyRates = service.findTopLowestCurrencyRatesForTheCurrency(CURRENCY_CODE, TOP_MIN_OR_MAX_VALUES);
		//Then
		Assert.assertEquals(TOP_MIN_OR_MAX_VALUES, resultListOfCurrencyRates.size());
	}
	
	@Test
	public void testFindTopHighestCurrencyRatesForTheCurrency(){
		//Given&When
		List<CurrencyRate> resultListOfCurrencyRates = service.findTopHighestCurrencyRatesForTheCurrency(CURRENCY_CODE, TOP_MIN_OR_MAX_VALUES);
		//Then
		Assert.assertEquals(TOP_MIN_OR_MAX_VALUES, resultListOfCurrencyRates.size());
	}
	
	@Test
	public void testFindCountriesWithAtLeastTwoCurrencies(){
		//Given
		saveCountriesToDB();
		int existingNumberOfCurrenciesMatchingCiteria = 7;
		//When
		List<Country> resultCountries = service.findCountriesWithAtLeastTwoCurrencies();
		//Then
		Assert.assertEquals(existingNumberOfCurrenciesMatchingCiteria, resultCountries.size());
	}
	
	@Test
	public void testFindCurrenciesWithMinimumRateDifferenceInPeriod(){
		//Given
		int existingNumberOfCurrenciesMatchingCiteria = 1;
		//When
		List<Currency> resultCurrencies = service.findCurrenciesWithMinimumRateDifferenceInPeriod(TEST_FROM_DATE, TEST_TO_DATE);
		//Then
		Assert.assertEquals(existingNumberOfCurrenciesMatchingCiteria, resultCurrencies.size());
	}
	
	@Test
	public void testFindCurrenciesWithMaximumRateDifferenceInPeriod(){
		//Given
		int existingNumberOffCurrenciesMatchingCriteria = 1;
		String existingCurrencyCodeOfCurrencyMatchingCriteria= "XDR";
		//When
		List<Currency> resultCurrencies = service.findCurrenciesWithMaximumRateDifferenceInPeriod(TEST_FROM_DATE, TEST_TO_DATE);
		//Then
		Assert.assertEquals(existingNumberOffCurrenciesMatchingCriteria, resultCurrencies.size());
		Assert.assertEquals(existingCurrencyCodeOfCurrencyMatchingCriteria, resultCurrencies.get(0).getCode());
	}
	
	private void saveCountriesToDB(){
		List<Country> countries = prepareTestCountries();
		Set<Currency> currencies = prepareTestCurrenciesFromCountries(countries);
		for(Currency tempCurrency : currencies) {
			service.saveOrUpdateCurrencyToDb(tempCurrency);
		}
		for(Country tempCountry : countries) {
			service.saveOrUpdateCountryToDb(tempCountry);
		}
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
