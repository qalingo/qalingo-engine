/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.util.CoreUtil;

public class ProductSkuViewBean extends AbstractViewBean {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -5163066749293135126L;

    protected String code;
    protected String name;
    protected String description;
    protected String backgroundImage;
    protected String carouselImage;
    protected String iconImage;

    protected String i18nName;
    protected String i18nDescription;
    protected String i18nShortDescription;
    
    protected int positionItem;
    protected boolean isDefault;
    protected boolean isSalable;
    
    protected ProductBrandViewBean brand;
    
    private Map<String, AttributeValueViewBean> globalAttributes = new HashMap<String, AttributeValueViewBean>();
    private Map<String, AttributeValueViewBean> marketAreaAttributes = new HashMap<String, AttributeValueViewBean>();
    
    protected ProductMarketingViewBean productMarketing;
    protected List<AssetViewBean> assets = new ArrayList<AssetViewBean>();
    
    protected List<ProductSkuCustomerCommentViewBean> comments = new ArrayList<ProductSkuCustomerCommentViewBean>();
    protected List<ProductSkuTagViewBean> tags = new ArrayList<ProductSkuTagViewBean>();

    protected List<ProductSkuOptionDefinitionViewBean> skuOptionDefinitions = new ArrayList<ProductSkuOptionDefinitionViewBean>();

    protected List<CatalogCategoryViewBean> catalogCategories = new ArrayList<CatalogCategoryViewBean>();

    @Deprecated
    protected String catalogPrice;
    protected String salePriceWithCurrencySign;
    protected String bestPriceWithCurrencySign;
    protected String currencySign;
    protected String currencyAbbreviated;
    
    protected String addToCartUrl;
    protected String removeFromCartUrl;
    protected String addToWishlistUrl;
    protected String removeFromWishlistUrl;

    protected String detailsUrl;
    protected String editUrl;
    
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

    public String getI18nName() {
        if(StringUtils.isNotEmpty(i18nName)){
            return i18nName;
        }
        return name;
    }
    
    public void setI18nName(String i18nName) {
        this.i18nName = i18nName;
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
        if(StringUtils.isNotEmpty(getI18nShortDescription())){
            if(getI18nShortDescription().length() >= 150){
                return CoreUtil.handleTruncatedDescription(getI18nShortDescription());
            } else {
                return getI18nShortDescription();
            }
        } else if (StringUtils.isNotEmpty(getI18nDescription())){
            if(getI18nDescription().length() >= 150){
                return CoreUtil.handleTruncatedDescription(getI18nDescription());
            } else {
                return getI18nDescription();
            }
        }
        return "";
    }

    public int getPositionItem() {
        return positionItem;
    }

    public void setPositionItem(int positionItem) {
        this.positionItem = positionItem;
    }
    
    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public boolean isSalable() {
        return isSalable;
    }

    public void setSalable(boolean isSalable) {
        this.isSalable = isSalable;
    }
    
    public ProductBrandViewBean getBrand() {
        if(brand == null && productMarketing != null){
            return productMarketing.getBrand();
        }
        return brand;
    }
    
    public void setBrand(ProductBrandViewBean brand) {
        this.brand = brand;
    }

    public Map<String, AttributeValueViewBean> getGlobalAttributes() {
        return globalAttributes;
    }
    
    public AttributeValueViewBean getGlobalAttribute(String code) {
        if(globalAttributes != null){
            AttributeValueViewBean attributeValue = globalAttributes.get(code);
            if(attributeValue != null){
                return attributeValue;
            }
        }
        return null;
    }
    
    public void setGlobalAttributes(Map<String, AttributeValueViewBean> globalAttributes) {
        this.globalAttributes = globalAttributes;
    }
    
    public Map<String, AttributeValueViewBean> getMarketAreaAttributes() {
        return marketAreaAttributes;
    }
    
    public AttributeValueViewBean getMarketAreaAttribute(String code) {
        if(marketAreaAttributes != null){
            AttributeValueViewBean attributeValue = marketAreaAttributes.get(code);
            if(attributeValue != null){
                return attributeValue;
            }
        }
        return null;
    }
    
    public void setMarketAreaAttributes(Map<String, AttributeValueViewBean> marketAreaAttributes) {
        this.marketAreaAttributes = marketAreaAttributes;
    }
    
    public ProductMarketingViewBean getProductMarketing() {
        return productMarketing;
    }
    
    public void setProductMarketing(ProductMarketingViewBean productMarketing) {
        this.productMarketing = productMarketing;
    }
    
    public List<AssetViewBean> getAssets() {
        return assets;
    }
    
    public List<AssetViewBean> getAssets(String type) {
        List<AssetViewBean> assetsByType = new ArrayList<AssetViewBean>();
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals(type)){
                assetsByType.add(assetViewBean);
            }
        }
        // DEFAULT PRODUCT MARKETING ASSET
        if(productMarketing != null){
            for (Iterator<AssetViewBean> iterator = productMarketing.getAssets().iterator(); iterator.hasNext();) {
                AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
                if(assetViewBean.getType().equals(type)){
                    assetsByType.add(assetViewBean);
                }
            }
        }
        if(assetsByType.size() == 0){
            assetsByType.add(getDefaultAsset());
        }
        return assetsByType;
    }
    
    public String getAssetPath(String type) {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals(type)){
                return assetViewBean.getPath();
            }
        }
        // DEFAULT PRODUCT MARKETING ASSET
        if(productMarketing != null){
            for (Iterator<AssetViewBean> iterator = productMarketing.getAssets().iterator(); iterator.hasNext();) {
                AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
                if(assetViewBean.getType().equals(type)){
                    return assetViewBean.getPath();
                }
            }
        }
        AssetViewBean assetViewBean = getDefaultAsset();
        if(assetViewBean != null){
            return assetViewBean.getPath();
        }
        return null;
    }
    
    public String getAssetAbsoluteWebPath(String type) {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals(type)){
                return assetViewBean.getAbsoluteWebPath();
            }
        }
        // DEFAULT PRODUCT MARKETING ASSET
        if(productMarketing != null){
            for (Iterator<AssetViewBean> iterator = productMarketing.getAssets().iterator(); iterator.hasNext();) {
                AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
                if(assetViewBean.getType().equals(type)){
                    return assetViewBean.getAbsoluteWebPath();
                }
            }
        }
        AssetViewBean assetViewBean = getDefaultAsset();
        if(assetViewBean != null){
            return assetViewBean.getAbsoluteWebPath();
        }
        return null;
    }
    
    public String getAssetRelativeWebPath(String type) {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals(type)){
                return assetViewBean.getRelativeWebPath();
            }
        }
        // DEFAULT PRODUCT MARKETING ASSET
        if(productMarketing != null){
            for (Iterator<AssetViewBean> iterator = productMarketing.getAssets().iterator(); iterator.hasNext();) {
                AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
                if(assetViewBean.getType().equals(type)){
                    return assetViewBean.getRelativeWebPath();
                }
            }
        }
        AssetViewBean assetViewBean = getDefaultAsset();
        if(assetViewBean != null){
            return assetViewBean.getRelativeWebPath();
        }
        return null;
    }

    public AssetViewBean getDefaultAsset() {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if("default".equals(assetViewBean.getType())){
                return assetViewBean;
            }
        }
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            return assetViewBean;
        }
        return null;
    }
    
    public boolean hasAssets(){
        if(assets != null && !assets.isEmpty()){
            return true;
        }
        return false;
    }
    
    public void setAssets(List<AssetViewBean> assets) {
        this.assets = assets;
    }
    
    public List<ProductSkuCustomerCommentViewBean> getComments() {
        return comments;
    }

    public void setComments(List<ProductSkuCustomerCommentViewBean> comments) {
        this.comments = comments;
    }

    public List<ProductSkuTagViewBean> getTags() {
        return tags;
    }

    public void setTags(List<ProductSkuTagViewBean> tags) {
        this.tags = tags;
    }
    
    public List<ProductSkuOptionDefinitionViewBean> getSkuOptionDefinitions() {
        return skuOptionDefinitions;
    }
    
    public void setSkuOptionDefinitions(List<ProductSkuOptionDefinitionViewBean> skuOptionDefinitions) {
        this.skuOptionDefinitions = skuOptionDefinitions;
    }
    
    public List<CatalogCategoryViewBean> getCatalogCategories() {
        return catalogCategories;
    }
    
    public CatalogCategoryViewBean getDefaultCatalogCategory() {
        if (catalogCategories != null) {
            for (Iterator<CatalogCategoryViewBean> iterator = catalogCategories.iterator(); iterator.hasNext();) {
                CatalogCategoryViewBean catalogCategoryViewBean = (CatalogCategoryViewBean) iterator.next();
                if (catalogCategoryViewBean.isDefault()) {
                    return catalogCategoryViewBean;
                }
            }
            if (!catalogCategories.isEmpty()) {
                CatalogCategoryViewBean catalogCategoryViewBean = catalogCategories.get(0);
                return catalogCategoryViewBean;
            }
        }
        return null;
    }
    
    public String getDefaultCatalogCategoryCode() {
        CatalogCategoryViewBean catalogCategoryViewBean = getDefaultCatalogCategory();
        if (catalogCategoryViewBean != null) {
            return catalogCategoryViewBean.getCode();
        }
        return null;
    }
    
    public void setCatalogCategories(List<CatalogCategoryViewBean> catalogCategories) {
        this.catalogCategories = catalogCategories;
    }
    
    @Deprecated
    public String getCatalogPrice() {
        return catalogPrice;
    }

    @Deprecated
    public void setCatalogPrice(String catalogPrice) {
        this.catalogPrice = catalogPrice;
    }

    public String getSalePriceWithCurrencySign() {
        return salePriceWithCurrencySign;
    }

    public void setSalePriceWithCurrencySign(String salePriceWithCurrencySign) {
        this.salePriceWithCurrencySign = salePriceWithCurrencySign;
    }

    public String getBestPriceWithCurrencySign() {
        return bestPriceWithCurrencySign;
    }

    public void setBestPriceWithCurrencySign(String bestPriceWithCurrencySign) {
        this.bestPriceWithCurrencySign = bestPriceWithCurrencySign;
    }

    public String getCurrencySign() {
        return currencySign;
    }

    public void setCurrencySign(String currencySign) {
        this.currencySign = currencySign;
    }

    public String getCurrencyAbbreviated() {
        return currencyAbbreviated;
    }

    public void setCurrencyAbbreviated(String currencyAbbreviated) {
        this.currencyAbbreviated = currencyAbbreviated;
    }

    public String getAddToCartUrl() {
        return addToCartUrl;
    }

    public void setAddToCartUrl(String addToCartUrl) {
        this.addToCartUrl = addToCartUrl;
    }

    public String getRemoveFromCartUrl() {
        return removeFromCartUrl;
    }

    public void setRemoveFromCartUrl(String removeFromCartUrl) {
        this.removeFromCartUrl = removeFromCartUrl;
    }

    public String getAddToWishlistUrl() {
        return addToWishlistUrl;
    }

    public void setAddToWishlistUrl(String addToWishlistUrl) {
        this.addToWishlistUrl = addToWishlistUrl;
    }

    public String getRemoveFromWishlistUrl() {
        return removeFromWishlistUrl;
    }

    public void setRemoveFromWishlistUrl(String removeFromWishlistUrl) {
        this.removeFromWishlistUrl = removeFromWishlistUrl;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    public String getEditUrl() {
        return editUrl;
    }

    public void setEditUrl(String editUrl) {
        this.editUrl = editUrl;
    }
    
}