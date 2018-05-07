package com.streamsoft.currencies.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.streamsoft.currencies.domain.Currency;
import com.streamsoft.currencies.domain.CurrencyRate;
import com.streamsoft.currencies.domain.RateSession;

@Transactional
@Repository
public interface CurrencyRateDao extends CrudRepository<CurrencyRate, Long> {
	Optional<CurrencyRate> findByRateSessionAndCurrency(RateSession rateSession, Currency currency);
	
	Optional<CurrencyRate> findByRateSessionAndCurrencyCode(RateSession rateSession, String currencyCode);
	
	@Override
	Optional<CurrencyRate> findById(Long id);
	
	@Query(value = "select min(mid) from currency_rates "
			+ "join currencies on (currencies.id_currency = currency_rates.id_currency) "
			+ "join rate_sessions on (rate_sessions.id_rate_session = currency_rates.id_rate_session) "
			+ "where currencies.currency_code = :currencyCode and rate_sessions.eff_date between :from and :to", nativeQuery=true)
	BigDecimal findMinimumCurrencyMidRateValueInPeriod(@Param("currencyCode")String currencyCode, @Param("from") LocalDate from, @Param("to") LocalDate to);
	
	@Query(value = "select max(mid) from currency_rates "
			+ "join currencies on (currencies.id_currency = currency_rates.id_currency) "
			+ "join rate_sessions on (rate_sessions.id_rate_session = currency_rates.id_rate_session) "
			+ "where currencies.currency_code = :currencyCode and rate_sessions.eff_date between :from and :to", nativeQuery=true)
	BigDecimal findMaximumCurrencyMidRateValueInPeriod(@Param("currencyCode")String currencyCode, @Param("from") LocalDate from, @Param("to") LocalDate to);
		
	@Query(value = "select * from currency_rates "
			+ "join currencies on (currencies.id_currency = currency_rates.id_currency) "
			+ "join rate_sessions on (rate_sessions.id_rate_session = currency_rates.id_rate_session) "
			+ "where currency_code = :currencyCode order by mid asc limit :topCount", nativeQuery=true)
	List<CurrencyRate> findTopLowestCurrencyRatesForTheCurrency(@Param("currencyCode") String currencyCode, @Param("topCount") int topCount);
		
	@Query(value = "select * from currency_rates "
			+ "join currencies on (currencies.id_currency = currency_rates.id_currency) "
			+ "join rate_sessions on (rate_sessions.id_rate_session = currency_rates.id_rate_session) "
			+ "where currency_code = :currencyCode order by mid desc limit :topCount", nativeQuery=true)
	List<CurrencyRate> findTopHighestCurrencyRatesForTheCurrency(@Param("currencyCode") String currencyCode, @Param("topCount") int topCount);
}