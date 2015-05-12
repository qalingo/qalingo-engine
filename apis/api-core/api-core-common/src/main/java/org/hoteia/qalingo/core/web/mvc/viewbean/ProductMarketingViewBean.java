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

public class ProductMarketingViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 9190853998911450184L;

	protected String code;
	protected String name;
    protected String description;

    protected String i18nName;
    protected String i18nDescription;
    protected String i18nShortDescription;

	protected int positionItem;

	protected String backgroundImage;
	protected String carouselImage;
	protected String iconImage;

	protected boolean isDefault;
	protected boolean featured;

	protected ProductBrandViewBean brand;

    private Map<String, AttributeValueViewBean> globalAttributes = new HashMap<String, AttributeValueViewBean>();
    private Map<String, AttributeValueViewBean> marketAreaAttributes = new HashMap<String, AttributeValueViewBean>();

    protected List<ProductSkuViewBean> productSkus = new ArrayList<ProductSkuViewBean>();
    protected List<ProductAssociationLinkViewBean> productAssociationLinks = new ArrayList<ProductAssociationLinkViewBean>();
    protected List<AssetViewBean> assets = new ArrayList<AssetViewBean>();

    protected List<CatalogCategoryViewBean> categories = new ArrayList<CatalogCategoryViewBean>();
    protected List<String> categoryCodes = new ArrayList<String>();

    protected List<ProductMarketingCustomerCommentViewBean> comments = new ArrayList<ProductMarketingCustomerCommentViewBean>();
    protected List<ProductMarketingTagViewBean> tags = new ArrayList<ProductMarketingTagViewBean>();

    protected CustomerProductRatesViewBean customerProductRates;

    protected String detailsUrl;
    protected String editUrl;

    protected String addSkuUrl;

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
	    if(productSkus != null){
	        for (Iterator<ProductSkuViewBean> iterator = productSkus.iterator(); iterator.hasNext();) {
	            ProductSkuViewBean productSkuViewBean = (ProductSkuViewBean) iterator.next();
	            if(productSkuViewBean.isSalable){
	                return true;
	            }
	        }
	    }
        return false;
    }

    public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public ProductBrandViewBean getBrand() {
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

	public List<ProductSkuViewBean> getProductSkus() {
		return productSkus;
	}

	public void setProductSkus(List<ProductSkuViewBean> productSkus) {
		this.productSkus = productSkus;
	}

	public List<ProductAssociationLinkViewBean> getProductAssociationLinks() {
		return productAssociationLinks;
	}

	public void setProductAssociationLinks(List<ProductAssociationLinkViewBean> productAssociationLinks) {
		this.productAssociationLinks = productAssociationLinks;
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
        AssetViewBean assetViewBean = getDefaultAsset();
        if(assetViewBean != null){
            return assetViewBean.getRelativeWebPath();
        }
        return null;
    }

    public AssetViewBean getDefaultAsset() {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.isDefault()){
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

	public List<CatalogCategoryViewBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CatalogCategoryViewBean> categories) {
        this.categories = categories;
    }

    public List<String> getCategoryCodes() {
        return categoryCodes;
    }

    public void setCategoryCodes(List<String> categoryCodes) {
        this.categoryCodes = categoryCodes;
    }

    public String getAddToCartUrl() {
		if (productSkus != null) {
			for (Iterator<ProductSkuViewBean> iterator = productSkus.iterator(); iterator.hasNext();) {
				ProductSkuViewBean productSkuViewBean = (ProductSkuViewBean) iterator.next();
				if (productSkuViewBean.isDefault()) {
					return productSkuViewBean.getAddToCartUrl();
				}
			}
			if (!productSkus.isEmpty()) {
				ProductSkuViewBean productSkuViewBean = productSkus.get(0);
				return productSkuViewBean.getAddToCartUrl();
			}
		}
		return null;
	}

    @Deprecated
    public String getSkuCode() {
        return getDefaultSkuCode();
    }
    
	public String getDefaultSkuCode() {
		if (productSkus != null) {
			for (Iterator<ProductSkuViewBean> iterator = productSkus.iterator(); iterator.hasNext();) {
				ProductSkuViewBean productSkuViewBean = (ProductSkuViewBean) iterator.next();
				if (productSkuViewBean.isDefault()) {
					return productSkuViewBean.getCode();
				}
			}
			if (!productSkus.isEmpty()) {
				ProductSkuViewBean productSkuViewBean = productSkus.get(0);
				return productSkuViewBean.getCode();
			}
		}
		return null;
	}

    public CatalogCategoryViewBean getDefaultCatalogCategory() {
        if (productSkus != null) {
            for (Iterator<ProductSkuViewBean> iterator = productSkus.iterator(); iterator.hasNext();) {
                ProductSkuViewBean productSkuViewBean = (ProductSkuViewBean) iterator.next();
                if (productSkuViewBean.isDefault()) {
                    return productSkuViewBean.getDefaultCatalogCategory();
                }
            }
            if (!productSkus.isEmpty()) {
                ProductSkuViewBean productSkuViewBean = productSkus.get(0);
                return productSkuViewBean.getDefaultCatalogCategory();
            }
        }
        return null;
    }
    
	public String getAddToWishlistUrl() {
		if (productSkus != null) {
			for (Iterator<ProductSkuViewBean> iterator = productSkus.iterator(); iterator.hasNext();) {
				ProductSkuViewBean productSkuViewBean = (ProductSkuViewBean) iterator.next();
				if (productSkuViewBean.isDefault()) {
					return productSkuViewBean.getAddToWishlistUrl();
				}
			}
			if (!productSkus.isEmpty()) {
				ProductSkuViewBean productSkuViewBean = productSkus.get(0);
				return productSkuViewBean.getAddToWishlistUrl();
			}
		}
		return null;
	}

	public String getBestPriceWithCurrencySign() {
		if (productSkus != null) {
			for (Iterator<ProductSkuViewBean> iterator = productSkus.iterator(); iterator.hasNext();) {
				ProductSkuViewBean productSkuViewBean = (ProductSkuViewBean) iterator.next();
				if (productSkuViewBean.isDefault()) {
					if (productSkuViewBean.getBestPriceWithCurrencySign() != null) {
						return productSkuViewBean.getBestPriceWithCurrencySign();
					}
				}
			}
			if (!productSkus.isEmpty()) {
				ProductSkuViewBean productSkuViewBean = productSkus.get(0);
				if (productSkuViewBean != null
						&& productSkuViewBean.getBestPriceWithCurrencySign() != null) {
					return productSkuViewBean.getBestPriceWithCurrencySign();
				}
			}
		}
		return null;
	}

    public String getSalePriceWithCurrencySign() {
        if (productSkus != null) {
            for (Iterator<ProductSkuViewBean> iterator = productSkus.iterator(); iterator.hasNext();) {
                ProductSkuViewBean productSkuViewBean = (ProductSkuViewBean) iterator.next();
                if (productSkuViewBean.isDefault()) {
                    if (productSkuViewBean.getSalePriceWithCurrencySign() != null) {
                        return productSkuViewBean.getSalePriceWithCurrencySign();
                    }
                }
            }
            if (!productSkus.isEmpty()) {
                ProductSkuViewBean productSkuViewBean = productSkus.get(0);
                if (productSkuViewBean != null
                        && productSkuViewBean.getSalePriceWithCurrencySign() != null) {
                    return productSkuViewBean.getSalePriceWithCurrencySign();
                }
            }
        }
        return null;
    }
    
    public List<ProductMarketingCustomerCommentViewBean> getComments() {
        return comments;
    }

    public void setComments(List<ProductMarketingCustomerCommentViewBean> comments) {
        this.comments = comments;
    }

    public List<ProductMarketingTagViewBean> getTags() {
        return tags;
    }

    public void setTags(List<ProductMarketingTagViewBean> tags) {
        this.tags = tags;
    }
  
    public CustomerProductRatesViewBean getCustomerProductRates() {
        return customerProductRates;
    }

    public void setCustomerProductRates(CustomerProductRatesViewBean customerProductRatesViewBean) {
        this.customerProductRates = customerProductRatesViewBean;
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

    public String getAddSkuUrl() {
        return addSkuUrl;
    }

    public void setAddSkuUrl(String addSkuUrl) {
        this.addSkuUrl = addSkuUrl;
    }

}