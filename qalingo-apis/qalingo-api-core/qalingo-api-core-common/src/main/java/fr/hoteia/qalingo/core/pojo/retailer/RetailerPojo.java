package fr.hoteia.qalingo.core.pojo.retailer;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import fr.hoteia.qalingo.core.pojo.AssetPojo;
import fr.hoteia.qalingo.core.pojo.store.StorePojo;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.asList;

public class RetailerPojo {

    private Long id;
    private int version;
    private String name;
    private String description;
    private String logo;
    private boolean isDefault;
    private boolean isOfficialRetailer;
    private boolean isBrand;
    private boolean isEcommerce;
    private String code;
    private int qualityOfService;
    private int priceScore;
    private int ratioQualityPrice;
    private Date dateCreate;
    private Date dateUpdate;

    private Collection<RetailerLinkPojo> links = Collections.emptyList();
    private Collection<RetailerAddressPojo> addresses = Collections.emptyList();
    private Collection<StorePojo> stores = Collections.emptyList();
    private Collection<AssetPojo> assetsIsGlobal = Collections.emptyList();
    private Collection<AssetPojo> assetsByMarketArea = Collections.emptyList();
    private Collection<RetailerAttributePojo> retailerGlobalAttributes = Collections.emptyList();
    private Collection<RetailerAttributePojo> retailerMarketAreaAttributes = Collections.emptyList();
    private Collection<RetailerCustomerRatePojo> customerRates = Collections.emptyList();
    private Collection<RetailerCustomerCommentPojo> customerComments = Collections.emptyList();
    private Collection<RetailerTagPojo> retailerTags = Collections.emptyList();

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

    public Collection<RetailerLinkPojo> getLinks() {
        return links;
    }

    public void setLinks(Collection<RetailerLinkPojo> links) {
        this.links = asList(links);
    }

    public Collection<RetailerAddressPojo> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<RetailerAddressPojo> addresses) {
        this.addresses = asList(addresses);
    }

    public Collection<StorePojo> getStores() {
        return stores;
    }

    public void setStores(Collection<StorePojo> stores) {
        this.stores = asList(stores);
    }

    public Collection<AssetPojo> getAssetsIsGlobal() {
        return assetsIsGlobal;
    }

    public void setAssetsIsGlobal(Collection<AssetPojo> assetsIsGlobal) {
        this.assetsIsGlobal = asList(assetsIsGlobal);
    }

    public Collection<AssetPojo> getAssetsByMarketArea() {
        return assetsByMarketArea;
    }

    public void setAssetsByMarketArea(Collection<AssetPojo> assetsByMarketArea) {
        this.assetsByMarketArea = asList(assetsByMarketArea);
    }

    public Collection<RetailerAttributePojo> getRetailerGlobalAttributes() {
        return retailerGlobalAttributes;
    }

    public void setRetailerGlobalAttributes(Collection<RetailerAttributePojo> retailerGlobalAttributes) {
        this.retailerGlobalAttributes = asList(retailerGlobalAttributes);
    }

    public Collection<RetailerAttributePojo> getRetailerMarketAreaAttributes() {
        return retailerMarketAreaAttributes;
    }

    public void setRetailerMarketAreaAttributes(Collection<RetailerAttributePojo> retailerMarketAreaAttributes) {
        this.retailerMarketAreaAttributes = asList(retailerMarketAreaAttributes);
    }

    public Collection<RetailerCustomerRatePojo> getCustomerRates() {
        return customerRates;
    }

    public void setCustomerRates(Collection<RetailerCustomerRatePojo> customerRates) {
        this.customerRates = asList(customerRates);
    }

    public Collection<RetailerCustomerCommentPojo> getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(Collection<RetailerCustomerCommentPojo> customerComments) {
        this.customerComments = asList(customerComments);
    }

    public Collection<RetailerTagPojo> getRetailerTags() {
        return retailerTags;
    }

    public void setRetailerTags(Collection<RetailerTagPojo> retailerTags) {
        this.retailerTags = asList(retailerTags);
    }
}
