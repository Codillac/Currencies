package com.streamsoft.currencies.exceptions;

public class NoCurrencyRatesException extends Exception {

	public NoCurrencyRatesException(final String message) {
		super(message);
	}
}