package com.streamsoft.currencies.controller;

import java.time.LocalDate;
import java.util.Optional;

public class NBPCurrencyRatesQueryDto {
	final String table;
	final String code;
	final Integer topCount;
	final LocalDate date;
	final LocalDate startDate;
	final LocalDate endDate;
	
	public NBPCurrencyRatesQueryDto(String table, String code, Integer topCount, LocalDate date, LocalDate startDate,
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

	public Optional <String> getOptionalCode() {
		return Optional.of(code);
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
