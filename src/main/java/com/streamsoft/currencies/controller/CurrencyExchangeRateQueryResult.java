package com.streamsoft.currencies.controller;

import java.math.BigDecimal;

public class CurrencyExchangeRateQueryResult {
	private BigDecimal currencyRate;
	private BigDecimal valueInPLN;
	public CurrencyExchangeRateQueryResult(BigDecimal currencyRate, BigDecimal valueInPLN) {
		this.currencyRate = currencyRate;
		this.valueInPLN = valueInPLN;
	}
	
	public BigDecimal calculateResult() {
		return valueInPLN.multiply(currencyRate);
	}
}
