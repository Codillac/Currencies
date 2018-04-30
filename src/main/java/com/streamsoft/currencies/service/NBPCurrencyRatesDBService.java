package com.streamsoft.currencies.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.repository.CurrencyRateDao;

@Service
public class NBPCurrencyRatesDBService {
	@Autowired
	CurrencyRateDao currencyRateDao;
	
	public void saveCurrencyRates(List<CurrencyRate> rates){
		currencyRateDao.saveAll(rates);
	}
}