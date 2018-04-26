package com.streamsoft.currencies.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.streamsoft.currencies.domain.NBPRatesFromCurrencyDto;
import com.streamsoft.currencies.domain.NBPRatesFromTableDto;

@Component
public class NBPGetCurrencyRatesHttpClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(NBPGetCurrencyRatesHttpClient.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	public NBPRatesFromCurrencyDto getCurrencyRates(final String url) throws RestClientException {
		LOGGER.info(this.getClass().getName() + ": requesting GET for CurrencyRates.");
		return restTemplate.getForObject(url, NBPRatesFromCurrencyDto.class);
	}
	
	public NBPRatesFromTableDto[] getTableRates(final String url) throws RestClientException {
		LOGGER.info(this.getClass().getName() + ": requesting GET for TableRates.");
		return restTemplate.getForObject(url, NBPRatesFromTableDto[].class);
	}
}