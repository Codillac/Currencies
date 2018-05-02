package com.streamsoft.currencies.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.streamsoft.currencies.domain.Currency;

public interface CurrencyDao extends CrudRepository<Currency, Long> {
	Optional<Currency> findByCode(String code);
}