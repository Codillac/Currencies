package com.streamsoft.currencies.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CURRENCY_RATES")
public class CurrencyRate {
	private Long id;
	private RateSession rateSession;
	private Currency currency;
	private BigDecimal mid;
	private BigDecimal bid;
	private BigDecimal ask;

	public CurrencyRate() {
	}

	public CurrencyRate(RateSession rateSession, Currency currency, BigDecimal mid, BigDecimal bid, BigDecimal ask) {
		this.rateSession = rateSession;
		this.currency = currency;
		this.mid = mid;
		this.bid = bid;
		this.ask = ask;
	}

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_CURRENCY_RATE", unique = true)
	public long getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "ID_RATE_SESSION")
	public RateSession getRateSession() {
		return rateSession;
	}

	@ManyToOne
	@JoinColumn(name = "ID_CURRENCY")
	public Currency getCurrency() {
		return currency;
	}

	@Column(name = "MID")
	public BigDecimal getMid() {
		return mid;
	}

	@Column(name = "BID")
	public BigDecimal getBid() {
		return bid;
	}

	@Column(name = "ASK")
	public BigDecimal getAsk() {
		return ask;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setRateSession(RateSession rateSession) {
		this.rateSession = rateSession;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public void setMid(BigDecimal mid) {
		this.mid = mid;
	}

	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}

	public void setAsk(BigDecimal ask) {
		this.ask = ask;
	}
}
