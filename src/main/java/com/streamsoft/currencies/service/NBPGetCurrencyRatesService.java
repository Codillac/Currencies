package com.streamsoft.currencies.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.streamsoft.currencies.client.NBPGetCurrencyRatesHttpClient;
import com.streamsoft.currencies.controller.NBPCurrencyRatesQueryDto;
import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.mapper.NBPCurrencyRatesFromHttpClientMapper;

public class NBPGetCurrencyRatesService {
	@Autowired
	private NBPGetCurrencyRatesHttpClient httpClient;
	
	@Autowired
	private
	NBPCurrencyRatesFromHttpClientMapper httpResponseMapper;
	
	public List<CurrencyRate> getCurrencyRatesFromTable(final String table){
		return httpResponseMapper.mapToExchangeRatesFromTable(httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, null, null, null, null, null )));
	}
	
	public List<CurrencyRate> getCurrencyRates(final String table, LocalDate date){
		return httpResponseMapper.mapToExchangeRatesFromTable(httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, null, null, date, null, null )));
	}
	
	public List<CurrencyRate> getCurrencyRates(final String table, Integer topCount){
		return httpResponseMapper.mapToExchangeRatesFromTable(httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, null, topCount, null, null, null )));
	}
	
	public List<CurrencyRate> getCurrencyRates(final String table, LocalDate startDate, LocalDate endDate){
		return httpResponseMapper.mapToExchangeRatesFromTable(httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, null, null, null, startDate, endDate )));
	}
	
	public List<CurrencyRate> getCurrencyRates(final String table, String code){
		return httpResponseMapper.mapToExchangeRatesFromTable(httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, code, null, null, null, null )));
	}
	
	public List<CurrencyRate> getCurrencyRates(final String table, String code, LocalDate date){
		return httpResponseMapper.mapToExchangeRatesFromTable(httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, code, null, date, null, null )));
	}
	
	public List<CurrencyRate> getCurrencyRates(final String table, String code, Integer topCount){
		return httpResponseMapper.mapToExchangeRatesFromTable(httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, code, topCount, null, null, null )));
	}
	
	public List<CurrencyRate> getCurrencyRates(final String table, String code, LocalDate startDate, LocalDate endDate){
		return httpResponseMapper.mapToExchangeRatesFromTable(httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, code, null, null, startDate, endDate)));
	}
}
