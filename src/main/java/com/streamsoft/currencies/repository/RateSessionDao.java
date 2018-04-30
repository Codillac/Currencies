package com.streamsoft.currencies.repository;

import org.springframework.data.repository.CrudRepository;

import com.streamsoft.currencies.domain.RateSession;

public interface RateSessionDao extends CrudRepository<RateSession, Long> {
}
