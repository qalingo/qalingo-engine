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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.enumtype.AssetType;

@Entity
@Table(name = "TECO_STORE", uniqueConstraints = { @UniqueConstraint(columnNames = { "CODE" }) })
public class Store extends AbstractEntity {

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

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "NAME")
    private String name;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RETAILER_ID", insertable = true, updatable = true)
    private Retailer retailer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "STORE_ID")
    private Set<StoreAttribute> attributes = new HashSet<StoreAttribute>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "STORE_ID")
    private Set<StoreBusinessHour> businessHours = new HashSet<StoreBusinessHour>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "STORE_ID")
    private Set<Asset> assets = new HashSet<Asset>();

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

    public Store() {
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

    public String getType() {
        return type;
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

    public Retailer getRetailer() {
        return retailer;
    }
    
    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
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

    // Attributes

    public StoreAttribute getStoreAttribute(String attributeCode) {
        return getStoreAttribute(attributeCode, null, null);
    }

    public StoreAttribute getStoreAttribute(String attributeCode, String localizationCode) {
        return getStoreAttribute(attributeCode, null, localizationCode);
    }

    public StoreAttribute getStoreAttribute(String attributeCode, Long marketAreaId) {
        return getStoreAttribute(attributeCode, marketAreaId, null);
    }

    public StoreAttribute getStoreAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
        StoreAttribute storeAttributeToReturn = null;
        if (attributes != null
                && Hibernate.isInitialized(attributes)) {
            List<StoreAttribute> storeAttributesFilter = new ArrayList<StoreAttribute>();
            for (Iterator<StoreAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                StoreAttribute storeAttribute = (StoreAttribute) iterator.next();
                AttributeDefinition attributeDefinition = storeAttribute.getAttributeDefinition();
                if (attributeDefinition != null && attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
                    storeAttributesFilter.add(storeAttribute);
                }
            }
            if (marketAreaId != null) {
                for (Iterator<StoreAttribute> iterator = storeAttributesFilter.iterator(); iterator.hasNext();) {
                    StoreAttribute storeAttribute = (StoreAttribute) iterator.next();
                    AttributeDefinition attributeDefinition = storeAttribute.getAttributeDefinition();
                    if (BooleanUtils.negate(attributeDefinition.isGlobal())) {
                        if (storeAttribute.getMarketAreaId() != null && BooleanUtils.negate(storeAttribute.getMarketAreaId().equals(marketAreaId))) {
                            iterator.remove();
                        }
                    }
                }
                if (storeAttributesFilter.size() == 0) {
                    // TODO : throw error ?
                }
            }
            if (StringUtils.isNotEmpty(localizationCode)) {
                for (Iterator<StoreAttribute> iterator = storeAttributesFilter.iterator(); iterator.hasNext();) {
                    StoreAttribute storeAttribute = (StoreAttribute) iterator.next();
                    AttributeDefinition attributeDefinition = storeAttribute.getAttributeDefinition();
                    if (BooleanUtils.negate(attributeDefinition.isGlobal())) {
                        String attributeLocalizationCode = storeAttribute.getLocalizationCode();
                        if (StringUtils.isNotEmpty(attributeLocalizationCode) && BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))) {
                            iterator.remove();
                        }
                    }
                }
                if (storeAttributesFilter.size() == 0) {
                    // TODO : throw error ?

                    for (Iterator<StoreAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                        StoreAttribute storeAttribute = (StoreAttribute) iterator.next();

                        // TODO : get a default locale code from setting
                        // database ?

                        if (storeAttribute.getLocalizationCode().equals(Constants.DEFAULT_LOCALE_CODE)) {
                            storeAttributeToReturn = storeAttribute;
                        }
                    }
                }
            }
            if (storeAttributesFilter.size() == 1) {
                storeAttributeToReturn = storeAttributesFilter.get(0);
            } else {
                // TODO : throw error ?
            }
        }
        return storeAttributeToReturn;
    }

    public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
        StoreAttribute storeAttribute = getStoreAttribute(attributeCode, marketAreaId, localizationCode);
        if (storeAttribute != null) {
            return storeAttribute.getValue();
        }
        return null;
    }

    public String getI18nCity(Localization localization) {
        return (String) getValue(StoreAttribute.STORE_ATTRIBUTE_I18N_CITY, null, localization.getCode());
    }
    
    // ASSET
    
 	public Asset getDefaultPackshotImage(String size) {
 		Asset defaultStoreImage = null;
 		List<Asset> assetsIsGlobal = getAssetsIsGlobal();
 		if(assetsIsGlobal != null
 		       && StringUtils.isNotEmpty(size)){
 			for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
 				Asset storeAsset = (Asset) iterator.next();
 				if(AssetType.PACKSHOT.equals(storeAsset.getType())
 						&& size.equals(storeAsset.getSize().name())
 						&& storeAsset.isDefault()){
 					defaultStoreImage = storeAsset;
 				}
 			}
 			for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
 				Asset storeImage = (Asset) iterator.next();
 				if(AssetType.PACKSHOT.equals(storeImage.getType())
 						&& size.equals(storeImage.getSize().name())){
 					defaultStoreImage = storeImage;
 				}
 			}
 		}
 		return defaultStoreImage;
 	}
 	
 	public Asset getDefaultBackgroundImage() {
 		Asset defaultStoreImage = null;
 		List<Asset> assetsIsGlobal = getAssetsIsGlobal();
 		if(assetsIsGlobal != null){
 			for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
 				Asset storeImage = (Asset) iterator.next();
 				if(AssetType.BACKGROUND.equals(storeImage.getType())
 						&& storeImage.isDefault()){
 					defaultStoreImage = storeImage;
 				}
 			}
 			for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
 				Asset storeImage = (Asset) iterator.next();
 				if(AssetType.BACKGROUND.equals(storeImage.getType())){
 					defaultStoreImage = storeImage;
 				}
 			}
 		}
 		return defaultStoreImage;
 	}
 	
 	public Asset getDefaultIconImage() {
 		Asset defaultStoreImage = null;
        List<Asset> assetsIsGlobal = getAssetsIsGlobal();
        if(assetsIsGlobal != null){
 			for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
 				Asset storeImage = (Asset) iterator.next();
 				if(AssetType.ICON.equals(storeImage.getType())
 						&& storeImage.isDefault()){
 					defaultStoreImage = storeImage;
 				}
 			}
 			for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
 				Asset storeImage = (Asset) iterator.next();
 				if(AssetType.ICON.equals(storeImage.getType())){
 					defaultStoreImage = storeImage;
 				}
 			}
 		}
 		return defaultStoreImage;
 	}
 	
	public List<Asset> getSlideShows() {
 		List<Asset> slideShows = new ArrayList<Asset>();
        List<Asset> assetsIsGlobal = getAssetsIsGlobal();
        if(assetsIsGlobal != null){
 			for (Iterator<Asset> iterator = assetsIsGlobal.iterator(); iterator.hasNext();) {
 				Asset storeImage = (Asset) iterator.next();
 				if(AssetType.SLIDESHOW.equals(storeImage.getType())){
 					slideShows.add(storeImage);
 				}
 			}
 		}
 		return slideShows;
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
    public boolean equals(Object obj) {
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