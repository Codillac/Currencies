package com.streamsoft.currencies.repository;

import org.springframework.data.repository.CrudRepository;

import com.streamsoft.currencies.domain.Currency;

public interface CurrencyDao extends CrudRepository<Currency, Long> {
}
