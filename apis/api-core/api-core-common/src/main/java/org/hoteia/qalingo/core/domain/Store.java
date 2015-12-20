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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.Constants;

@Entity
@Table(name = "TECO_STORE")
public class Store extends AbstractExtendEntity<Store, StoreAttribute> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 9020657992879779713L;

    public static final String CACHE_NAME = "web_cache_retailer";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "SCORING", nullable = false, columnDefinition = "default 1")
    private long scoring;
    
    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean active;
    
    @Column(name = "IS_PRIMARY", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean primary;
    
    @Column(name = "IS_B2C", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean b2c;
    
    @Column(name = "IS_B2B", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean b2b;
    
    @Column(name = "CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;
    
    @Column(name = "ADDRESS1")
    private String address1;

    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "ADDITIONAL_INFORMATION")
    private String addressAdditionalInformation;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE_CODE")
    private String stateCode;

    @Column(name = "AREA_CODE")
    private String areaCode;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "PHONE")
    private String phone;
    
    @Column(name = "FAX")
    private String fax;
    
    @Column(name = "WEBSITE")
    private String website;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.Retailer.class)
    @JoinColumn(name = "RETAILER_ID", insertable = true, updatable = true)
    private Retailer retailer;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.Warehouse.class)
    @JoinColumn(name = "WAREHOUSE_ID", insertable = true, updatable = true)
    private Warehouse warehouse;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.StoreAttribute.class)
    @JoinColumn(name = "STORE_ID")
    private Set<StoreAttribute> attributes = new HashSet<StoreAttribute>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.StoreBusinessHour.class)
    @JoinColumn(name = "STORE_ID")
    private Set<StoreBusinessHour> businessHours = new HashSet<StoreBusinessHour>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.StoreService.class)
    @JoinColumn(name = "STORE_ID")
    private Set<StoreService> services = new HashSet<StoreService>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.Asset.class)
    @JoinColumn(name = "STORE_ID")
    private Set<Asset> assets = new HashSet<Asset>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.StoreCustomerRate.class)
    @JoinColumn(name = "STORE_ID")
    private Set<StoreCustomerRate> customerRates = new HashSet<StoreCustomerRate>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.StoreCustomerComment.class)
    @JoinColumn(name = "STORE_ID")
    private Set<StoreCustomerComment> customerComments = new HashSet<StoreCustomerComment>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.StoreTagRel.class)
    @JoinColumn(name = "STORE_ID")
    private Set<StoreTagRel> tagRels = new HashSet<StoreTagRel>();
    
    @Column(name = "LONGITUDE")
    private String longitude;

    @Column(name = "LATITUDE")
    private String latitude;

    @Transient
    private Double distance;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public Store() {
        this.dateCreate = new Date();
        this.dateUpdate = new Date();
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

    public long getScoring() {
        return scoring;
    }

    public void setScoring(long scoring) {
        this.scoring = scoring;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isB2c() {
        return b2c;
    }

    public void setB2c(boolean b2c) {
        this.b2c = b2c;
    }

    public boolean isB2b() {
        return b2b;
    }

    public void setB2b(boolean b2b) {
        this.b2b = b2b;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }
    
    public boolean hasType(String value) {
        if(type != null){
            return type.contains(value);
        }
        return false;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddressAdditionalInformation() {
        return addressAdditionalInformation;
    }

    public void setAddressAdditionalInformation(String addressAdditionalInformation) {
        this.addressAdditionalInformation = addressAdditionalInformation;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Retailer getRetailer() {
        return retailer;
    }
    
    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
    }
    
    public Warehouse getWarehouse() {
        return warehouse;
    }
    
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
    
    public Set<StoreAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<StoreAttribute> attributes) {
        this.attributes = attributes;
    }

    public Set<StoreBusinessHour> getBusinessHours() {
        return businessHours;
    }
    
    public void setBusinessHours(Set<StoreBusinessHour> businessHours) {
        this.businessHours = businessHours;
    }
    
    public Set<StoreService> getServices() {
        return services;
    }
    
    public List<StoreService> getServices(String type) {
        List<StoreService> storeServices = null;
        if (services != null 
                && Hibernate.isInitialized(services)) {
            storeServices = new ArrayList<StoreService>();
            for (Iterator<StoreService> iterator = services.iterator(); iterator.hasNext();) {
                StoreService storeService = (StoreService) iterator.next();
                if (storeService != null && storeService.getType() != null
                        && storeService.getType().equals(type)) {
                    storeServices.add(storeService);
                }
            }
        }
        return storeServices;
    }
    
    public void setServices(Set<StoreService> services) {
        this.services = services;
    }
    
    public Set<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }

    public List<Asset> getAssetsIsGlobal() {
        List<Asset> assetsIsGlobal = null;
        if (assets != null 
                && Hibernate.isInitialized(assets)) {
            assetsIsGlobal = new ArrayList<Asset>();
            for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                Asset asset = (Asset) iterator.next();
                if (asset != null && asset.isGlobal()) {
                    assetsIsGlobal.add(asset);
                }
            }
        }
        return assetsIsGlobal;
    }

    public List<Asset> getAssetsByMarketArea() {
        List<Asset> assetsByMarketArea = null;
        if (assets != null 
                && Hibernate.isInitialized(assets)) {
            assetsByMarketArea = new ArrayList<Asset>();
            for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                Asset asset = (Asset) iterator.next();
                if (asset != null && !asset.isGlobal()) {
                    assetsByMarketArea.add(asset);
                }
            }
        }
        return assetsByMarketArea;
    }

    public Set<StoreCustomerRate> getCustomerRates() {
        return customerRates;
    }

    public void setCustomerRates(Set<StoreCustomerRate> customerRates) {
        this.customerRates = customerRates;
    }

    public Set<StoreCustomerComment> getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(Set<StoreCustomerComment> customerComments) {
        this.customerComments = customerComments;
    }
    
    public Set<StoreTagRel> getTagRels() {
        return tagRels;
    }
    
    public List<Tag> getTags() {
        List<Tag> tags = null;
        if (Hibernate.isInitialized(tagRels) && !tagRels.isEmpty()) {
            tags = new ArrayList<Tag>();
            for (Iterator<StoreTagRel> iterator = tagRels.iterator(); iterator.hasNext();) {
                StoreTagRel storeTagRel = (StoreTagRel) iterator.next();
                if(Hibernate.isInitialized(storeTagRel.getPk().getTag()) && storeTagRel.getPk().getTag() != null){
                    tags.add(storeTagRel.getStoreTag());
                }
            }
        }
        return tags;
    }
    
    public void setTagRels(Set<StoreTagRel> tagRels) {
        this.tagRels = tagRels;
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

    public Double getDistance() {
        return distance;
    }
    
    public void setDistance(Double distance) {
        this.distance = distance;
    }
    
    public Double getDistanceFromInKm(String fromLatitude, String fromLongitude) {
        double earthRadius = Constants.EARTH_RADIUS;
        double dLat = Math.toRadians(new Double(getLatitude()) - new Double(fromLatitude));
        double dLng = Math.toRadians(new Double(getLongitude()) - new Double(fromLongitude));
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(new Double(fromLatitude))) * Math.cos(Math.toRadians(new Double(getLatitude())));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;
        return dist;
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

    // Attributes

    public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
        AbstractAttribute attribute = getAttribute(attributeCode, marketAreaId, localizationCode);
        if (attribute != null) {
            return attribute.getValue();
        }
        return null;
    }

    public String getI18nName(String localizationCode) {
        String i18Name = (String) getValue(StoreAttribute.STORE_ATTRIBUTE_I18N_NAME, null, localizationCode);
        if(StringUtils.isNotEmpty(i18Name)){
            return i18Name;
        }
        return name;
    }
    
    public String getI18nDescription(String localizationCode) {
        String i18Description = (String) getValue(StoreAttribute.STORE_ATTRIBUTE_I18N_DESCRIPTION, null, localizationCode);
        if (StringUtils.isNotEmpty(i18Description)) {
            return i18Description;
        }
        return description;
    }
    
    public String getI18nCity(String localizationCode) {
        String i18City = (String) getValue(StoreAttribute.STORE_ATTRIBUTE_I18N_CITY_NAME, null, localizationCode);
        if(StringUtils.isNotEmpty(i18City)){
            return i18City;
        }
        return city;
    }
    
	public List<StoreBusinessHour> getStoreBusinessHours (){
		List<StoreBusinessHour> storeBusinessHours = null;
		 if (businessHours != null 
	                && Hibernate.isInitialized(businessHours)) {
			 storeBusinessHours = new ArrayList<StoreBusinessHour>();
	            for (Iterator<StoreBusinessHour> iterator = businessHours.iterator(); iterator.hasNext();) {
	            	StoreBusinessHour storeBusinessHour = (StoreBusinessHour) iterator.next();
	            	storeBusinessHours.add(storeBusinessHour);
	            }
	       }
		return storeBusinessHours;
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
    public boolean equals(Object sourceObj) {
        Object obj = deproxy(sourceObj);
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Store other = (Store) obj;
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
        return "Store [id=" + id + ", version=" + version + ", code=" + code + ", type=" + type + ", name=" + name + ", address1=" + address1 + ", address2=" + address2
                + ", addressAdditionalInformation=" + addressAdditionalInformation + ", postalCode=" + postalCode + ", city=" + city + ", stateCode=" + stateCode + ", areaCode=" + areaCode
                + ", countryCode=" + countryCode + ", longitude=" + longitude + ", latitude=" + latitude + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}