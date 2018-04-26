package com.streamsoft.currencies.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.streamsoft.currencies.client.NBPGetCurrencyRatesHttpClient;
import com.streamsoft.currencies.controller.CurrencyExchangeRateQueryParam;
import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.domain.NBPRatesFromCurrencyDto;
import com.streamsoft.currencies.domain.NBPRatesFromTableDto;
import com.streamsoft.currencies.mapper.NBPCurrencyRatesDomainMapper;

@Service
public class NBPGetCurrencyRatesService implements GetCurrencyRatesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(NBPGetCurrencyRatesHttpClient.class);
	
	@Autowired
	private NBPGetCurrencyRatesHttpClient client;
	
	@Autowired
	private NBPCurrencyRatesDomainMapper mapper;
	
	private final static String NBP_API_ENDPOINT = "http://api.nbp.pl/api/exchangerates/";
	private final static String TABLES = "tables/";
	private final static String RATES = "rates/";
	
	@Override
	public List<CurrencyRate> getCurrencyRates(final CurrencyExchangeRateQueryParam queryParam) throws com.streamsoft.currencies.exceptions.NoCurrencyRatesException {
		List<CurrencyRate> currencyRates;
		if (queryParam.getCode().isPresent()) {
			currencyRates = requestCurrencyRates(queryParam);
		} else {
			currencyRates = requestTableRates(queryParam);
		}
		if (currencyRates.size() != 0) {
			return currencyRates;
		} else {
			LOGGER.error("No currency rates for querry.");
			throw new com.streamsoft.currencies.exceptions.NoCurrencyRatesException("There are no currency rates for this querry!");
		}
	}
	
	private List<CurrencyRate> requestTableRates(final CurrencyExchangeRateQueryParam queryParam) {
		try {
			LOGGER.info(this.getClass().getName() + ": executing request for Rates from the specified Table.");
			NBPRatesFromTableDto[] ratesFromTableDtoArray = client.getTableRates(buildUrl(queryParam, TABLES));
			List<NBPRatesFromTableDto> ratesFromTableDtoList = Arrays.asList(ratesFromTableDtoArray);
			List<CurrencyRate> finalListOfCurrencyRates = new ArrayList<>();
			for(NBPRatesFromTableDto currentRateFromTableDto :ratesFromTableDtoList) {
				finalListOfCurrencyRates.addAll(mapper.mapToExchangeRatesFromTable(currentRateFromTableDto));
			}
			return finalListOfCurrencyRates;
		} catch (RestClientException e) {
			LOGGER.error(e.getMessage());
			return new ArrayList<>();
		}
	}
	
	private List<CurrencyRate> requestCurrencyRates(final CurrencyExchangeRateQueryParam queryParam) {
		try {
			LOGGER.info(this.getClass().getName() + ": executing request for Rates for the specified Currency.");
			NBPRatesFromCurrencyDto ratesFromCurrencyDto = client.getCurrencyRates(buildUrl(queryParam, RATES));
			return mapper.mapToExchangeRatesFromCurrency(ratesFromCurrencyDto);
		} catch (RestClientException e) {
			LOGGER.error(e.getMessage());
			return new ArrayList<>();
		}
	}
	
	private String buildUrl(final CurrencyExchangeRateQueryParam queryParam, String methodConstant) {
		String url = NBP_API_ENDPOINT + methodConstant + queryParam.getTable() + "/";
		LOGGER.info(url);
		if(queryParam.getCode().isPresent() && methodConstant == RATES) {
			url += queryParam.getCode().get() + "/";
		}
		LOGGER.info(url);
		if(queryParam.getDate().isPresent()) {
			url += queryParam.getDate().get() + "/";
		} else if(queryParam.getStartDate().isPresent() && queryParam.getEndDate().isPresent()) {
			url += queryParam.getStartDate().get() + "/";
			url += queryParam.getEndDate().get() + "/";
		} else if(queryParam.getTopCount().isPresent()) {
			url += "last/" + queryParam.getTopCount().get() + "/";
		}
		return url;
	}
}
