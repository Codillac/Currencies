package com.streamsoft.currencies;

import java.time.LocalDate;

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
	NBPCurrencyRatesService service;
	
	@Autowired
	NBPCurrencyRatesDBService dbS;
	
	@Test
	public void testGetCurrencyRate() {
		//Given
		String code = "USD";
		LocalDate date = LocalDate.of(2018, 4, 25);
		//When
		CurrencyRate resultRate = service.getCurrencyRate(code, date);
		//Then
		System.out.println(resultRate.getMid());
		System.out.println(dbS.getCurrencyRateFromDbByDateAndCurrencyCode(date, code).get().getMid());
	}
}
