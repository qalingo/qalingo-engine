/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
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
@Table(name="TECO_DELIVERY_METHOD_COUNTRY")
public class DeliveryMethodCountry extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 4460976314296673958L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Column(name="CODE_COUNTRY")
	private String codeCountry;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="DELIVERY_METHOD_COUNTRY_ID")
	private Set<DeliveryMethodCounty> deliveryMethodCounties = new HashSet<DeliveryMethodCounty>(); 
	
	public DeliveryMethodCountry() {
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

	public Set<DeliveryMethodCounty> getDeliveryMethodCounties() {
		return deliveryMethodCounties;
	}
	
	public void setDeliveryMethodCounties(Set<DeliveryMethodCounty> deliveryMethodCounties) {
		this.deliveryMethodCounties = deliveryMethodCounties;
	}
	
}