package com.streamsoft.currencies.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
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
			+ "join crrt.currency cr "
			+ "join crrt.rateSession rs "
			+ "where cr.code = :currencyCode and rs.effectiveDate between :from and :to")
	BigDecimal findMinimumCurrencyMidRateValueInPeriod(@Param("currencyCode")String currencyCode, @Param("from") LocalDate from, @Param("to") LocalDate to);
	
	@Query("select max(crrt.mid) from CurrencyRate crrt "
			+ "join crrt.currency cr "
			+ "join crrt.rateSession rs "
			+ "where cr.code = :currencyCode and rs.effectiveDate between :from and :to")
	BigDecimal findMaximumCurrencyMidRateValueInPeriod(@Param("currencyCode")String currencyCode, @Param("from") LocalDate from, @Param("to") LocalDate to);
		
	@Query(value = "select crrt from CurrencyRate crrt "
			+ "join crrt.currency cr "
			+ "where cr.code = :currencyCode order by crrt.mid asc")
	List<CurrencyRate> findTopLowestCurrencyRatesForTheCurrency(@Param("currencyCode") String currencyCode, Pageable pageable);
		
	@Query("select crrt from CurrencyRate crrt "
			+ "join crrt.currency cr "
			+ "where cr.code = :currencyCode order by crrt.mid desc")
	List<CurrencyRate> findTopHighestCurrencyRatesForTheCurrency(@Param("currencyCode") String currencyCode, Pageable pageable);
}