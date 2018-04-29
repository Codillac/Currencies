package com.streamsoft.currencies;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.service.NBPGetCurrencyRatesService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NBPGetCurrencyRatesServiceTestSuite {
	private static final String TABLE_A = "A";
	private static final String TABLE_B = "B";
	private static final String TABLE_C = "C";
	
	@Autowired
	NBPGetCurrencyRatesService service;
	
	@Test
	public void testGetCurrencyRatesFromTable(){
		//Given&When
		List<CurrencyRate> result1 = service.getCurrencyRatesFromTable(TABLE_A);
		List<CurrencyRate> result2 = service.getCurrencyRatesFromTable(TABLE_B);
		List<CurrencyRate> result3 = service.getCurrencyRatesFromTable(TABLE_C);
		//Then
		result1.stream().forEach(r -> System.out.println(r.getCurrency().getCode() + ", " + r.getCurrency().getName() + ": " + r.getMid()));
		result2.stream().forEach(r -> System.out.println(r.getCurrency().getCode() + ", " + r.getCurrency().getName() + ": " + r.getMid()));
		result3.stream().forEach(r -> System.out.println(r.getCurrency().getCode() + ", " + r.getCurrency().getName() + ": " + r.getAsk() + " | " + r.getBid()));
		System.out.println();
		Assert.assertTrue(!result1.isEmpty());
		Assert.assertTrue(!result2.isEmpty());
		Assert.assertTrue(!result3.isEmpty());
	}
	
	@Test
	public void testGetCurrencyRatesFromTableDate(){
		//Given&When
		List<CurrencyRate> result = service.getCurrencyRatesFromTableDate(TABLE_A, LocalDate.of(2018, 4, 25));
		//Then
		result.stream().forEach(r -> System.out.println(r.getCurrency().getCode() + ", " + r.getCurrency().getName() + ": " + r.getMid() + " / " + r.getRate().getEffectiveDate()));
		Assert.assertTrue(!result.isEmpty());
	}
}
