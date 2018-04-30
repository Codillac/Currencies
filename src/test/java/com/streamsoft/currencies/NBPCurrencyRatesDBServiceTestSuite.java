package com.streamsoft.currencies;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.streamsoft.currencies.domain.Country;
import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.repository.CountryDao;
import com.streamsoft.currencies.service.NBPCurrencyRatesDBService;
import com.streamsoft.currencies.service.NBPGetCurrencyRatesService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NBPCurrencyRatesDBServiceTestSuite {
	@Autowired
	CountryDao countryDao;
	
	@Autowired
	NBPCurrencyRatesDBService dbService;
	
	@Autowired
	NBPGetCurrencyRatesService getCurrenciesService;
	
	@Test
	public void testSaveAllDownloadedCurrencyRates() {
		//Given
		countryDao.save(new Country("Stany Zjednoczone", "USA"));
		List<CurrencyRate> rates = getCurrenciesService.getCurrencyRatesForCurrency("A", "USD");
		dbService.saveCurrencyRates(rates);
	}
}
