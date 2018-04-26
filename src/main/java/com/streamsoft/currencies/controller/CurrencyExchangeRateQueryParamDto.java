package com.streamsoft.currencies.controller;

import java.time.LocalDate;

public class CurrencyExchangeRateQueryParamDto {
	private String table;
	private  String code;
	private  Integer topCount;
	private  LocalDate date;
	private  LocalDate startDate;
	private  LocalDate endDate;
		
	public CurrencyExchangeRateQueryParamDto(String table, String code) {
		this.table = table;
		this.code = code;
	}

	public CurrencyExchangeRateQueryParamDto(String table, String code, Integer topCount, LocalDate date, LocalDate startDate,
			LocalDate endDate) {
		this.table = table;
		this.code = code;
		this.topCount = topCount;
		this.date = date;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getTable() {
		return table;
	}

	public String getCode() {
		return code;
	}

	public Integer getTopCount() {
		return topCount;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}
}
