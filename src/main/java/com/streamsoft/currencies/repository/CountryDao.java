package com.streamsoft.currencies.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.streamsoft.currencies.domain.Country;

@Transactional
@Repository
public interface CountryDao extends CrudRepository<Country, Long> {
	Optional<Country> findByCode(String code);
	
	@Query("select cntr from Country cntr "
			+ "join cntr.currencies cr "
			+ "group by cntr.code "
			+ "having count(*) >= 2")
	List<Country> findCountriesWithAtLeastTwoCurrencies();
}