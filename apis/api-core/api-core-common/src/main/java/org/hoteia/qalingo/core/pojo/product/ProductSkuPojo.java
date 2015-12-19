/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.pojo.AssetPojo;
import org.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import org.hoteia.qalingo.core.util.CoreUtil;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class ProductSkuPojo {

    private Long id;
    private int version;
    private String code;
    private String ean;

    private String name;
    private String description;
    
    protected String i18nName;
    protected String i18nDescription;
    protected String i18nShortDescription;
    
    private Integer width;
    private Integer height;
    private Integer length;
    private Integer weight;
    private String defaultPackshotImage;
    private String defaultBackgroundImage;
    private String defaultIconImage;
    private String priceWithStandardCurrencySign;
    
    private boolean isDefault;
    
    private boolean enabledB2B;
    private boolean enabledB2C;

    private boolean salableB2B;
    private boolean salableB2C;

    private int ranking;
    
    private Date dateCreate;
    private Date dateUpdate;
    
    private ProductBrandPojo productBrand;
    private ProductMarketingPojo productMarketing;

    private List<ProductSkuAttributePojo> attributes = new ArrayList<ProductSkuAttributePojo>();

    private List<ProductSkuOptionDefinitionPojo> optionDefinitions = new ArrayList<ProductSkuOptionDefinitionPojo>();

    private List<AssetPojo> assets = new ArrayList<AssetPojo>();
    private List<ProductSkuPricePojo> prices = new ArrayList<ProductSkuPricePojo>();
    private List<ProductSkuStockPojo> stocks = new ArrayList<ProductSkuStockPojo>();
    private List<RetailerPojo> retailers = new ArrayList<RetailerPojo>();

    private String detailsUrl;

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

    public String getEan() {
        return ean;
    }
    
    public void setEan(String ean) {
        this.ean = ean;
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
    
    public String getI18nName() {
        if(StringUtils.isNotEmpty(i18nName)){
            return i18nName;
        }
        return name;
    }
    
    public void setI18nName(String i18nName) {
        this.i18nName = i18nName;
    }
    
    public String getI18nTruncatedName() {
        int size = Constants.POJO_NAME_MAX_LENGTH;
        if (StringUtils.isNotEmpty(getI18nName())){
            if(getI18nName().length() >= size){
                return CoreUtil.handleTruncatedString(getI18nName(), size);
            } else {
                return getI18nName();
            }
        }
        return "";
    }
    
    public String getI18nDescription() {
        if(StringUtils.isNotEmpty(i18nDescription)){
            return i18nDescription;
        }
        return description;
    }
    
    public void setI18nDescription(String i18nDescription) {
        this.i18nDescription = i18nDescription;
    }
    
    public String getI18nShortDescription() {
        return i18nShortDescription;
    }
    
    public void setI18nShortDescription(String i18nShortDescription) {
        this.i18nShortDescription = i18nShortDescription;
    }
    
    public String getI18nTruncatedDescription() {
        int size = Constants.POJO_DESCRIPTION_MAX_LENGTH;
        if(StringUtils.isNotEmpty(getI18nShortDescription())){
            if(getI18nShortDescription().length() >= size){
                return CoreUtil.handleTruncatedString(getI18nShortDescription(), size);
            } else {
                return getI18nShortDescription();
            }
        } else if (StringUtils.isNotEmpty(getI18nDescription())){
            if(getI18nDescription().length() >= size){
                return CoreUtil.handleTruncatedString(getI18nDescription(), size);
            } else {
                return getI18nDescription();
            }
        }
        return "";
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getDefaultPackshotImage() {
        return defaultPackshotImage;
    }

    public void setDefaultPackshotImage(String defaultPackshotImage) {
        this.defaultPackshotImage = defaultPackshotImage;
    }

    public String getDefaultBackgroundImage() {
        return defaultBackgroundImage;
    }

    public void setDefaultBackgroundImage(String defaultBackgroundImage) {
        this.defaultBackgroundImage = defaultBackgroundImage;
    }

    public String getDefaultIconImage() {
        return defaultIconImage;
    }

    public void setDefaultIconImage(String defaultIconImage) {
        this.defaultIconImage = defaultIconImage;
    }

    public String getPriceWithStandardCurrencySign() {
        return priceWithStandardCurrencySign;
    }
    
    public void setPriceWithStandardCurrencySign(String priceWithStandardCurrencySign) {
        this.priceWithStandardCurrencySign = priceWithStandardCurrencySign;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isEnabledB2B() {
        return enabledB2B;
    }

    public void setEnabledB2B(boolean enabledB2B) {
        this.enabledB2B = enabledB2B;
    }
    
    public boolean isEnabledB2C() {
        return enabledB2C;
    }

    public void setEnabledB2C(boolean enabledB2C) {
        this.enabledB2C = enabledB2C;
    }
    
    public boolean isSalableB2B() {
        return salableB2B;
    }

    public void setSalableB2B(boolean salableB2B) {
        this.salableB2B = salableB2B;
    }
    
    public boolean isSalableB2C() {
        return salableB2C;
    }

    public void setSalableB2C(boolean salableB2C) {
        this.salableB2C = salableB2C;
    }

    public int getRanking() {
        return ranking;
    }
    
    public void setRanking(int ranking) {
        this.ranking = ranking;
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

    public ProductBrandPojo getProductBrand(){
        if(productBrand == null && productMarketing != null){
            return productMarketing.getProductBrand();
        }
        return productBrand;
    }
    
    public void setProductBrand(ProductBrandPojo productBrand) {
        this.productBrand = productBrand;
    }
    
    @JsonBackReference
    public ProductMarketingPojo getProductMarketing() {
        return productMarketing;
    }

    public void setProductMarketing(ProductMarketingPojo productMarketing) {
        this.productMarketing = productMarketing;
    }

    public List<ProductSkuAttributePojo> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductSkuAttributePojo> attributes) {
        this.attributes = attributes;
    }

    public List<ProductSkuOptionDefinitionPojo> getOptionDefinitions() {
        return optionDefinitions;
    }
    
    public void setOptionDefinitions(List<ProductSkuOptionDefinitionPojo> optionDefinitions) {
        this.optionDefinitions = optionDefinitions;
    }
    
    public List<AssetPojo> getAssets() {
        return assets;
    }

    public String getAssetPath(String type) {
        for (Iterator<AssetPojo> iterator = assets.iterator(); iterator.hasNext();) {
            AssetPojo assetViewBean = (AssetPojo) iterator.next();
            if (assetViewBean.getType().equals(type)) {
                return assetViewBean.getPath();
            }
        }
        return null;
    }

    public String getAssetAbsoluteWebPath(String type) {
        for (Iterator<AssetPojo> iterator = assets.iterator(); iterator.hasNext();) {
            AssetPojo assetViewBean = (AssetPojo) iterator.next();
            if (assetViewBean.getType().equals(type)) {
                return assetViewBean.getAbsoluteWebPath();
            }
        }
        return null;
    }

    public String getAssetRelativeWebPath(String type) {
        for (Iterator<AssetPojo> iterator = assets.iterator(); iterator.hasNext();) {
            AssetPojo assetViewBean = (AssetPojo) iterator.next();
            if (assetViewBean.getType().equals(type)) {
                return assetViewBean.getRelativeWebPath();
            }
        }
        return null;
    }

    public void setAssets(List<AssetPojo> assets) {
        this.assets = assets;
    }

    public List<ProductSkuPricePojo> getPrices() {
        return prices;
    }

    public void setPrices(List<ProductSkuPricePojo> prices) {
        this.prices = prices;
    }

    public List<ProductSkuStockPojo> getStocks() {
        return stocks;
    }

    public void setStocks(List<ProductSkuStockPojo> stocks) {
        this.stocks = stocks;
    }

    public List<RetailerPojo> getRetailers() {
        return retailers;
    }

    public void setRetailers(List<RetailerPojo> retailers) {
        this.retailers = retailers;
    }
    
    public String getDetailsUrl() {
        return detailsUrl;
    }
    
    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }
    
}