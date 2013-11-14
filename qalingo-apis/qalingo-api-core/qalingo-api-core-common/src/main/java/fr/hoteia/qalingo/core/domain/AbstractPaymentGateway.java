/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name="TECO_PAYMENT_GATEWAY")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="PAYMENT_GATEWAY_TYPE",
    discriminatorType=DiscriminatorType.STRING
)
public abstract class AbstractPaymentGateway implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -8137471618726382479L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="NAME")
	private String name;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="CODE")
	private String code;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="PAYMENT_GATEWAY_ID")
	private Set<PaymentGatewayAttribute> paymentGatewayAttributes = new HashSet<PaymentGatewayAttribute>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public AbstractPaymentGateway() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public Set<PaymentGatewayAttribute> getPaymentGatewayAttributes() {
		return paymentGatewayAttributes;
	}
	
	public void setPaymentGatewayAttributes(Set<PaymentGatewayAttribute> paymentGatewayAttributes) {
		this.paymentGatewayAttributes = paymentGatewayAttributes;
	}
	
	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	
	/**
	 * 
	 */
	@Transient
	public void authorizationAndCapture() {
	}
	
	@Transient
	public void authorizationOnly(){
	}

	/**
	 * 
	 */
	@Transient
	public void priorAuthorizationAndCapture() {
	}
	
	/**
	 * 
	 */
	@Transient
	public void capture() {
	}
	
	/**
	 * 
	 */
	@Transient
	public void credit() {
	}
	
	/**
	 * 
	 */
	@Transient
	public void unlinkedCredit() {
	}
	
	/**
	 * 
	 */
	@Transient
	public void voidCreditProcess() {
	}
	
}
