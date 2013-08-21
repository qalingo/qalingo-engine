/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.enumtype.BoUrls;
import fr.hoteia.qalingo.core.service.pojo.RequestData;
import fr.hoteia.qalingo.core.web.mvc.service.impl.AbstractUrlServiceImpl;
import fr.hoteia.qalingo.core.web.service.BackofficeUrlService;

@Service("backofficeUrlService")
@Transactional
public class BackofficeUrlServiceImpl extends AbstractUrlServiceImpl implements BackofficeUrlService {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
	public String buildProductMasterCategoryDetailsUrl(String productCategoryCode) throws Exception {
		return buildPrefix() + "/catalog-master-category-details.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
	}
	
	public String buildProductVirtualCategoryDetailsUrl(String productCategoryCode) throws Exception {
		return buildPrefix() + "/catalog-virtual-category-details.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
	}
	
	public String buildAddMasterProductCategoryUrl(String productCategoryCode) throws Exception {
		if(StringUtils.isNotEmpty(productCategoryCode)){
			return buildPrefix() + "/add-master-catalog-category.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
		} else {
			return buildPrefix() + "/add-master-catalog-category.html";
		}
	}

	public String buildAddVirtualProductCategoryUrl(String productCategoryCode) throws Exception {
		if(StringUtils.isNotEmpty(productCategoryCode)){
			return buildPrefix() + "/add-virtual-catalog-category.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
		} else {
			return buildPrefix() + "/add-virtual-catalog-category.html";
		}
	}
	
	public String buildMasterProductCategoryEditUrl(String productCategoryCode) throws Exception {
		return buildPrefix() + "/catalog-master-category-edit.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
	}
	
	public String buildVirtualProductCategoryEditUrl(String productCategoryCode) throws Exception {
		return buildPrefix() + "/catalog-virtual-category-edit.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE + "=" + handleString(productCategoryCode);
	}
	
	public String buildProductMarketingDetailsUrl(String productMarketingCode) throws Exception {
		return buildPrefix() + "/product-marketing-details.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_MARKETING_CODE + "=" + handleString(productMarketingCode);
	}
	
	public String buildProductMarketingEditUrl(String productMarketingCode) throws Exception {
		return buildPrefix() + "/product-marketing-edit.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_MARKETING_CODE + "=" + handleString(productMarketingCode);
	}
	
	public String buildProductSkuDetailsUrl(String productSkuCode) throws Exception {
		return buildPrefix() + "/product-sku-details.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_SKU_CODE + "=" + handleString(productSkuCode);
	}
	
	public String buildProductSkuEditUrl(String productSkuCode) throws Exception {
		return buildPrefix() + "/product-sku-edit.html?" + RequestConstants.REQUEST_PARAM_PRODUCT_SKU_CODE + "=" + handleString(productSkuCode);
	}
	
	public String buildAssetDetailsUrl(String assetCode) throws Exception {
		return buildPrefix() + "/asset-details.html?" + RequestConstants.REQUEST_PARAM_ASSET_CODE + "=" + handleString(assetCode);
	}
	
	public String buildAssetEditUrl(String assetCode) throws Exception {
		return buildPrefix() + "/asset-edit.html?" + RequestConstants.REQUEST_PARAM_ASSET_CODE + "=" + handleString(assetCode);
	}
	
	public String buildRuleDetailsUrl(String promotionCode) throws Exception {
		return buildPrefix() + "/rule-details.html?" + RequestConstants.REQUEST_PARAM_RULE_CODE + "=" + handleString(promotionCode);
	}
	
	public String buildRuleEditUrl(String promotionCode) throws Exception {
		return buildPrefix() + "/rule-edit.html?" + RequestConstants.REQUEST_PARAM_RULE_CODE + "=" + handleString(promotionCode);
	}
	
	public String buildShippingDetailsUrl(String shippingCode) throws Exception {
		return buildPrefix() + "/shipping-details.html?" + RequestConstants.REQUEST_PARAM_SHIPPING_CODE + "=" + handleString(shippingCode);
	}
	
	public String buildShippingEditUrl(String shippingCode) throws Exception {
		return buildPrefix() + "/shipping-edit.html?" + RequestConstants.REQUEST_PARAM_SHIPPING_CODE + "=" + handleString(shippingCode);
	}
	
	public String buildOrderDetailsUrl(String orderNum) throws Exception {
		return buildPrefix() + "/order-details.html?" + RequestConstants.REQUEST_PARAM_ORDER_CODE + "=" + handleString(orderNum);
	}
	
	public String buildOrderEditUrl(String orderNum) throws Exception {
		return buildPrefix() + "/order-edit.html?" + RequestConstants.REQUEST_PARAM_ORDER_CODE + "=" + handleString(orderNum);
	}
	
	public String buildCustomerDetailsUrl(String customerCode) throws Exception {
		return buildPrefix() + "/customer-details.html?" + RequestConstants.REQUEST_PARAM_CUSTOMER_CODE + "=" + handleString(customerCode);
	}
	
	public String buildCustomerEditUrl(String customerCode) throws Exception {
		return buildPrefix() + "/customer-edit.html?" + RequestConstants.REQUEST_PARAM_CUSTOMER_CODE + "=" + handleString(customerCode);
	}
	
	protected String buildPrefix(){
		return Constants.SPRING_URL_PATH;
	}
	
	// KEEP

    public String generateUrl(final BoUrls url, final RequestData requestData) {
    	return generateUrl(url, requestData, null);
    }
    
    @SuppressWarnings("unchecked")
    public String generateUrl(final BoUrls url, final RequestData requestData, Object... params) {
    	String urlStr = null;
    	Map<String, String> getParams = new HashMap<String, String>();
    	Map<String, String> urlParams = new HashMap<String, String>();
    	try {
        	if(params != null){
                for (Object param : params) {
                    if (param == null) continue;
                    if (param instanceof Map) {
                        getParams = (Map<String, String>) param;
                    } else {
                        LOG.warn("Unknowned url parameter : [{}]", param);
                    }
                }    		
        	}
        	
        	if(StringUtils.isEmpty(urlStr)){
        		urlStr = buildPrefix(requestData);
        	}
        	
        	urlStr = urlStr + url.getUrl();
	        
        } catch (Exception e) {
        	LOG.error("Can't build Url!", e);
        }
    	return handleUrlParameters(urlStr, urlParams, getParams);
    }
    
	public String buildChangeLanguageUrl(final RequestData requestData, final Localization localization) throws Exception {
		return buildPrefix(requestData) + BoUrls.CHANGE_LANGUAGE_URL + "?" + RequestConstants.REQUEST_PARAMETER_LOCALE_CODE + "=" + handleString(localization.getCode());
	}
	
	public String buildChangeContextUrl(final RequestData requestData) {
		final MarketPlace marketPlace = requestData.getMarketPlace();
		final Market market = requestData.getMarket();
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getLocalization();
		final Retailer retailer = requestData.getRetailer();
		
		String url = buildPrefix(requestData) + BoUrls.CHANGE_CONTEXT_URL + "?";
		url = url + RequestConstants.REQUEST_PARAMETER_MARKET_PLACE_CODE + "=" + marketPlace.getCode();
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_CODE + "=" + market.getCode();
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE + "=" + marketArea.getCode();
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_LANGUAGE + "=" + localization.getCode();
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_RETAILER_CODE + "=" + retailer.getCode();
		return url;
	}
	
	public String buildSpringSecurityCheckUrl(final RequestData requestData) throws Exception {
		return buildContextPath(requestData) + BoUrls.SPRING_SECURITY_URL;
	}
	
}