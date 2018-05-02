package com.streamsoft.currencies.domain.factory;

import com.streamsoft.currencies.domain.Country;
import com.streamsoft.currencies.domain.Currency;

public class CountryFactory {
	public final Country makeCountry(Currency currency) {
		switch(currency.getCode()) {
		case "USD": return new Country("Stany zjednoczone", "USD");
		default: return null;
		}
	}
}
