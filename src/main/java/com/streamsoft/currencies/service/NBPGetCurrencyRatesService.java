package com.streamsoft.currencies.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.streamsoft.currencies.client.NBPGetCurrencyRatesHttpClient;
import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.domain.NBPCurrencyRatesQueryDto;
import com.streamsoft.currencies.mapper.NBPCurrencyRatesFromHttpClientMapper;

@Service
public class NBPGetCurrencyRatesService {
	@Autowired
	private NBPGetCurrencyRatesHttpClient httpClient;

	@Autowired
	private NBPCurrencyRatesFromHttpClientMapper httpResponseMapper;

	public List<CurrencyRate> getCurrencyRatesFromTable(final String table) {
		return httpResponseMapper.mapToExchangeRatesFromTable(
				httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, null, null, null, null, null)));
	}

	public List<CurrencyRate> getCurrencyRatesFromTableDate(final String table, final LocalDate date) {
		return httpResponseMapper.mapToExchangeRatesFromTable(
				httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, null, null, date, null, null)));
	}

	public List<CurrencyRate> getCurrencyRatesFromTableTopCount(final String table, final Integer topCount) {
		return httpResponseMapper.mapToExchangeRatesFromTable(
				httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, null, topCount, null, null, null)));
	}

	public List<CurrencyRate> getCurrencyRatesFromTablePeriod(final String table, final LocalDate startDate, final LocalDate endDate) {
		return httpResponseMapper.mapToExchangeRatesFromTable(
				httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, null, null, null, startDate, endDate)));
	}

	public List<CurrencyRate> getCurrencyRatesForCurrency(final String table, final String code) {
		return httpResponseMapper.mapToExchangeRatesFromTable(
				httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, code, null, null, null, null)));
	}

	public List<CurrencyRate> getCurrencyRatesForCurrencyDate(final String table, final String code, final LocalDate date) {
		return httpResponseMapper.mapToExchangeRatesFromTable(
				httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, code, null, date, null, null)));
	}

	public List<CurrencyRate> getCurrencyRatesForCurrencyTopCount(final String table, final String code, final Integer topCount) {
		return httpResponseMapper.mapToExchangeRatesFromTable(
				httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, code, topCount, null, null, null)));
	}

	public List<CurrencyRate> getCurrencyRatesForCurrencyPeriod(final String table, final String code, final LocalDate startDate,
			LocalDate endDate) {
		return httpResponseMapper.mapToExchangeRatesFromTable(
				httpClient.getTableRates(new NBPCurrencyRatesQueryDto(table, code, null, null, startDate, endDate)));
	}
}
