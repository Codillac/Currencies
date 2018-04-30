package com.streamsoft.currencies.domain;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "COUNTRIES")
public class Country {
	private Long id;
	private String name;
	private List<Currency> currencies;
	
	public Country() {
	}
	
	public Country(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name = "ID_COUNTRY", unique = true)
	public long getId() {
		return id;
	}
	
	@Column(name = "COUNTRY_NAME")
	public String getName() {
		return name;
	}

	@ManyToMany
	@JoinTable(name = "JOIN_CURRENCY_COUNTRY",
				joinColumns = {@JoinColumn(name = "ID_COUNTRY", referencedColumnName = "ID_COUNTRY")},
				inverseJoinColumns = {@JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")}
	)
	public List<Currency> getCurrencies() {
		return currencies;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}
}
