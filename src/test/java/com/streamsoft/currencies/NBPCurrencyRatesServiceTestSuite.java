package com.streamsoft.currencies;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.service.NBPCurrencyRatesDBService;
import com.streamsoft.currencies.service.NBPCurrencyRatesService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NBPCurrencyRatesServiceTestSuite {
	@Autowired
	NBPCurrencyRatesService currencyRatesService;
	
	@Autowired
	NBPCurrencyRatesDBService dbService;
	
	@Test
	public void testGetCurrencyRate() {
		//Given
		String code = "USD";
		LocalDate date = LocalDate.of(2018, 4, 25);
		//When
		CurrencyRate resultRate = currencyRatesService.getCurrencyRate(code, date);
		BigDecimal fromDbRateValue = dbService.getCurrencyRateFromDbByDateAndCurrencyCode(date, code).get().getMid();
		//Then
		Assert.assertEquals(resultRate.getMid(), fromDbRateValue);
	}
	
	@Test
	public void testGetAndSaveMillionCurrencyRates() {
		currencyRatesService.getAndSaveMillionCurrencyRates();
	}
}
