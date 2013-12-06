/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.AbstractRuleReferential;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.Shipping;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.BackofficeUrlService;

@Service("backofficeUrlService")
@Transactional
public class BackofficeUrlServiceImpl extends AbstractUrlServiceImpl implements BackofficeUrlService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public String buildChangeLanguageUrl(final RequestData requestData) throws Exception {
		final MarketPlace marketPlace = requestData.getMarketPlace();
		final Market market = requestData.getMarket();
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getMarketAreaLocalization();
		final Retailer retailer = requestData.getMarketAreaRetailer();
		
		String url = buildDefaultPrefix(requestData) + BoUrls.CHANGE_LANGUAGE.getUrlWithoutWildcard() + "?";
		url = url + RequestConstants.REQUEST_PARAMETER_MARKET_PLACE_CODE + "=" + handleString(marketPlace.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_CODE + "=" + handleString(market.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE + "=" + handleString(marketArea.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_RETAILER_CODE + "=" + handleString(retailer.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_LANGUAGE + "=" + handleString(localization.getCode());
		return url;
	}
	
	public String buildChangeContextUrl(final RequestData requestData) throws Exception {
		final MarketPlace marketPlace = requestData.getMarketPlace();
		final Market market = requestData.getMarket();
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getMarketAreaLocalization();
		final Retailer retailer = requestData.getMarketAreaRetailer();
		
		String url = buildDefaultPrefix(requestData) + BoUrls.CHANGE_CONTEXT.getUrlWithoutWildcard() + "?";
		url = url + RequestConstants.REQUEST_PARAMETER_MARKET_PLACE_CODE + "=" + handleString(marketPlace.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_CODE + "=" + handleString(market.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE + "=" + handleString(marketArea.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_RETAILER_CODE + "=" + handleString(retailer.getCode());
        url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_LANGUAGE + "=" + handleString(localization.getCode());
		return url;
	}
	
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
                    if (param instanceof CatalogCategoryMaster) {
                        CatalogCategoryMaster catalogCategoryMaster = (CatalogCategoryMaster) param;
                        getParams.put(RequestConstants.REQUEST_PARAMETER_PRODUCT_CATEGORY_CODE, handleParamValue(catalogCategoryMaster.getCode()));
                        break;
                    } else if (param instanceof CatalogCategoryVirtual) {
                        CatalogCategoryVirtual catalogCategoryVirtual = (CatalogCategoryVirtual) param;
                        getParams.put(RequestConstants.REQUEST_PARAMETER_PRODUCT_CATEGORY_CODE, handleParamValue(catalogCategoryVirtual.getId().toString()));
                        break;
                    } else if (param instanceof ProductMarketing) {
                        ProductMarketing productMarketing = (ProductMarketing) param;
                        getParams.put(RequestConstants.REQUEST_PARAMETER_PRODUCT_MARKETING_CODE, handleParamValue(productMarketing.getId().toString()));
                        break;
                    } else if (param instanceof ProductSku) {
                        ProductSku productSku = (ProductSku) param;
                        getParams.put(RequestConstants.REQUEST_PARAMETER_PRODUCT_SKU_CODE, handleParamValue(productSku.getId().toString()));
                        break;
                    } else if (param instanceof Asset) {
                        Asset asset = (Asset) param;
                        getParams.put(RequestConstants.REQUEST_PARAMETER_ASSET_CODE, handleParamValue(asset.getId().toString()));
                        break;
                    } else if (param instanceof AbstractRuleReferential) {
                        AbstractRuleReferential rule = (AbstractRuleReferential) param;
                        getParams.put(RequestConstants.REQUEST_PARAMETER_RULE_CODE, handleParamValue(rule.getId().toString()));
                        break;
                    } else if (param instanceof Shipping) {
                        Shipping shipping = (Shipping) param;
                        getParams.put(RequestConstants.REQUEST_PARAMETER_SHIPPING_CODE, handleParamValue(shipping.getId().toString()));
                        break;
                    } else if (param instanceof OrderCustomer) {
                        OrderCustomer order = (OrderCustomer) param;
                        getParams.put(RequestConstants.REQUEST_PARAMETER_ORDER_CODE, handleParamValue(order.getId().toString()));
                        break;
                    } else if (param instanceof Customer) {
                        Customer customer = (Customer) param;
                        getParams.put(RequestConstants.REQUEST_PARAMETER_CUSTOMER_CODE, handleParamValue(customer.getId().toString()));
                        break;
                    } else if (param instanceof EngineSetting) {
                        EngineSetting engineSetting = (EngineSetting) param;
                        getParams.put(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_ID, handleParamValue(engineSetting.getId().toString()));
                        break;
                    } else if (param instanceof EngineSettingValue) {
                        EngineSettingValue engineSettingValue = (EngineSettingValue) param;
                        getParams.put(RequestConstants.REQUEST_PARAMETER_ENGINE_SETTING_VALUE_ID, handleParamValue(engineSettingValue.getId().toString()));
                        break;
                    } else if (param instanceof AbstractPaymentGateway) {
                        AbstractPaymentGateway paymentGateway = (AbstractPaymentGateway) param;
                        getParams.put(RequestConstants.REQUEST_PARAMETER_PAYMENT_GATEWAY_ID, handleParamValue(paymentGateway.getId().toString()));
                        break;
                    } else if (param instanceof User) {
                        User user = (User) param;
                        getParams.put(RequestConstants.REQUEST_PARAMETER_USER_ID, handleParamValue(user.getId().toString()));
                        break;
                    } else if (param instanceof Map) {
                        getParams = (Map<String, String>) param;
                        break;
                    } else {
                        logger.warn("Unknowned url parameter : [{}]", param);
                    }
                }    		
        	}
        	
        	if(StringUtils.isEmpty(urlStr)){
                // AD THE DEFAULT PREFIX - DEFAULT PATH IS 
                urlStr = buildDefaultPrefix(requestData);
                if(url.withPrefixSEO()){
                    urlStr = getFullPrefixUrl(requestData);
                }
        	}
        	
            // REMOVE THE / AT EH END BEFORE ADDING THE /**.html segment
            if (urlStr.endsWith("/")) {
                urlStr = urlStr.substring(0, urlStr.length() - 1);
            }
        	
        	urlStr = urlStr + url.getUrlWithoutWildcard();
	        
        } catch (Exception e) {
        	logger.error("Can't build Url!", e);
        }
    	return handleUrlParameters(urlStr, urlParams, getParams);
    }
    
    @Override
    protected String getSeoSegmentMain(Locale locale) throws Exception{
        return "";
    }
    
	public String buildSpringSecurityCheckUrl(final RequestData requestData) throws Exception {
		return buildContextPath(requestData) + BoUrls.SPRING_SECURITY_URL;
	}
	
}