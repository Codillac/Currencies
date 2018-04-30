package com.streamsoft.currencies.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.streamsoft.currencies.domain.Country;

@Transactional
@Repository
public interface CountryDao extends CrudRepository<Country, Long> {
}
