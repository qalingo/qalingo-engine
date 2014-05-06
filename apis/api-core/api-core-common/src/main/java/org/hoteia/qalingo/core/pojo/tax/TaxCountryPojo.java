/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.tax;

import java.util.HashSet;
import java.util.Set;

public class TaxCountryPojo {

	private Long id;
	private String codeCountry;
	private Set<TaxStatePojo> taxCounties = new HashSet<TaxStatePojo>(); 
	
	public TaxCountryPojo() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeCountry() {
		return codeCountry;
	}
	
	public void setCodeCountry(String codeCountry) {
		this.codeCountry = codeCountry;
	}

	public Set<TaxStatePojo> getTaxCounties() {
		return taxCounties;
	}

	public void setTaxeCounties(Set<TaxStatePojo> taxCounties) {
		this.taxCounties = taxCounties;
	}
	
}