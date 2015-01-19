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
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.util.CoreUtil;

public class CartItemViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -7032831815877311774L;
	
	protected String skuCode;

    protected String i18nName;
    protected String i18nDescription;
    protected String i18nShortDescription;
    
	protected int quantity;
	protected String unitPriceWithCurrencySign;
    protected String feesWithCurrencySign;
	protected String amountWithCurrencySign;
	
    protected List<AssetViewBean> assets = new ArrayList<AssetViewBean>();
    
	protected String deleteUrl;
    protected String productDetailsUrl;

	public String getSkuCode() {
		return skuCode;
	}
	
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	
    public String getI18nName() {
        return i18nName;
    }
    
    public void setI18nName(String i18nName) {
        this.i18nName = i18nName;
    }
    
    public String getI18nDescription() {
        return i18nDescription;
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
    
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getUnitPriceWithCurrencySign() {
        return unitPriceWithCurrencySign;
    }

    public void setUnitPriceWithCurrencySign(String unitPriceWithCurrencySign) {
        this.unitPriceWithCurrencySign = unitPriceWithCurrencySign;
    }

    public String getFeesWithCurrencySign() {
        return feesWithCurrencySign;
    }

    public void setFeesWithCurrencySign(String feesWithCurrencySign) {
        this.feesWithCurrencySign = feesWithCurrencySign;
    }

    public String getAmountWithCurrencySign() {
        return amountWithCurrencySign;
    }

    public void setAmountWithCurrencySign(String amountWithCurrencySign) {
        this.amountWithCurrencySign = amountWithCurrencySign;
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
            if("default".equals(assetViewBean.getType())){
                return assetViewBean;
            }
        }
        return null;
    }
    
    public void setAssets(List<AssetViewBean> assets) {
        this.assets = assets;
    }

    public String getDeleteUrl() {
		return deleteUrl;
	}
	
	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}
	
	public String getProductDetailsUrl() {
        return productDetailsUrl;
    }
	
	public void setProductDetailsUrl(String productDetailsUrl) {
        this.productDetailsUrl = productDetailsUrl;
    }
	
}