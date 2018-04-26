package com.streamsoft.currencies.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CURRENCY_RATE")
public class CurrencyRate {
	private long id;
	private Rate rate;
	private Currency currency;
	private BigDecimal mid;
	private BigDecimal bid;
	private BigDecimal ask;
	
	public CurrencyRate() {
	}
	
	public CurrencyRate(Rate rate, Currency currency, BigDecimal mid, BigDecimal bid, BigDecimal ask) {
		this.rate = rate;
		this.currency = currency;
		this.mid = mid;
		this.bid = bid;
		this.ask = ask;
	}
	
	@Id
	@NotNull
	@GeneratedValue
	@Column(name = "ID", unique = true)
	public long getId() {
		return id;
	}
	
	@ManyToOne
	@JoinColumn(name = "ID_RATE")
	public Rate getRate() {
		return rate;
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

	public void setRate(Rate rate) {
		this.rate = rate;
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
