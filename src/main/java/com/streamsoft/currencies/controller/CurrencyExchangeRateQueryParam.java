package com.streamsoft.currencies.controller;

import java.time.LocalDate;
import java.util.Optional;

public final class CurrencyExchangeRateQueryParam {
	private final String table;
	private final String code;
	private final Integer topCount;
	private final LocalDate date;
	private final LocalDate startDate;
	private final LocalDate endDate;
	
	public CurrencyExchangeRateQueryParam(String table) {
		this.table = table;
		this.code = null;
		this.topCount = null;
		this.date = null;
		this.startDate = null;
		this.endDate = null;
	}
	
	public CurrencyExchangeRateQueryParam(String table, String code) {
		this.table = table;
		this.code = code;
		this.topCount = null;
		this.date = null;
		this.startDate = null;
		this.endDate = null;
	}



	public String getTable() {
		return table;
	}
	public Optional<String> getCode() {
		return Optional.ofNullable(code);
	}
	public Optional<Integer> getTopCount() {
		return Optional.ofNullable(topCount);
	}
	public Optional<LocalDate> getDate() {
		return Optional.ofNullable(date);
	}
	public Optional<LocalDate> getStartDate() {
		return Optional.ofNullable(startDate);
	}
	public Optional<LocalDate> getEndDate() {
		return Optional.ofNullable(endDate);
	}
}
