package com.streamsoft.currencies.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.streamsoft.currencies.domain.Currency;
import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.domain.RateSession;
import com.streamsoft.currencies.domain.NBPRateFromCurrencyDto;
import com.streamsoft.currencies.domain.NBPRateFromTableDto;
import com.streamsoft.currencies.domain.NBPRatesFromCurrencyDto;
import com.streamsoft.currencies.domain.NBPRatesFromTableDto;

@Component
public final class NBPCurrencyRatesFromHttpClientMapper {
	
	public List<CurrencyRate> mapToExchangeRatesFromCurrency(NBPRatesFromCurrencyDto ratesDto){
		Currency currency = new Currency(ratesDto.getCurrency(), ratesDto.getCode());
		List<CurrencyRate> currencyRates = new ArrayList<>();
		for(NBPRateFromCurrencyDto operationalRateForCur : ratesDto.getRates()) {
			RateSession operationalRate = new RateSession(operationalRateForCur.getNo(), ratesDto.getTable(), operationalRateForCur.getEffectiveDate(), operationalRateForCur.getTradingDate());
			currencyRates.add(new CurrencyRate(operationalRate, currency, operationalRateForCur.getMid(), operationalRateForCur.getBid(), operationalRateForCur.getAsk()));
		}
		return currencyRates;
	}
	
	public List<CurrencyRate> mapToExchangeRatesFromTable(NBPRatesFromTableDto[] ratesDto){
		List<NBPRatesFromTableDto> ratesFromTableDtoList = Arrays.asList(ratesDto);		
		List<CurrencyRate> finalListOfCurrencyRates = new ArrayList<>();
		for(NBPRatesFromTableDto currentRatesFromTableDtoList : ratesFromTableDtoList) {
			RateSession rate = new RateSession(currentRatesFromTableDtoList.getNo(), currentRatesFromTableDtoList.getTable(), currentRatesFromTableDtoList.getEffectiveDate(), currentRatesFromTableDtoList.getTradingDate());
			for(NBPRateFromTableDto currentRate : currentRatesFromTableDtoList.getRates()) {
				Currency currency = new Currency(currentRate.getCurrency(), currentRate.getCode());
				finalListOfCurrencyRates.add(new CurrencyRate(rate, currency, currentRate.getMid().orElse(null), currentRate.getBid().orElse(null), currentRate.getAsk().orElse(null)));
			}
		}
		return finalListOfCurrencyRates;
	}
}