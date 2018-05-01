package com.streamsoft.currencies.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NBPRatesFromCurrencyDto {
	@JsonProperty("table")
	private String table;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("code")
	private String code;
	@JsonProperty("rates")
	private List<NBPRateFromCurrencyDto> rates;
	
	public NBPRatesFromCurrencyDto() {
	}

	public NBPRatesFromCurrencyDto(String table, String currency, String code, List<NBPRateFromCurrencyDto> rates) {
		this.table = table;
		this.currency = currency;
		this.code = code;
		this.rates = rates;
	}

	public String getTable() {
		return table;
	}

	public String getCurrency() {
		return currency;
	}

	public String getCode() {
		return code;
	}

	public List<NBPRateFromCurrencyDto> getRates() {
		return rates;
	}
}
