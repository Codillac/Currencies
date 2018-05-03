package com.streamsoft.currencies.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.streamsoft.currencies.domain.RateSession;

public interface RateSessionDao extends CrudRepository<RateSession, Long> {
	Optional<RateSession> findByNumber(String number);
	Optional<RateSession> findByEffectiveDate(LocalDate effectiveDate);
}
