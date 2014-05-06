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

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

@Entity
@Table(name="TECO_MARKETPLACE", uniqueConstraints = {@UniqueConstraint(columnNames= {"CODE"})})
public class MarketPlace extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -8834213059142444939L;

    public static final String CACHE_NAME = "web_cache_market";

    public final static String MARKET_PLACE_ATTRIBUTE_DOMAIN_NAME = "MARKET_PLACE_DOMAIN_NAME";

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

    @Column(name = "IS_DEFAULT", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDefault;

    @Column(name = "THEME")
    private String theme;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MASTER_CATALOG_ID")
    private CatalogMaster masterCatalog;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MARKETPLACE_ID")
    private Set<Market> markets = new HashSet<Market>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MARKET_PLACE_ID")
    private Set<MarketPlaceAttribute> marketPlaceAttributes = new HashSet<MarketPlaceAttribute>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public MarketPlace() {
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

    public boolean isDefault() {
        return isDefault;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public CatalogMaster getMasterCatalog() {
        return masterCatalog;
    }

    public void setMasterCatalog(CatalogMaster masterCatalog) {
        this.masterCatalog = masterCatalog;
    }

    public Set<Market> getMarkets() {
        return markets;
    }

    public Market getDefaultMarket() {
        Market defaultMarket = null;
        Set<Market> markets = getMarkets();
        if (markets != null 
                && Hibernate.isInitialized(markets)) {
            for (Iterator<Market> iterator = markets.iterator(); iterator.hasNext();) {
                Market market = (Market) iterator.next();
                if (market.isDefault()) {
                    defaultMarket = market;
                }
            }
            if (defaultMarket == null) {
                Iterator<Market> iterator = markets.iterator();
                defaultMarket = (Market) iterator.next();
            }
        }
        return defaultMarket;
    }

    public Market getMarket(String marketCode) {
        Market marketToReturn = null;
        Set<Market> markets = getMarkets();
        if (markets != null 
                && Hibernate.isInitialized(markets)) {
            for (Iterator<Market> iterator = markets.iterator(); iterator.hasNext();) {
                Market market = (Market) iterator.next();
                if (market.getCode().equalsIgnoreCase(marketCode)) {
                    marketToReturn = market;
                }
            }
        }
        return marketToReturn;
    }

    public void setMarkets(Set<Market> markets) {
        this.markets = markets;
    }

    public Set<MarketPlaceAttribute> getMarketPlaceAttributes() {
        return marketPlaceAttributes;
    }

    public void setMarketPlaceAttributes(Set<MarketPlaceAttribute> marketPlaceAttributes) {
        this.marketPlaceAttributes = marketPlaceAttributes;
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

    public String getDomainName(String contextNameValue) {
        return getAttributeValueString(MARKET_PLACE_ATTRIBUTE_DOMAIN_NAME, contextNameValue);
    }

    private String getAttributeValueString(String attributeDefinitionCode, String contextNameValue) {
        if (marketPlaceAttributes != null 
                && Hibernate.isInitialized(marketPlaceAttributes)) {
            for (Iterator<MarketPlaceAttribute> iterator = marketPlaceAttributes.iterator(); iterator.hasNext();) {
                MarketPlaceAttribute marketPlaceAttribute = (MarketPlaceAttribute) iterator.next();
                AttributeDefinition attributeDefinition = marketPlaceAttribute.getAttributeDefinition();
                if (StringUtils.isNotEmpty(marketPlaceAttribute.getContext()) && marketPlaceAttribute.getContext().equals(contextNameValue)
                        && attributeDefinition.getCode().equals(attributeDefinitionCode)) {
                    return (String) marketPlaceAttribute.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
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
        MarketPlace other = (MarketPlace) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (dateCreate == null) {
            if (other.dateCreate != null)
                return false;
        } else if (!dateCreate.equals(other.dateCreate))
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
        return "MarketPlace [id=" + id + ", version=" + version + ", code=" + code + ", name=" + name + ", description=" + description + ", isDefault=" + isDefault + ", theme=" + theme
                + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}