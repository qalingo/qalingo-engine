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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.Hibernate;

@Entity
@Table(name="TECO_PAYMENT_GATEWAY", uniqueConstraints = {@UniqueConstraint(columnNames= {"CODE"})})
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="PAYMENT_GATEWAY_TYPE",
    discriminatorType=DiscriminatorType.STRING
)
public abstract class AbstractPaymentGateway extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -8137471618726382479L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PAYMENT_GATEWAY_ID")
    private Set<PaymentGatewayAttribute> attributes = new HashSet<PaymentGatewayAttribute>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.PaymentGatewayOption.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "TECO_PAYMENT_GATEWAY_OPTION_REL", joinColumns = @JoinColumn(name = "PAYMENT_GATEWAY_ID"), inverseJoinColumns = @JoinColumn(name = "PAYMENT_GATEWAY_OPTION_ID"))
    private Set<PaymentGatewayOption> options = new HashSet<PaymentGatewayOption>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.MarketArea.class)
    @JoinTable(name = "TECO_MARKET_AREA_PAYMENT_GATEWAY_REL", joinColumns = @JoinColumn(name = "PAYMENT_GATEWAY_ID"), inverseJoinColumns = @JoinColumn(name = "MARKET_AREA_ID"))
    private Set<MarketArea> marketAreas = new HashSet<MarketArea>();
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
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
	
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
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
	
	public Set<PaymentGatewayAttribute> getAttributes() {
		return attributes;
	}
	
    public boolean updateAttribute(String code, String value) {
        if (attributes != null 
                && Hibernate.isInitialized(attributes)) {
            for (Iterator<PaymentGatewayAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                PaymentGatewayAttribute attribute = (PaymentGatewayAttribute) iterator.next();
                if(code.equals(attribute.getAttributeDefinition().getCode())){
                    attribute.setValue(value);
                    return true;
                }
            }
        }
        return false;
    }
    
    public void addAttribute(MarketArea marketArea, AttributeDefinition attributeDefinition, String value) {
        PaymentGatewayAttribute attribute = new PaymentGatewayAttribute();
        attribute.setAttributeDefinition(attributeDefinition);
        if(marketArea != null){
            attribute.setMarketAreaId(marketArea.getId());
        }
        attribute.setValue(value);
        if (attributes == null) {
            attributes = new HashSet<PaymentGatewayAttribute>();
        }
        attributes.add(attribute);
    }
    
    public List<PaymentGatewayAttribute> getGlobalAttributes() {
        List<PaymentGatewayAttribute> globalAttributes = null;
        if (attributes != null
                && Hibernate.isInitialized(attributes)) {
            globalAttributes = new ArrayList<PaymentGatewayAttribute>();
            for (Iterator<PaymentGatewayAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                PaymentGatewayAttribute attribute = (PaymentGatewayAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && attributeDefinition.isGlobal()) {
                    globalAttributes.add(attribute);
                }
            }
        }        
        return globalAttributes;
    }

    public List<PaymentGatewayAttribute> getMarketAreaAttributes(Long marketAreaId) {
        List<PaymentGatewayAttribute> marketAreaAttributes = null;
        if (attributes != null
                && Hibernate.isInitialized(attributes)) {
            marketAreaAttributes = new ArrayList<PaymentGatewayAttribute>();
            for (Iterator<PaymentGatewayAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                PaymentGatewayAttribute attribute = (PaymentGatewayAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && !attributeDefinition.isGlobal()) {
                    marketAreaAttributes.add(attribute);
                }
            }
        }        
        return marketAreaAttributes;
    }
    
	public void setAttributes(Set<PaymentGatewayAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public Set<PaymentGatewayOption> getOptions() {
        return options;
    }
	
    public boolean updateOption(String code, String value) {
        if (options != null 
                && Hibernate.isInitialized(options)) {
            for (Iterator<PaymentGatewayOption> iterator = options.iterator(); iterator.hasNext();) {
                PaymentGatewayOption option = (PaymentGatewayOption) iterator.next();
                if(code.equals(option.getCode())){
                    option.setOptionValue(value);
                    return true;
                }
            }
        }
        return false;
    }
    
	public void setOptions(Set<PaymentGatewayOption> options) {
        this.options = options;
    }
	
	public Set<MarketArea> getMarketAreas() {
        return marketAreas;
    }
	
    public void addMarketArea(MarketArea marketArea) {
        if(marketAreas == null){
            marketAreas = new HashSet<MarketArea>();
        }
        marketAreas.add(marketArea);
    }
	
	public void setMarketAreas(Set<MarketArea> marketAreas) {
        this.marketAreas = marketAreas;
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
