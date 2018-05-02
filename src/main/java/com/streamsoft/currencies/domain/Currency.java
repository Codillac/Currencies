package com.streamsoft.currencies.domain;

import java.util.ArrayList;
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
	private List<CurrencyRate> currencyRates = new ArrayList<>();
	private Set<Country> countries = new HashSet<>();

	public Currency() {
	}

	public Currency(String name, String code) {
		this.name = name;
		this.code = code;
	}

	@Id
	@NotNull
	@SequenceGenerator(name = "CURRENCIES_SEQ", sequenceName = "CURRENCIES_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CURRENCIES_SEQ")
	@Column(name = "ID_CURRENCY", unique = true)
	public Long getId() {
		return id;
	}

	@Column(name = "CURRENCY_NAME")
	public String getName() {
		return name;
	}

	@NotNull
	@Column(name = "CURRENCY_CODE", unique = true, length = 10)
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

	public void setId(Long id) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Currency other = (Currency) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
}