package com.streamsoft.currencies.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.streamsoft.currencies.domain.Currency;
import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.domain.RateSession;

@Transactional
@Repository
public interface CurrencyRateDao extends CrudRepository<CurrencyRate, Long> {
	Optional<CurrencyRate> findByRateSessionAndCurrency(RateSession rateSession, Currency currency);
}