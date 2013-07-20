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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name="TECO_RETAILER")
@FilterDefs(
		value = {
			@FilterDef(name="filterRetailerAttributeIsGlobal"),
			@FilterDef(name="filterRetailerAttributeByMarketArea", parameters= { @ParamDef(name="marketAreaId", type="long") }),
			@FilterDef(name="filterRetailerAssetIsGlobal"),
			@FilterDef(name="filterRetailerAssetByMarketArea", parameters= { @ParamDef(name="marketAreaId", type="long") })
		})
public class Retailer implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 7735245064467350070L;

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

	@Column(name="IS_DEFAULT", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isDefault;
	
	@Column(name="IS_OFFICIAL_RETAILER", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isOfficialRetailer;

	@Column(name="IS_BRAND", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isBrand;

	@Column(name="IS_ECOMMERCE", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isEcommerce;
	
	@Column(name="CODE")
	private String code;

	@Column(name="QUALITY_OF_SERVICE", nullable=false, columnDefinition="tinyint(1) default 0")
	private int qualityOfService;
	
	@Column(name="PRICE_SCORE", nullable=false, columnDefinition="tinyint(1) default 0")
	private int priceScore;

	@Column(name="RATIO_QUALITY_PRICE", nullable=false, columnDefinition="tinyint(1) default 0")
	private int ratioQualityPrice;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="RETAILER_ID")
	private Set<RetailerAddress> addresses = new HashSet<RetailerAddress>(); 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="RETAILER_ID")
	private Set<Store> stores = new HashSet<Store>(); 

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_MARKETING_ID")
	@Filter(name="filterRetailerAssetIsGlobal", condition="IS_GLOBAL = '1' AND SCOPE = 'RETAILER'")
	@OrderBy(clause = "ordering asc")
	private Set<Asset> assetsIsGlobal = new HashSet<Asset>(); 

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_MARKETING_ID")
	@Filter(name="filterRetailerAssetByMarketArea", condition="IS_GLOBAL = '0' AND MARKET_AREA_ID = :marketAreaId AND SCOPE = 'RETAILER'")
	@OrderBy(clause = "ordering asc")
	private Set<Asset> assetsByMarketArea = new HashSet<Asset>();  
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_MARKETTING_ID")
	@Filter(name="filterRetailerAttributeIsGlobal", condition="IS_GLOBAL = '1'")
	private Set<RetailerAttribute> retailerGlobalAttributes = new HashSet<RetailerAttribute>(); 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_MARKETTING_ID")
	@Filter(name="filterRetailerAttributeByMarketArea", condition="MARKET_AREA_ID = :marketAreaId")
	private Set<RetailerAttribute> retailerMarketAreaAttributes = new HashSet<RetailerAttribute>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public Retailer() {
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

	public boolean isDefault() {
		return isDefault;
	}
	
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public boolean isOfficialRetailer() {
		return isOfficialRetailer;
	}

	public void setOfficialRetailer(boolean isOfficialRetailer) {
		this.isOfficialRetailer = isOfficialRetailer;
	}

	public boolean isBrand() {
		return isBrand;
	}

	public void setBrand(boolean isBrand) {
		this.isBrand = isBrand;
	}
	
	public boolean isEcommerce() {
		return isEcommerce;
	}

	public void setEcommerce(boolean isEcommerce) {
		this.isEcommerce = isEcommerce;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public int getQualityOfService() {
    	return qualityOfService;
    }

	public void setQualityOfService(int qualityOfService) {
    	this.qualityOfService = qualityOfService;
    }

	public int getPriceScore() {
    	return priceScore;
    }

	public void setPriceScore(int priceScore) {
    	this.priceScore = priceScore;
    }

	public int getRatioQualityPrice() {
    	return ratioQualityPrice;
    }

	public void setRatioQualityPrice(int ratioQualityPrice) {
    	this.ratioQualityPrice = ratioQualityPrice;
    }

	public Set<RetailerAddress> getAddresses() {
	    return addresses;
    }
	
	public void setAddresses(Set<RetailerAddress> addresses) {
	    this.addresses = addresses;
    }
	
	public Set<Store> getStores() {
		return stores;
	}
	
	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}
	
	public Set<Asset> getAssetsIsGlobal() {
    	return assetsIsGlobal;
    }

	public void setAssetsIsGlobal(Set<Asset> assetsIsGlobal) {
    	this.assetsIsGlobal = assetsIsGlobal;
    }

	public Set<Asset> getAssetsByMarketArea() {
    	return assetsByMarketArea;
    }

	public void setAssetsByMarketArea(Set<Asset> assetsByMarketArea) {
    	this.assetsByMarketArea = assetsByMarketArea;
    }

	public Set<RetailerAttribute> getRetailerGlobalAttributes() {
    	return retailerGlobalAttributes;
    }

	public void setRetailerGlobalAttributes(Set<RetailerAttribute> retailerGlobalAttributes) {
    	this.retailerGlobalAttributes = retailerGlobalAttributes;
    }

	public Set<RetailerAttribute> getRetailerMarketAreaAttributes() {
    	return retailerMarketAreaAttributes;
    }

	public void setRetailerMarketAreaAttributes(Set<RetailerAttribute> retailerMarketAreaAttributes) {
    	this.retailerMarketAreaAttributes = retailerMarketAreaAttributes;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((dateCreate == null) ? 0 : dateCreate.hashCode());
		result = prime * result
				+ ((dateUpdate == null) ? 0 : dateUpdate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isBrand ? 1231 : 1237);
		result = prime * result + (isDefault ? 1231 : 1237);
		result = prime * result + (isOfficialRetailer ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + version;
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
		Retailer other = (Retailer) obj;
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
		if (dateUpdate == null) {
			if (other.dateUpdate != null)
				return false;
		} else if (!dateUpdate.equals(other.dateUpdate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isBrand != other.isBrand)
			return false;
		if (isDefault != other.isDefault)
			return false;
		if (isOfficialRetailer != other.isOfficialRetailer)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Retailer [id=" + id + ", version=" + version + ", name=" + name
				+ ", description=" + description + ", isDefault=" + isDefault
				+ ", isOfficialRetailer=" + isOfficialRetailer + ", isBrand="
				+ isBrand + ", code=" + code + ", dateCreate=" + dateCreate
				+ ", dateUpdate=" + dateUpdate + "]";
	}
	
}
