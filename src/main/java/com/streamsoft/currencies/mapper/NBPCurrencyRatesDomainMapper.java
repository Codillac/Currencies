package com.streamsoft.currencies.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.streamsoft.currencies.domain.Currency;
import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.domain.Rate;
import com.streamsoft.currencies.domain.NBPRateFromCurrencyDto;
import com.streamsoft.currencies.domain.NBPRateFromTableDto;
import com.streamsoft.currencies.domain.NBPRatesFromCurrencyDto;
import com.streamsoft.currencies.domain.NBPRatesFromTableDto;

@Component
public final class NBPCurrencyRatesDomainMapper {
	
	public List<CurrencyRate> mapToExchangeRatesFromCurrency(NBPRatesFromCurrencyDto ratesDto){
		Currency currency = new Currency(ratesDto.getCurrency(), ratesDto.getCode());
		List<CurrencyRate> currencyRates = new ArrayList<>();
		for(NBPRateFromCurrencyDto operationalRateForCur : ratesDto.getRates()) {
			Rate operationalRate = new Rate(operationalRateForCur.getNo(), ratesDto.getTable(), operationalRateForCur.getEffectiveDate(), operationalRateForCur.getTradingDate());
			currencyRates.add(new CurrencyRate(operationalRate, currency, operationalRateForCur.getMid(), operationalRateForCur.getBid(), operationalRateForCur.getAsk()));
		}
		return currencyRates;
	}
	
	public List<CurrencyRate> mapToExchangeRatesFromTable(NBPRatesFromTableDto ratesDto){
		Rate rate = new Rate(ratesDto.getNo(), ratesDto.getTable(), ratesDto.getEffectiveDate(), ratesDto.getTradingDate());
		List<CurrencyRate> currencyRates = new ArrayList<>();
		for(NBPRateFromTableDto operationalRate : ratesDto.getRates()) {
			Currency currency = new Currency(operationalRate.getCurrency(), operationalRate.getCode());
			currencyRates.add(new CurrencyRate(rate, currency, operationalRate.getMid().orElse(null), operationalRate.getBid().orElse(null), operationalRate.getAsk().orElse(null)));
		}
		return currencyRates;
	}
}