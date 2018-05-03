package com.streamsoft.currencies.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.streamsoft.currencies.domain.Currency;

@Transactional
@Repository
public interface CurrencyDao extends CrudRepository<Currency, Long> {
	Optional<Currency> findByCode(String code);
	
	@Query(value = "", nativeQuery=true)
	List<Currency> findCurrenciesWithTheBiggestDifferenceInPeriod();
}