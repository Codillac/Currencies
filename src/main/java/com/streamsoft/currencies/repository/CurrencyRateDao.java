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
	
	@Query("select min(crrt.mid) from CurrencyRate crrt "
			+ "inner join crrt.currency cr "
			+ "inner join crrt.rateSession rs "
			+ "where cr.code = :currencyCode and rs.effectiveDate between :from and :to")
	BigDecimal findMinimumCurrencyMidRateValueInPeriod(@Param("currencyCode")String currencyCode, @Param("from") LocalDate from, @Param("to") LocalDate to);
	
	@Query("select max(crrt.mid) from CurrencyRate crrt "
			+ "inner join crrt.currency cr "
			+ "inner join crrt.rateSession rs "
			+ "where cr.code = :currencyCode and rs.effectiveDate between :from and :to")
	BigDecimal findMaximumCurrencyMidRateValueInPeriod(@Param("currencyCode")String currencyCode, @Param("from") LocalDate from, @Param("to") LocalDate to);
		
	@Query(value = "from CurrencyRate crrt "
			+ "inner join crrt.currency cr "
			+ "inner join crrt.rateSession rs "
			+ "where cr.code = :currencyCode order by crrt.mid asc")
	List<CurrencyRate> findTopLowestCurrencyRatesForTheCurrency(@Param("currencyCode") String currencyCode);
		
	@Query(value = "select * from currency_rates "
			+ "join currencies on (currencies.id_currency = currency_rates.id_currency) "
			+ "join rate_sessions on (rate_sessions.id_rate_session = currency_rates.id_rate_session) "
			+ "where currency_code = :currencyCode order by mid desc limit :topCount", nativeQuery=true)
	List<CurrencyRate> findTopHighestCurrencyRatesForTheCurrency(@Param("currencyCode") String currencyCode, @Param("topCount") int topCount);
}