package com.streamsoft.currencies;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.domain.RateSession;
import com.streamsoft.currencies.service.NBPGetCurrencyRatesService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NBPGetCurrencyRatesServiceTestSuite {
	private static final String TABLE_A = "A";
	private static final String TABLE_B = "B";
	private static final String TABLE_C = "C";
	private static final LocalDate TEST_DATE = LocalDate.of(2018, 4, 25);
	private static final LocalDate TEST_START_DATE = LocalDate.of(2018, 04, 16);
	private static final LocalDate TEST_END_DATE = LocalDate.of(2018, 4, 20);
	
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
		List<CurrencyRate> results = service.getCurrencyRatesFromTableDate(TABLE_A, TEST_DATE);
		boolean isDateOfRateCorrect = true;
		for(CurrencyRate currentRate : results){
			if(!currentRate.getRate().getEffectiveDate().equals(TEST_DATE)){
				isDateOfRateCorrect = false;
			}
		}
		//Then
		results.stream().forEach(r -> System.out.println(r.getCurrency().getCode() + ", " + r.getCurrency().getName() + ": " + r.getMid() + " / " + r.getRate().getEffectiveDate()));
		System.out.println();
		Assert.assertTrue(!results.isEmpty());
		Assert.assertTrue(isDateOfRateCorrect);
	}
	
	@Test
	public void testGetCurrencyRatesFromTableTopCount(){
		//Given&When
		List<CurrencyRate> results = service.getCurrencyRatesFromTableTopCount(TABLE_A, 3);
		Set<RateSession> rateSessions = new HashSet<>();
		for(CurrencyRate currentRate : results){
			rateSessions.add(currentRate.getRate());
		}
		//Then
		rateSessions.stream().forEach(rs -> System.out.println(rs.getNumber()));
		System.out.println();
		Assert.assertEquals(3, rateSessions.size());
	}
	
	@Test
	public void testGetCurrencyRatesFromTablePeriod(){
		//Given&When
		List<CurrencyRate> results = service.getCurrencyRatesFromTablePeriod(TABLE_A, TEST_START_DATE, TEST_END_DATE);
		boolean isDateOfRateCorrect = true;
		for(CurrencyRate currentRate : results){
			if(currentRate.getRate().getEffectiveDate().isAfter(TEST_END_DATE) || currentRate.getRate().getEffectiveDate().isBefore(TEST_START_DATE)){
				isDateOfRateCorrect = false;
			}
		}
		//Then
		results.stream().forEach(r -> System.out.println(r.getCurrency().getCode() + ", " + r.getCurrency().getName() + ": " + r.getMid() + " / " + r.getRate().getEffectiveDate()));
		System.out.println();
		Assert.assertTrue(!results.isEmpty());
		Assert.assertTrue(isDateOfRateCorrect);
	}
}
