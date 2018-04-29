package com.streamsoft.currencies.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CURRENCY")
public class Currency {
	private long id;
	private String name;
	private String code;
	private List<CurrencyRate> currencyRates;

	public Currency() {
	}

	public Currency(String name, String code) {
		this.name = name;
		this.code = code;
	}

	@Id
	@NotNull
	@GeneratedValue
	@Column(name = "ID", unique = true)
	public long getId() {
		return id;
	}

	@Column(name = "CURRENCY_NAME")
	public String getName() {
		return name;
	}

	@NotNull
	@Column(name = "CURRENCY_CODE", unique = true)
	public String getCode() {
		return code;
	}

	@OneToMany(targetEntity = CurrencyRate.class, mappedBy = "currency", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<CurrencyRate> getCurrencyRates() {
		return currencyRates;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCurrencyRates(List<CurrencyRate> currencyRates) {
		this.currencyRates = currencyRates;
	}
}