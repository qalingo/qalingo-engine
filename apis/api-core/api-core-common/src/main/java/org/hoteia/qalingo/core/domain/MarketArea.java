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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "TECO_MARKET_AREA", uniqueConstraints = {@UniqueConstraint(columnNames= {"CODE"})})
public class MarketArea extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -6237479836764154416L;

    public static final String CACHE_NAME = "web_cache_market";

    public final static String MARKET_AREA_ATTRIBUTE_DEFAULT_CONTEXT = "DEFAULT_CONTEXT";
    
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

    @Column(name = "IS_ECOMMERCE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isEcommerce;

    @Column(name = "THEME")
    private String theme;

    @Column(name = "GEOLOC_COUNTRY_CODE")
    private String geolocCountryCode;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "VIRTUAL_CATALOG_ID")
    private CatalogVirtual catalog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKET_ID", insertable = true, updatable = true)
    private Market market;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFAULT_CURRENCY_ID", insertable = true, updatable = true)
    private CurrencyReferential defaultCurrency;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.CurrencyReferential.class)
    @JoinTable(name = "TECO_MARKET_AREA_CURRENCY_REL", joinColumns = @JoinColumn(name = "MARKET_AREA_ID"), inverseJoinColumns = @JoinColumn(name = "CURRENCY_ID"))
    private Set<CurrencyReferential> currencies = new HashSet<CurrencyReferential>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MARKET_AREA_ID")
    private Set<MarketAreaAttribute> marketAreaAttributes = new HashSet<MarketAreaAttribute>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFAULT_LOCALIZATION_ID", insertable = true, updatable = true)
    private Localization defaultLocalization;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.Localization.class)
    @JoinTable(name = "TECO_MARKET_AREA_LOCALIZATION_REL", joinColumns = @JoinColumn(name = "MARKET_AREA_ID"), inverseJoinColumns = @JoinColumn(name = "LOCALIZATION_ID"))
    private Set<Localization> localizations = new HashSet<Localization>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFAULT_RETAILER_ID", insertable = false, updatable = false)
    private Retailer defaultRetailer;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.Retailer.class)
    @JoinTable(name = "TECO_MARKET_AREA_RETAILER_REL", joinColumns = @JoinColumn(name = "MARKET_AREA_ID"), inverseJoinColumns = @JoinColumn(name = "RETAILER_ID"))
    private Set<Retailer> retailers = new HashSet<Retailer>();

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "MARKET_AREA_ID")
    private Set<WarehouseMarketAreaRel> warehouseMarketAreaRels = new HashSet<WarehouseMarketAreaRel>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.Tax.class)
    @JoinTable(name = "TECO_MARKET_AREA_TAX_REL", joinColumns = @JoinColumn(name = "MARKET_AREA_ID"), inverseJoinColumns = @JoinColumn(name = "TAX_ID"))
    private Set<Tax> taxes = new HashSet<Tax>();
    
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.AbstractPaymentGateway.class)
    @JoinTable(name = "TECO_MARKET_AREA_PAYMENT_GATEWAY_REL", joinColumns = @JoinColumn(name = "MARKET_AREA_ID"), inverseJoinColumns = @JoinColumn(name = "PAYMENT_GATEWAY_ID"))
    private Set<AbstractPaymentGateway> paymentGateways = new HashSet<AbstractPaymentGateway>();

    @Column(name = "LONGITUDE")
    private String longitude;

    @Column(name = "LATITUDE")
    private String latitude;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public MarketArea() {
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

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public boolean isEcommerce() {
        return isEcommerce;
    }

    public void setEcommerce(boolean isEcommerce) {
        this.isEcommerce = isEcommerce;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getGeolocCountryCode() {
        return geolocCountryCode;
    }
    
    public void setGeolocCountryCode(String geolocCountryCode) {
        this.geolocCountryCode = geolocCountryCode;
    }
    
    public CatalogVirtual getCatalog() {
        return catalog;
    }

    public void setCatalog(CatalogVirtual catalog) {
        this.catalog = catalog;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }
    
    public CurrencyReferential getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(CurrencyReferential defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public Set<CurrencyReferential> getCurrencies() {
        return currencies;
    }
    
    public CurrencyReferential getCurrency(String currencyCode) {
        CurrencyReferential currencyToReturn = null;
        if (currencies != null 
                && Hibernate.isInitialized(currencies)) {
            for (Iterator<CurrencyReferential> iterator = currencies.iterator(); iterator.hasNext();) {
                CurrencyReferential currency = (CurrencyReferential) iterator.next();
                if (currency.getCode().equalsIgnoreCase(currencyCode)) {
                    currencyToReturn = currency;
                }
            }
        }
        return currencyToReturn;
    }

    public void setCurrencies(Set<CurrencyReferential> currencies) {
        this.currencies = currencies;
    }

    public Set<MarketAreaAttribute> getMarketAreaAttributes() {
        return marketAreaAttributes;
    }

    public void setMarketAreaAttributes(Set<MarketAreaAttribute> marketAreaAttributes) {
        this.marketAreaAttributes = marketAreaAttributes;
    }
    
    public Localization getDefaultLocalization() {
        return defaultLocalization;
    }

    public void setDefaultLocalization(Localization defaultLocalization) {
        this.defaultLocalization = defaultLocalization;
    }

    public Set<Localization> getLocalizations() {
        return localizations;
    }

    public Localization getLocalization(String localeCode) {
        Localization localeToReturn = null;
        if (localizations != null 
                && Hibernate.isInitialized(localizations)) {
            for (Iterator<Localization> iterator = localizations.iterator(); iterator.hasNext();) {
                Localization localization = (Localization) iterator.next();
                if (localization.getCode().equalsIgnoreCase(localeCode)) {
                    localeToReturn = localization;
                }
            }
        }
        return localeToReturn;
    }

    public void setLocalizations(Set<Localization> localizations) {
        this.localizations = localizations;
    }

    public Retailer getDefaultRetailer() {
        return defaultRetailer;
    }
    
    public void setDefaultRetailer(Retailer defaultRetailer) {
        this.defaultRetailer = defaultRetailer;
    }

    public Set<Retailer> getRetailers() {
        return retailers;
    }

    public Retailer getRetailer(String retailerCode) {
        Retailer retailerToReturn = null;
        if (retailers != null 
                && Hibernate.isInitialized(retailers)) {
            for (Iterator<Retailer> iterator = retailers.iterator(); iterator.hasNext();) {
                Retailer retailer = (Retailer) iterator.next();
                if (retailer.getCode().equalsIgnoreCase(retailerCode)) {
                    retailerToReturn = retailer;
                }
            }
        }
        return retailerToReturn;
    }

    public void setRetailers(Set<Retailer> retailers) {
        this.retailers = retailers;
    }

    public Set<WarehouseMarketAreaRel> getWarehouseMarketAreaRels() {
        return warehouseMarketAreaRels;
    }
    
    public List<Warehouse> getWarehouses() {
        List<WarehouseMarketAreaRel> sortedCatalogVirtualCategoryVirtualRels = null;
        List<Warehouse> sortedWarehouses = null;
        if (warehouseMarketAreaRels != null 
                && Hibernate.isInitialized(warehouseMarketAreaRels)) {
            sortedCatalogVirtualCategoryVirtualRels = new LinkedList<WarehouseMarketAreaRel>(warehouseMarketAreaRels);
            Collections.sort(sortedCatalogVirtualCategoryVirtualRels, new Comparator<WarehouseMarketAreaRel>() {
                @Override
                public int compare(WarehouseMarketAreaRel o1, WarehouseMarketAreaRel o2) {
                    if (o1 != null && o1.getRanking() != null && o2 != null && o2.getRanking() != null) {
                        return o1.getRanking().compareTo(o2.getRanking());
                    }
                    return 0;
                }
            });
            if(sortedCatalogVirtualCategoryVirtualRels != null){
                sortedWarehouses = new LinkedList<Warehouse>();
                for (Iterator<WarehouseMarketAreaRel> iterator = sortedCatalogVirtualCategoryVirtualRels.iterator(); iterator.hasNext();) {
                    WarehouseMarketAreaRel warehouseMarketAreaRel = (WarehouseMarketAreaRel) iterator.next();
                    sortedWarehouses.add(warehouseMarketAreaRel.getWarehouse());
                }
            }
        }
        return sortedWarehouses;
    }
    
    public void setWarehouseMarketAreaRels(Set<WarehouseMarketAreaRel> warehouseMarketAreaRels) {
        this.warehouseMarketAreaRels = warehouseMarketAreaRels;
    }
    
    public Set<DeliveryMethod> getDeliveryMethods() {
        Set<DeliveryMethod> deliveryMethods = null;
        if(warehouseMarketAreaRels != null
                && Hibernate.isInitialized(warehouseMarketAreaRels)){
            deliveryMethods = new HashSet<DeliveryMethod>();
            for (Iterator<WarehouseMarketAreaRel> iteratorWarehouse = warehouseMarketAreaRels.iterator(); iteratorWarehouse.hasNext();) {
                WarehouseMarketAreaRel warehouseMarketArea = (WarehouseMarketAreaRel) iteratorWarehouse.next();
                if(warehouseMarketAreaRels != null
                        && Hibernate.isInitialized(warehouseMarketAreaRels)){
                    Warehouse warehouse = warehouseMarketArea.getPk().getWarehouse();
                    if(warehouse.getDeliveryMethods() != null
                            && Hibernate.isInitialized(warehouse.getDeliveryMethods())){
                        for (Iterator<DeliveryMethod> iteratorDeliveryMethod = warehouse.getDeliveryMethods().iterator(); iteratorDeliveryMethod.hasNext();) {
                            DeliveryMethod deliveryMethod = (DeliveryMethod) iteratorDeliveryMethod.next();
                            deliveryMethods.add(deliveryMethod);
                        }
                    }
                }
            }
        }
        return deliveryMethods;
    }

    public DeliveryMethod getDeliveryMethod(String deliveryMethodCode) {
        DeliveryMethod deliveryMethodToReturn = null;
        Set<DeliveryMethod> deliveryMethods = getDeliveryMethods();
        if (deliveryMethods != null 
                && Hibernate.isInitialized(deliveryMethods)) {
            for (Iterator<DeliveryMethod> iterator = deliveryMethods.iterator(); iterator.hasNext();) {
                DeliveryMethod deliveryMethod = (DeliveryMethod) iterator.next();
                if (deliveryMethod.getCode().equalsIgnoreCase(deliveryMethodCode)) {
                    deliveryMethodToReturn = deliveryMethod;
                }
            }
        }
        return deliveryMethodToReturn;
    }

    public Set<Tax> getTaxes() {
        return taxes;
    }
    
    public void setTaxes(Set<Tax> taxes) {
        this.taxes = taxes;
    }
    
    public Set<AbstractPaymentGateway> getPaymentGateways() {
        return paymentGateways;
    }
    
    public AbstractPaymentGateway getPaymentGateway(String paymentGatewayCode) {
        AbstractPaymentGateway paymentGatewayToReturn = null;
        if (paymentGateways != null 
                && Hibernate.isInitialized(paymentGateways)) {
            for (Iterator<AbstractPaymentGateway> iterator = paymentGateways.iterator(); iterator.hasNext();) {
                AbstractPaymentGateway paymentGateway = (AbstractPaymentGateway) iterator.next();
                if (paymentGateway.getCode().equalsIgnoreCase(paymentGatewayCode)) {
                    paymentGatewayToReturn = paymentGateway;
                }
            }
        }
        return paymentGatewayToReturn;
    }
    
    public void setPaymentGateways(Set<AbstractPaymentGateway> paymentGateways) {
        this.paymentGateways = paymentGateways;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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
        return (String) getAttributeValue(MarketAreaAttribute.MARKET_AREA_ATTRIBUTE_DOMAIN_NAME, contextNameValue);
    }

    public String getEmailFromAddress(String contextNameValue, String emailType) {
        String emailFromAddress = (String) getAttributeValue(MarketAreaAttribute.MARKET_AREA_ATTRIBUTE_EMAIL_FROM_ADDRESS, contextNameValue + "/" + emailType);
        if (StringUtils.isEmpty(emailFromAddress)) {
            emailFromAddress = (String) getAttributeValue(MarketAreaAttribute.MARKET_AREA_ATTRIBUTE_EMAIL_FROM_ADDRESS, MARKET_AREA_ATTRIBUTE_DEFAULT_CONTEXT);
        }
        return emailFromAddress;
    }

    public String getEmailFromName(String contextNameValue, String emailType) {
        return (String) getAttributeValue(MarketAreaAttribute.MARKET_AREA_ATTRIBUTE_EMAIL_FROM_NAME, contextNameValue + "/" + emailType);
    }

    public String getEmailToContact(String contextNameValue) {
        return (String) getAttributeValue(MarketAreaAttribute.MARKET_AREA_ATTRIBUTE_EMAIL_TO_CONTACT, contextNameValue);
    }

    public List<String> getShareOptions(String contextNameValue) {
        String value = (String) getAttributeValue(MarketAreaAttribute.MARKET_AREA_ATTRIBUTE_SHARE_OPTIONS, contextNameValue);
        if (StringUtils.isNotEmpty(value)) {
            if (value.contains(",")) {
                return Arrays.asList(value.split("\\s*,\\s*"));
            } else {
                return Arrays.asList(value);
            }
        }
        return null;
    }
    
    public boolean withSavedPaymentInformation() {
        boolean withSavedPaymentInformation = false;
        Boolean attributeValue = (Boolean) getAttributeValue(MarketAreaAttribute.MARKET_AREA_ATTRIBUTE_SAVE_PAYMENT_INFORMATION, MARKET_AREA_ATTRIBUTE_DEFAULT_CONTEXT);
        if(attributeValue != null){
            withSavedPaymentInformation = attributeValue.booleanValue();
        }
        return withSavedPaymentInformation;
    }

    public String getOrderConfirmationTemplate() {
        return (String) getAttributeValue(MarketAreaAttribute.MARKET_AREA_ATTRIBUTE_ORDER_CONFIRMATION_TEMPLATE, MARKET_AREA_ATTRIBUTE_DEFAULT_CONTEXT);
    }
    
    public String getShippingConfirmationTemplate() {
        return (String) getAttributeValue(MarketAreaAttribute.MARKET_AREA_ATTRIBUTE_SHIPPING_CONFIRMATION_TEMPLATE, MARKET_AREA_ATTRIBUTE_DEFAULT_CONTEXT);
    }
    
    public String getInvoiceTemplate() {
        return (String) getAttributeValue(MarketAreaAttribute.MARKET_AREA_ATTRIBUTE_INVOICE_TEMPLATE, MARKET_AREA_ATTRIBUTE_DEFAULT_CONTEXT);
    }
    
    protected Object getAttributeValue(String attributeDefinitionCode, String contextNameValue) {
        if (marketAreaAttributes != null 
                && Hibernate.isInitialized(marketAreaAttributes)) {
            for (Iterator<MarketAreaAttribute> iterator = marketAreaAttributes.iterator(); iterator.hasNext();) {
                MarketAreaAttribute marketAreaAttribute = (MarketAreaAttribute) iterator.next();
                AttributeDefinition attributeDefinition = marketAreaAttribute.getAttributeDefinition();
                if (StringUtils.isNotEmpty(marketAreaAttribute.getContext()) && marketAreaAttribute.getContext().equals(contextNameValue)
                        && attributeDefinition.getCode().equals(attributeDefinitionCode)) {
                    return (Object) marketAreaAttribute.getValue();
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
        MarketArea other = (MarketArea) obj;
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
        return "MarketArea [id=" + id + ", version=" + version + ", code=" + code + ", name=" + name + ", description=" + description + ", isDefault=" + isDefault + ", isEcommerce=" + isEcommerce
                + ", theme=" + theme + ", geolocCountryCode=" + geolocCountryCode + ", longitude=" + longitude + ", latitude=" + latitude + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate
                + "]";
    }

}