package com.streamsoft.currencies.repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.streamsoft.currencies.domain.Currency;

@Transactional
@Repository
public interface CurrencyDao extends CrudRepository<Currency, Long> {
	Optional<Currency> findByCode(String code);
	
	@Query(value = "select * from currencies where currencies.id_currency = any (select ids from ("
			+ "select (max(mid) - min(mid)) as diffs, currency_rates.id_currency as ids from currency_rates "
			+ "join rate_sessions on rate_sessions.id_rate_session = currency_rates.id_rate_session "
			+ "where rate_sessions.eff_date between :from and :to "
			+ "group by currency_rates.id_currency "
			+ "order by diffs asc) minvalues "
			+ "join currencies on currencies.id_currency = ids "
			+ "where diffs = (select min(minvalue.diff) from "
			+ "(select (max(mid) - min(mid)) as diff, currency_rates.id_currency as curid from currency_rates "
			+ "join rate_sessions on rate_sessions.id_rate_session = currency_rates.id_rate_session "
			+ "where rate_sessions.eff_date between :from and :to "
			+ "group by currency_rates.id_currency "
			+ "order by diff asc) minvalue) "
			+ "order by ids)", nativeQuery = true)
//	@Query("select cr from Currency cr "
//			+ "join cr.currencyRates crrt "
//			+ "join crrt.rateSession rs "
//			+ "where rs.effectiveDate between :from and :to "
//			+ "and crrt.mid = (select min(select max(crrt.mid) - min(crrt.mid) from crrt "
//			+ "group by cr.code) from crrt")
	List<Currency> findCurrenciesWithMinimumRateDifferenceInPeriod(@Param("from") LocalDate from, @Param("to") LocalDate to);
	
	@Query(value = "select * from currencies where currencies.id_currency = any (select ids from ("
			+ "select (max(mid) - min(mid)) as diffs, currency_rates.id_currency as ids from currency_rates "
			+ "join rate_sessions on rate_sessions.id_rate_session = currency_rates.id_rate_session "
			+ "where rate_sessions.eff_date between :from and :to "
			+ "group by currency_rates.id_currency "
			+ "order by diffs asc) minvalues "
			+ "join currencies on currencies.id_currency = ids "
			+ "where diffs = (select max(minvalue.diff) from "
			+ "(select (max(mid) - min(mid)) as diff, currency_rates.id_currency as curid from currency_rates "
			+ "join rate_sessions on rate_sessions.id_rate_session = currency_rates.id_rate_session "
			+ "where rate_sessions.eff_date between :from and :to "
			+ "group by currency_rates.id_currency "
			+ "order by diff asc) minvalue) "
			+ "order by ids)", nativeQuery = true)
//	@Query("select cur from Currency cur"
//			+ "")
	List<Currency> findCurrenciesWithMaximumRateDifferenceInPeriod(@Param("from") LocalDate from, @Param("to") LocalDate to);
}