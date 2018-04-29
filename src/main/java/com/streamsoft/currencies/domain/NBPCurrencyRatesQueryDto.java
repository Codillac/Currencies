package com.streamsoft.currencies.domain;

import java.time.LocalDate;
import java.util.Optional;

public class NBPCurrencyRatesQueryDto {
	final String table;
	final String code;
	final Integer topCount;
	final LocalDate date;
	final LocalDate startDate;
	final LocalDate endDate;
	
	public NBPCurrencyRatesQueryDto(String table) {
		this.table = table;
		this.code = null;
		this.topCount = null;
		this.date = null;
		this.startDate = null;
		this.endDate = null;
	}

	public NBPCurrencyRatesQueryDto(String table, Integer topCount) {
		this.table = table;
		this.code = null;
		this.topCount = topCount;
		this.date = null;
		this.startDate = null;
		this.endDate = null;
	}


	public NBPCurrencyRatesQueryDto(String table, LocalDate date) {
		this.table = table;
		this.code = null;
		this.topCount = null;
		this.date = date;
		this.startDate = null;
		this.endDate = null;
	}

	public NBPCurrencyRatesQueryDto(String table, LocalDate startDate,
			LocalDate endDate) {
		this.table = table;
		this.code = null;
		this.topCount = null;
		this.date = null;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public NBPCurrencyRatesQueryDto(String table, String code) {
		this.table = table;
		this.code = code;
		this.topCount = null;
		this.date = null;
		this.startDate = null;
		this.endDate = null;
	}

	public NBPCurrencyRatesQueryDto(String table, String code, Integer topCount) {
		this.table = table;
		this.code = code;
		this.topCount = topCount;
		this.date = null;
		this.startDate = null;
		this.endDate = null;
	}
	public NBPCurrencyRatesQueryDto(String table, String code, LocalDate date) {
		this.table = table;
		this.code = code;
		this.topCount = null;
		this.date = date;
		this.startDate = null;
		this.endDate = null;
	}

	public NBPCurrencyRatesQueryDto(String table, String code, LocalDate startDate,
			LocalDate endDate) {
		this.table = table;
		this.code = code;
		this.topCount = null;
		this.date = null;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getTable() {
		return table;
	}

	public Optional <String> getOptionalCode() {
		return Optional.ofNullable(code);
	}

	public Optional <Integer> getOptionalTopCount() {
		return Optional.ofNullable(topCount);
	}

	public Optional <LocalDate> getOptionalDate() {
		return Optional.ofNullable(date);
	}

	public Optional <LocalDate> getOptionalStartDate() {
		return Optional.ofNullable(startDate);
	}

	public Optional <LocalDate> getOptionalEndDate() {
		return Optional.ofNullable(endDate);
	}
}
