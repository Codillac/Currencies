package com.streamsoft.currencies.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.streamsoft.currencies.domain.Country;
import com.streamsoft.currencies.domain.Currency;
import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.domain.RateSession;
import com.streamsoft.currencies.repository.CountryDao;
import com.streamsoft.currencies.repository.CurrencyDao;
import com.streamsoft.currencies.repository.CurrencyRateDao;
import com.streamsoft.currencies.repository.RateSessionDao;

@Service
public class NBPCurrencyRatesDBService {
	@Autowired
	CountryDao countryDao;
	
	@Autowired
	CurrencyDao currencyDao;
	
	@Autowired
	RateSessionDao rateSessionDao;
	
	@Autowired
	CurrencyRateDao currencyRateDao;
	
	public Optional<CurrencyRate> getCurrencyRateFromDbById(Long id){
		return currencyRateDao.findById(id);
	}
	
	public Optional<CurrencyRate> getCurrencyRateFromDbByDateAndCurrencyCode(LocalDate date, String currencyCode){
		Optional<RateSession> rateSession = rateSessionDao.findByEffectiveDate(date);
		if(rateSession.isPresent()) {
			return currencyRateDao.findByRateSessionAndCurrencyCode(rateSession.get(), currencyCode);
		}
		return Optional.empty();
	}
	
	public void saveOrUpdateCurrencyRateToDb(CurrencyRate currencyRate){
		Optional<RateSession> rateSession = rateSessionDao.findByNumber(currencyRate.getRateSession().getNumber());
		if(rateSession.isPresent()) {
			currencyRate.setRateSession(rateSession.get());
		} else {
			rateSessionDao.save(currencyRate.getRateSession());
		}
		Optional<Currency> currency = currencyDao.findByCode(currencyRate.getCurrency().getCode());
		if(currency.isPresent()) {
			currencyRate.setCurrency(currency.get());
		} else {
			currencyDao.save(currencyRate.getCurrency());
		}
		Optional<CurrencyRate> existingCurrencyRate = currencyRateDao.findByRateSessionAndCurrency(currencyRate.getRateSession(), currencyRate.getCurrency());
		if(existingCurrencyRate.isPresent()) {
			currencyRate.setId(existingCurrencyRate.get().getId());
		} else {
			currencyRateDao.save(currencyRate);
		}
	}
	
	public void deleteCurrencyRateFromDb(CurrencyRate currencyRate) {
		currencyRateDao.delete(currencyRate);
	}
	
	public BigDecimal findMinimumCurrencyMidRateValueInPeriod(String currencyCode,LocalDate from, LocalDate to) {
		return currencyRateDao.findMinimumCurrencyMidRateValueInPeriod(currencyCode, from, to);
	}
	
	public BigDecimal findMaximumCurrencyMidRateValueInPeriod(String currencyCode,LocalDate from, LocalDate to) {
		return currencyRateDao.findMaximumCurrencyMidRateValueInPeriod(currencyCode, from, to);
	}
	
	public List<CurrencyRate> findTopLowestCurrencyRatesForTheCurrency(String currencyCode, int topCount) {
		Pageable queryLimit = PageRequest.of(0, topCount);
		return currencyRateDao.findTopLowestCurrencyRatesForTheCurrency(currencyCode, queryLimit);
	}
	
	public List<CurrencyRate> findTopHighestCurrencyRatesForTheCurrency(String currencyCode, int topCount) {
		Pageable queryLimit = PageRequest.of(0, topCount);
		return currencyRateDao.findTopHighestCurrencyRatesForTheCurrency(currencyCode, queryLimit);
	}
	
	public List<Country> findCountriesWithAtLeastTwoCurrencies(){
		return countryDao.findCountriesWithAtLeastTwoCurrencies();
	}
	
	public List<Currency> findCurrenciesWithMinimumRateDifferenceInPeriod(LocalDate from, LocalDate to) {
		return currencyDao.findCurrenciesWithMinimumRateDifferenceInPeriod(from, to);
	}
	
	public List<Currency> findCurrenciesWithMaximumRateDifferenceInPeriod(LocalDate from, LocalDate to) {
		Map<Currency, BigDecimal> resultMap = currencyDao.findCurrenciesWithMaximumRateDifferenceInPeriod(from, to);
		BigDecimal maxDifference = BigDecimal.ZERO;
		List<Currency> matchingCurrencies = new ArrayList<>();
		for(Map.Entry<Currency, BigDecimal> entry : resultMap.entrySet()) {
			BigDecimal presentValue = entry.getValue();
			maxDifference = maxDifference.max(presentValue);
		}
		for(Map.Entry<Currency, BigDecimal> entry : resultMap.entrySet()) {
			BigDecimal presentValue = entry.getValue();
			if(presentValue.equals(maxDifference)) {
				matchingCurrencies.add(entry.getKey());
			}
		}
		return matchingCurrencies;
	}
	
	public void saveOrUpdateCountryToDb(Country country) {
		Optional<Country> existingCountry = countryDao.findByCode(country.getCode());
		if(existingCountry.isPresent()){
			country.setId(existingCountry.get().getId());
		} else {
			countryDao.save(country);
		}
	}
	
	public void saveOrUpdateCurrencyToDb(Currency currency) {
		Optional<Currency> existingCurrency = currencyDao.findByCode(currency.getCode());
		if(existingCurrency.isPresent()) {
			currency.setId(existingCurrency.get().getId());
		} else {
			currencyDao.save(currency);
		}
	}
	
	public long countAllCurrencyRatesInDb() {
		return currencyRateDao.count();
	}
}