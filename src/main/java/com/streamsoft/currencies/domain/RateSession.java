package com.streamsoft.currencies.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "RATE_SESSIONS")
public class RateSession {
	private Long id;
	private String number;
	private String table;
	private LocalDate effectiveDate;
	private LocalDate tradingDate;
	private List<CurrencyRate> currencyRates = new ArrayList<>();

	public RateSession() {
	}

	public RateSession(String number, String table, LocalDate effectiveDate, LocalDate tradingDate) {
		this.number = number;
		this.table = table;
		this.effectiveDate = effectiveDate;
		this.tradingDate = tradingDate;
	}

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_RATE_SESSION", unique = true)
	public Long getId() {
		return id;
	}

	@NotNull
	@Column(name = "RATE_NO", unique = true)
	public String getNumber() {
		return number;
	}

	@Column(name = "TABLE_NAME")
	public String getTable() {
		return table;
	}

	@Column(name = "EFF_DATE")
	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	@Column(name = "TRADING_DATE")
	public LocalDate getTradingDate() {
		return tradingDate;
	}

	@OneToMany(targetEntity = CurrencyRate.class, mappedBy = "rateSession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<CurrencyRate> getCurrencyRates() {
		return currencyRates;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setTradingDate(LocalDate tradingDate) {
		this.tradingDate = tradingDate;
	}

	public void setCurrencyRates(List<CurrencyRate> currencyRates) {
		this.currencyRates = currencyRates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RateSession other = (RateSession) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}
}
