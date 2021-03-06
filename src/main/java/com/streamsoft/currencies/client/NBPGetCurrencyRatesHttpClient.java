package com.streamsoft.currencies.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.streamsoft.currencies.domain.NBPCurrencyRatesQueryDto;
import com.streamsoft.currencies.domain.NBPRatesFromCurrencyDto;
import com.streamsoft.currencies.domain.NBPRatesFromTableDto;

@Component
public class NBPGetCurrencyRatesHttpClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(NBPGetCurrencyRatesHttpClient.class);

	private final static String NBP_API_ENDPOINT = "http://api.nbp.pl/api/exchangerates/";
	private final static String TABLES = "tables/";
	private final static String RATES = "rates/";

	@Autowired
	private RestTemplate restTemplate;

	public NBPRatesFromCurrencyDto getCurrencyRates(final NBPCurrencyRatesQueryDto query) {
		LOGGER.info(this.getClass().getName() + ": requesting GET for CurrencyRates.");
		return restTemplate.getForObject(buildUrl(query, RATES), NBPRatesFromCurrencyDto.class);
	}

	public NBPRatesFromTableDto[] getTableRates(final NBPCurrencyRatesQueryDto query) {
		LOGGER.info(this.getClass().getName() + ": requesting GET for TableRates.");
		return restTemplate.getForObject(buildUrl(query, TABLES), NBPRatesFromTableDto[].class);
	}

	private String buildUrl(final NBPCurrencyRatesQueryDto queryParam, final String methodConstant) {
		String url = NBP_API_ENDPOINT + methodConstant + queryParam.getTable() + "/";
		if (queryParam.getOptionalCode().isPresent() && methodConstant == RATES) {
			url += queryParam.getOptionalCode().get() + "/";
		}
		if (queryParam.getOptionalDate().isPresent()) {
			url += queryParam.getOptionalDate().get() + "/";
		} else if (queryParam.getOptionalStartDate().isPresent() && queryParam.getOptionalEndDate().isPresent()) {
			url += queryParam.getOptionalStartDate().get() + "/";
			url += queryParam.getOptionalEndDate().get() + "/";
		} else if (queryParam.getOptionalTopCount().isPresent()) {
			url += "last/" + queryParam.getOptionalTopCount().get() + "/";
		}
		return url;
	}
}
