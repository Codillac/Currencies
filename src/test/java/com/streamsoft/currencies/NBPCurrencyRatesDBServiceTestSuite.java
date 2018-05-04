package com.streamsoft.currencies;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.streamsoft.currencies.domain.Country;
import com.streamsoft.currencies.domain.CurrencyRate;
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
	public void testFindMinimumCurrencyRateValueInPeriod(){
		//Given
		String currencyCode = "USD";
		LocalDate from = LocalDate.of(2018, 4, 20);
		LocalDate to = LocalDate.of(2018, 4, 25);
		//When
		BigDecimal result = service.findMinimumCurrencyRateValueInPeriod(currencyCode, from, to);
		//Then
		Assert.assertEquals(BigDecimal.valueOf(3.39), result);
	}
	
	@Test
	public void testFindMaximumCurrencyRateValueInPeriod(){
		//Given
		String currencyCode = "USD";
		LocalDate from = LocalDate.of(2018, 4, 20);
		LocalDate to = LocalDate.of(2018, 4, 25);
		//When
		BigDecimal result = service.findMaximumCurrencyRateValueInPeriod(currencyCode, from, to);
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
	public void testFindCountriesWithAtLeastTwoCurrencies(){
		//Given&When
		List<Country> resultCountries = service.findCountriesWithAtLeastTwoCurrencies();
		//Then
		Assert.assertEquals(7, resultCountries.size());
	}
}
