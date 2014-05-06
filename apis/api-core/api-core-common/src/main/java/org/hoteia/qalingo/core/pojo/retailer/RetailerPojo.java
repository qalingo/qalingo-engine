/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.retailer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hoteia.qalingo.core.pojo.AssetPojo;
import org.hoteia.qalingo.core.pojo.store.StorePojo;

public class RetailerPojo {

    private Long id;
    private int version;
    private String name;
    private String description;
    private String logo;
    private boolean isDefault;
    private boolean isSelected = false;
    private boolean isOfficialRetailer;
    private boolean isBrand;
    private boolean isEcommerce;
    private String code;
    private int qualityOfService;
    private int priceScore;
    private int ratioQualityPrice;
    private Date dateCreate;
    private Date dateUpdate;

    private List<RetailerLinkPojo> links = new ArrayList<RetailerLinkPojo>();
    private List<RetailerAddressPojo> addresses = new ArrayList<RetailerAddressPojo>();
    private List<StorePojo> stores = new ArrayList<StorePojo>();
    private List<AssetPojo> assetsIsGlobal = new ArrayList<AssetPojo>();
    private List<AssetPojo> assetsByMarketArea = new ArrayList<AssetPojo>();
    private List<RetailerAttributePojo> retailerGlobalAttributes = new ArrayList<RetailerAttributePojo>();
    private List<RetailerAttributePojo> retailerMarketAreaAttributes = new ArrayList<RetailerAttributePojo>();
    private List<RetailerCustomerRatePojo> customerRates = new ArrayList<RetailerCustomerRatePojo>();
    private List<RetailerCustomerCommentPojo> customerComments = new ArrayList<RetailerCustomerCommentPojo>();
    private List<RetailerTagPojo> retailerTags = new ArrayList<RetailerTagPojo>();

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    
    public boolean isOfficialRetailer() {
        return isOfficialRetailer;
    }

    public void setOfficialRetailer(boolean officialRetailer) {
        isOfficialRetailer = officialRetailer;
    }

    public boolean isBrand() {
        return isBrand;
    }

    public void setBrand(boolean brand) {
        isBrand = brand;
    }

    public boolean isEcommerce() {
        return isEcommerce;
    }

    public void setEcommerce(boolean ecommerce) {
        isEcommerce = ecommerce;
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

    public List<RetailerLinkPojo> getLinks() {
        return links;
    }

    public void setLinks(List<RetailerLinkPojo> links) {
        this.links = links;
    }

    public List<RetailerAddressPojo> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<RetailerAddressPojo> addresses) {
        this.addresses = addresses;
    }

    public List<StorePojo> getStores() {
        return stores;
    }

    public void setStores(List<StorePojo> stores) {
        this.stores = stores;
    }

    public List<AssetPojo> getAssetsIsGlobal() {
        return assetsIsGlobal;
    }

    public void setAssetsIsGlobal(List<AssetPojo> assetsIsGlobal) {
        this.assetsIsGlobal = assetsIsGlobal;
    }

    public List<AssetPojo> getAssetsByMarketArea() {
        return assetsByMarketArea;
    }

    public void setAssetsByMarketArea(List<AssetPojo> assetsByMarketArea) {
        this.assetsByMarketArea = assetsByMarketArea;
    }

    public List<RetailerAttributePojo> getRetailerGlobalAttributes() {
        return retailerGlobalAttributes;
    }

    public void setRetailerGlobalAttributes(List<RetailerAttributePojo> retailerGlobalAttributes) {
        this.retailerGlobalAttributes = retailerGlobalAttributes;
    }

    public List<RetailerAttributePojo> getRetailerMarketAreaAttributes() {
        return retailerMarketAreaAttributes;
    }

    public void setRetailerMarketAreaAttributes(List<RetailerAttributePojo> retailerMarketAreaAttributes) {
        this.retailerMarketAreaAttributes = retailerMarketAreaAttributes;
    }

    public List<RetailerCustomerRatePojo> getCustomerRates() {
        return customerRates;
    }

    public void setCustomerRates(List<RetailerCustomerRatePojo> customerRates) {
        this.customerRates = customerRates;
    }

    public List<RetailerCustomerCommentPojo> getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(List<RetailerCustomerCommentPojo> customerComments) {
        this.customerComments = customerComments;
    }

    public List<RetailerTagPojo> getRetailerTags() {
        return retailerTags;
    }

    public void setRetailerTags(List<RetailerTagPojo> retailerTags) {
        this.retailerTags = retailerTags;
    }
    
}