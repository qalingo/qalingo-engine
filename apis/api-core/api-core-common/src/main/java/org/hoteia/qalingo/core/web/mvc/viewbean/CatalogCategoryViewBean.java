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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class CatalogCategoryViewBean extends AbstractViewBean implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 5107897401068601858L;

    protected String code;
    protected String name;
    protected String description;
    protected String ranking;

    protected String i18nName;
    protected String i18nDescription;

    protected String backgroundImage;
    protected String carouselImage;
    protected String slideshowImage;
    protected String iconImage;
    protected boolean isRoot;
    protected int countProduct;
    
    protected CatalogCategoryViewBean masterCategory;
    protected CatalogCategoryViewBean defaultParentCategory;

    private Map<String, AttributeValueViewBean> globalAttributes = new HashMap<String, AttributeValueViewBean>();
    private Map<String, AttributeValueViewBean> marketAreaAttributes = new HashMap<String, AttributeValueViewBean>();

    protected int countSubCategories = 0;
    protected List<CatalogCategoryViewBean> subCategories = new ArrayList<CatalogCategoryViewBean>();
    protected int countProductMarketings = 0;
    protected List<ProductMarketingViewBean> productMarketings = new ArrayList<ProductMarketingViewBean>();
    protected List<ProductMarketingViewBean> featuredProductMarketings = new ArrayList<ProductMarketingViewBean>();

    protected List<AssetViewBean> assets = new ArrayList<AssetViewBean>();

    protected String productAxeUrl;
    protected String productLineUrl;

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
    
    public String getRanking() {
        return ranking;
    }
    
    public void setRanking(String ranking) {
        this.ranking = ranking;
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

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getCarouselImage() {
        return carouselImage;
    }

    public void setCarouselImage(String carouselImage) {
        this.carouselImage = carouselImage;
    }

    public String getSlideshowImage() {
        return handleString(slideshowImage);
    }
    
    public void setSlideshowImage(String slideshowImage) {
        this.slideshowImage = slideshowImage;
    }
    
    public String getIconImage() {
        return handleString(iconImage);
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }

    public int getCountProduct() {
        return countProduct;
    }

    public void setCountProduct(int countProduct) {
        this.countProduct = countProduct;
    }

    public CatalogCategoryViewBean getMasterCategory() {
        return masterCategory;
    }
    
    public boolean isWithMasterCategory() {
        if(masterCategory != null){
            return true;
        }
        return false;
    }
    
    public void setMasterCategory(CatalogCategoryViewBean masterCategory) {
        this.masterCategory = masterCategory;
    }
    
    public CatalogCategoryViewBean getDefaultParentCategory() {
        return defaultParentCategory;
    }

    public void setDefaultParentCategory(CatalogCategoryViewBean defaultParentCategory) {
        this.defaultParentCategory = defaultParentCategory;
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

    public int getCountSubCategories() {
        return countSubCategories;
    }
    
    public void setCountSubCategories(int countSubCategories) {
        this.countSubCategories = countSubCategories;
    }
    
    public List<CatalogCategoryViewBean> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CatalogCategoryViewBean> subCategories) {
        this.subCategories = subCategories;
    }

    public int getCountProductMarketings() {
        return countProductMarketings;
    }
    
    public void setCountProductMarketings(int countProductMarketings) {
        this.countProductMarketings = countProductMarketings;
    }
    
    public List<ProductMarketingViewBean> getProductMarketings() {
        return productMarketings;
    }

    public void setProductMarketings(List<ProductMarketingViewBean> productMarketings) {
        this.productMarketings = productMarketings;
    }
    
    public List<ProductMarketingViewBean> getFeaturedProductMarketings() {
        return featuredProductMarketings;
    }

    public void setFeaturedProductMarketings(List<ProductMarketingViewBean> featuredProductMarketings) {
        this.featuredProductMarketings = featuredProductMarketings;
    }
    
    public List<AssetViewBean> getAssets() {
        return assets;
    }
    
    public void setAssets(List<AssetViewBean> assets) {
        this.assets = assets;
    }
    
    public String getProductAxeUrl() {
        return productAxeUrl;
    }

    public void setProductAxeUrl(String productAxeUrl) {
        this.productAxeUrl = productAxeUrl;
    }

    public String getProductLineUrl() {
        return productLineUrl;
    }

    public void setProductLineUrl(String productLineUrl) {
        this.productLineUrl = productLineUrl;
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