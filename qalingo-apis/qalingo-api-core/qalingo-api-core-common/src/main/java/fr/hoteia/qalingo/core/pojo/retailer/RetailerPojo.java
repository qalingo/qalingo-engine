package fr.hoteia.qalingo.core.pojo.retailer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import fr.hoteia.qalingo.core.pojo.AssetPojo;
import fr.hoteia.qalingo.core.pojo.store.StorePojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;

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

    private Collection<RetailerLinkPojo> links = new ArrayList<RetailerLinkPojo>();
    private Collection<RetailerAddressPojo> addresses = new ArrayList<RetailerAddressPojo>();
    private Collection<StorePojo> stores = new ArrayList<StorePojo>();
    private Collection<AssetPojo> assetsIsGlobal = new ArrayList<AssetPojo>();
    private Collection<AssetPojo> assetsByMarketArea = new ArrayList<AssetPojo>();
    private Collection<RetailerAttributePojo> retailerGlobalAttributes = new ArrayList<RetailerAttributePojo>();
    private Collection<RetailerAttributePojo> retailerMarketAreaAttributes = new ArrayList<RetailerAttributePojo>();
    private Collection<RetailerCustomerRatePojo> customerRates = new ArrayList<RetailerCustomerRatePojo>();
    private Collection<RetailerCustomerCommentPojo> customerComments = new ArrayList<RetailerCustomerCommentPojo>();
    private Collection<RetailerTagPojo> retailerTags = new ArrayList<RetailerTagPojo>();

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
        this.links = PojoUtil.asList(links);
    }

    public Collection<RetailerAddressPojo> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<RetailerAddressPojo> addresses) {
        this.addresses = PojoUtil.asList(addresses);
    }

    public Collection<StorePojo> getStores() {
        return stores;
    }

    public void setStores(Collection<StorePojo> stores) {
        this.stores = PojoUtil.asList(stores);
    }

    public Collection<AssetPojo> getAssetsIsGlobal() {
        return assetsIsGlobal;
    }

    public void setAssetsIsGlobal(Collection<AssetPojo> assetsIsGlobal) {
        this.assetsIsGlobal = PojoUtil.asList(assetsIsGlobal);
    }

    public Collection<AssetPojo> getAssetsByMarketArea() {
        return assetsByMarketArea;
    }

    public void setAssetsByMarketArea(Collection<AssetPojo> assetsByMarketArea) {
        this.assetsByMarketArea = PojoUtil.asList(assetsByMarketArea);
    }

    public Collection<RetailerAttributePojo> getRetailerGlobalAttributes() {
        return retailerGlobalAttributes;
    }

    public void setRetailerGlobalAttributes(Collection<RetailerAttributePojo> retailerGlobalAttributes) {
        this.retailerGlobalAttributes = PojoUtil.asList(retailerGlobalAttributes);
    }

    public Collection<RetailerAttributePojo> getRetailerMarketAreaAttributes() {
        return retailerMarketAreaAttributes;
    }

    public void setRetailerMarketAreaAttributes(Collection<RetailerAttributePojo> retailerMarketAreaAttributes) {
        this.retailerMarketAreaAttributes = PojoUtil.asList(retailerMarketAreaAttributes);
    }

    public Collection<RetailerCustomerRatePojo> getCustomerRates() {
        return customerRates;
    }

    public void setCustomerRates(Collection<RetailerCustomerRatePojo> customerRates) {
        this.customerRates = PojoUtil.asList(customerRates);
    }

    public Collection<RetailerCustomerCommentPojo> getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(Collection<RetailerCustomerCommentPojo> customerComments) {
        this.customerComments = PojoUtil.asList(customerComments);
    }

    public Collection<RetailerTagPojo> getRetailerTags() {
        return retailerTags;
    }

    public void setRetailerTags(Collection<RetailerTagPojo> retailerTags) {
        this.retailerTags = PojoUtil.asList(retailerTags);
    }
}
