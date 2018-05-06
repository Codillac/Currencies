package com.streamsoft.currencies.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
		RateSession rateSession = rateSessionDao.findByEffectiveDate(date).get();
		return currencyRateDao.findByRateSessionAndCurrencyCode(rateSession, currencyCode);
	}
	
	public void saveCurrencyRateToDb(CurrencyRate currencyRate){
		Optional<RateSession> rateSession = rateSessionDao.findByNumber(currencyRate.getRateSession().getNumber());
		if(rateSession.isPresent()) {
			currencyRate.setRateSession(rateSession.get());
		}	
		rateSessionDao.save(currencyRate.getRateSession());
		Optional<Currency> currency = currencyDao.findByCode(currencyRate.getCurrency().getCode());
		if(currency.isPresent()) {
			currencyRate.setCurrency(currency.get());
		}
		currencyDao.save(currencyRate.getCurrency());
		Optional<CurrencyRate> existingCurrencyRate = currencyRateDao.findByRateSessionAndCurrency(currencyRate.getRateSession(), currencyRate.getCurrency());
		if(existingCurrencyRate.isPresent()) {
			currencyRate.setId(existingCurrencyRate.get().getId());
		}
		currencyRateDao.save(currencyRate);
	}
	
	public void deleteCurrencyRateFromDb(CurrencyRate currencyRate){
		currencyRateDao.delete(currencyRate);
	}
	
	public BigDecimal findMinimumCurrencyMidRateValueInPeriod(String currencyCode,LocalDate from, LocalDate to){
		return currencyRateDao.findMinimumCurrencyMidRateValueInPeriod(currencyCode, from, to);
	}
	
	public BigDecimal findMaximumCurrencyMidRateValueInPeriod(String currencyCode,LocalDate from, LocalDate to){
		return currencyRateDao.findMaximumCurrencyMidRateValueInPeriod(currencyCode, from, to);
	}
	
	public List<CurrencyRate> findTopLowestCurrencyRatesForTheCurrency(String currencyCode, int topCount){
		return currencyRateDao.findTopLowestCurrencyRatesForTheCurrency(currencyCode, topCount);
	}
	
	public List<CurrencyRate> findTopHighestCurrencyRatesForTheCurrency(String currencyCode, int topCount){
		return currencyRateDao.findTopHighestCurrencyRatesForTheCurrency(currencyCode, topCount);
	}
	
	public List<Country> findCountriesWithAtLeastTwoCurrencies(){
		return countryDao.findCountriesWithAtLeastTwoCurrencies();
	}
	
	public List<Currency> findCurrenciesWithMinimumRateDifferenceInPeriod(LocalDate from, LocalDate to){
		return currencyDao.findCurrenciesWithMinimumRateDifferenceInPeriod(from, to);
	}
	
	public List<Currency> findCurrenciesWithMaximumRateDifferenceInPeriod(LocalDate from, LocalDate to){
		return currencyDao.findCurrenciesWithMaximumRateDifferenceInPeriod(from, to);
	}
}