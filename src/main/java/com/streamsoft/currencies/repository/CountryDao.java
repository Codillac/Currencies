package com.streamsoft.currencies.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.streamsoft.currencies.domain.Country;

@Transactional
@Repository
public interface CountryDao extends CrudRepository<Country, Long> {
	@Query(value = "select * from countries join join_currency_country on (countries.id_country = join_currency_country.id_country) join currencies on (join_currency_country.id_currency = currencies.id_currency) group by country_name having count(*) >= 2", nativeQuery=true)
	List<Country> findCountriesWithAtLeastTwoCurrencies();
}
