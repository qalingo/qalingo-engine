/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TECO_TAX_COUNTRY")
public class TaxCountry extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -3914688014885715616L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Column(name="CODE_COUNTRY")
	private String codeCountry;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="TAX_COUNTRY_ID")
	private Set<TaxState> taxCounties = new HashSet<TaxState>(); 
	
	public TaxCountry() {
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

	public Set<TaxState> getTaxCounties() {
		return taxCounties;
	}

	public void setTaxeCounties(Set<TaxState> taxCounties) {
		this.taxCounties = taxCounties;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codeCountry == null) ? 0 : codeCountry.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        TaxCountry other = (TaxCountry) obj;
        if (codeCountry == null) {
            if (other.codeCountry != null)
                return false;
        } else if (!codeCountry.equals(other.codeCountry))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TaxCountry [id=" + id + ", codeCountry=" + codeCountry + "]";
    }
	
}