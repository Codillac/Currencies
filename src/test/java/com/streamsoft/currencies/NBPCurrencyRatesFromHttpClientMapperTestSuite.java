package com.streamsoft.currencies;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.domain.NBPRateFromTableDto;
import com.streamsoft.currencies.domain.NBPRatesFromTableDto;
import com.streamsoft.currencies.mapper.NBPCurrencyRatesFromHttpClientMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NBPCurrencyRatesFromHttpClientMapperTestSuite {
	@Autowired
	NBPCurrencyRatesFromHttpClientMapper mapper;
	
	@Test
	public void testMapToExchangeRatesFromTable(){
		//Given
		NBPRateFromTableDto rateDto1 = new NBPRateFromTableDto("dolar amerykancky", "USD", BigDecimal.valueOf(4.30), null, null);
		NBPRateFromTableDto rateDto2 = new NBPRateFromTableDto("franek zlodziej", "CHF", BigDecimal.valueOf(9.99), null, null);
		NBPRateFromTableDto rateDto3 = new NBPRateFromTableDto("pepickie kupidelko", "CZK", BigDecimal.valueOf(0.99), null, null);
		NBPRateFromTableDto rateDto4 = new NBPRateFromTableDto("zloto emigranta", "GBP", BigDecimal.valueOf(5), null, null);
		List<NBPRateFromTableDto> listOne = new ArrayList<>();
		List<NBPRateFromTableDto> listTwo = new ArrayList<>();
		listOne.add(rateDto1);
		listOne.add(rateDto2);
		listTwo.add(rateDto3);
		listTwo.add(rateDto4);
		NBPRatesFromTableDto ratesDto1 = new NBPRatesFromTableDto("A", "099-2018", LocalDate.now(), LocalDate.now(), listOne);
		NBPRatesFromTableDto ratesDto2 = new NBPRatesFromTableDto("A", "098-2018", LocalDate.now().minusDays(1), LocalDate.now().minusDays(1), listTwo);
		List<NBPRatesFromTableDto> ratesList = new ArrayList<>();
		ratesList.add(ratesDto1);
		ratesList.add(ratesDto2);
		NBPRatesFromTableDto[] ratesArray = new NBPRatesFromTableDto[ratesList.size()];
		ratesArray = ratesList.toArray(ratesArray);
		//When
		final List<CurrencyRate> resultList = mapper.mapToExchangeRatesFromTable(ratesArray);
		//Then
		resultList.stream().forEach(r -> System.out.println(r.getCurrency().getCode() + ", " + r.getCurrency().getName() + ", " + r.getMid()));
		Assert.assertEquals(4, resultList.size());
	}
}
