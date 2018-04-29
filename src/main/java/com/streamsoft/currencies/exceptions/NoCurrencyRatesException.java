package com.streamsoft.currencies.exceptions;

public class NoCurrencyRatesException extends NullPointerException {

	public NoCurrencyRatesException(final String message) {
		super(message);
	}
}