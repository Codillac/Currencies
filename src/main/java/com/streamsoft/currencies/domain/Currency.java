package com.streamsoft.currencies.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CURRENCIES")
public class Currency {
	private Long id;
	private String name;
	private String code;
	private List<CurrencyRate> currencyRates;
	private Set<Country> countries = new HashSet<>();

	public Currency() {
	}

	public Currency(String name, String code) {
		this.name = name;
		this.code = code;
	}

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_CURRENCY", unique = true)
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
	
	@ManyToMany(mappedBy = "currencies")
	public Set<Country> getCountries() {
		return countries;
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

	public void setCountries(Set<Country> countries) {
		this.countries = countries;
	}
}