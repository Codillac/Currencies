package com.streamsoft.currencies.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "COUNTRIES")
public class Country {
	private Long id;
	private String name;
	private String code;
	private Set<Currency> currencies = new HashSet<>();
	
	public Country() {
	}
	
	public Country(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	@Id
	@SequenceGenerator(name = "COUNTRIES_SEQ", sequenceName = "COUNTRIES_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="COUNTRIES_SEQ")
	@NotNull
	@Column(name = "ID_COUNTRY", unique = true)
	public Long getId() {
		return id;
	}
	
	@Column(name = "COUNTRY_NAME", unique = true)
	public String getName() {
		return name;
	}
	
	@Column(name = "COUNTRY_CODE")
	public String getCode() {
		return code;
	}

	@ManyToMany
	@JoinTable(name = "JOIN_CURRENCY_COUNTRY",
				joinColumns = {@JoinColumn(name = "ID_COUNTRY", referencedColumnName = "ID_COUNTRY")},
				inverseJoinColumns = {@JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")}
	)
	public Set<Currency> getCurrencies() {
		return currencies;
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

	public void setCurrencies(Set<Currency> currencies) {
		this.currencies = currencies;
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
		Country other = (Country) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
}
