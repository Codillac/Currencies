package com.streamsoft.currencies.domain;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NBPRatesFromTableDto {
	@JsonProperty("table")
	private String table;
	@JsonProperty("no")
	private String no;
	@JsonProperty("effectiveDate")
	private LocalDate effectiveDate;
	@JsonProperty("tradingDate")
	private LocalDate tradingDate;
	@JsonProperty("rates")
	private List<NBPRateFromTableDto> rates;
	
	public String getTable() {
		return table;
	}
	public String getNo() {
		return no;
	}
	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}
	public LocalDate getTradingDate() {
		return tradingDate;
	}
	public List<NBPRateFromTableDto> getRates() {
		return rates;
	}
}
