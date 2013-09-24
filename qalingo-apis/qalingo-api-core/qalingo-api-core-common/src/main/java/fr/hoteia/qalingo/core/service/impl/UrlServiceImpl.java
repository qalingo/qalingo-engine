/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.CartItem;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.ProductBrand;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.enumtype.BoUrls;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.service.UrlService;
import fr.hoteia.qalingo.core.service.pojo.RequestData;
import fr.hoteia.qalingo.core.web.mvc.service.impl.AbstractUrlServiceImpl;

@Service(value = "urlService")
@Transactional
public class UrlServiceImpl extends AbstractUrlServiceImpl implements UrlService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	public String buildCustomerDetailsUrl(final RequestData requestData, String permalink) throws Exception {
		return buildContextPath(requestData) + "/customer/" + permalink;
	}
	
	public String buildAddThisUrl(String shareCode, String absoluteUrl) throws Exception {
		String url = null;
		if (StringUtils.isNotEmpty(shareCode) && StringUtils.isNotEmpty(absoluteUrl)){
			try {
				url = "http://api.addthis.com/oexchange/0.8/forward/" + shareCode + "/offer?url=" + URLEncoder.encode(absoluteUrl, Constants.ANSI);
			} catch (Exception e) {
				LOG.error("SocialNetwork AddThis URL can't be encode!", e);
			}
		}
		return url;
	}
	
	public String buildChangeLanguageUrl(final RequestData requestData, final Localization localization) throws Exception {
		return buildDefaultPrefix(requestData) + FoUrls.CHANGE_LANGUAGE_URL + "?" + RequestConstants.REQUEST_PARAMETER_LOCALE_CODE + "=" + handleString(localization.getCode());
	}
	
	public String buildChangeLanguageUrl(final RequestData requestData) throws Exception {
		final MarketPlace marketPlace = requestData.getMarketPlace();
		final Market market = requestData.getMarket();
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getLocalization();
		final Retailer retailer = requestData.getRetailer();
		
		String url = buildDefaultPrefix(requestData) + FoUrls.CHANGE_LANGUAGE_URL + "?";
		url = url + RequestConstants.REQUEST_PARAMETER_MARKET_PLACE_CODE + "=" + handleString(marketPlace.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_CODE + "=" + handleString(market.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE + "=" + handleString(marketArea.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_LANGUAGE + "=" + handleString(localization.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_RETAILER_CODE + "=" + handleString(retailer.getCode());
		return url;
	}
	
	public String buildChangeContextUrl(final RequestData requestData) throws Exception {
		final MarketPlace marketPlace = requestData.getMarketPlace();
		final Market market = requestData.getMarket();
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getLocalization();
		final Retailer retailer = requestData.getRetailer();
		
		String url = buildDefaultPrefix(requestData) + BoUrls.CHANGE_CONTEXT_URL + "?";
		url = url + RequestConstants.REQUEST_PARAMETER_MARKET_PLACE_CODE + "=" + handleString(marketPlace.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_CODE + "=" + handleString(market.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_AREA_CODE + "=" + handleString(marketArea.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_MARKET_LANGUAGE + "=" + handleString(localization.getCode());
		url = url + "&" + RequestConstants.REQUEST_PARAMETER_RETAILER_CODE + "=" + handleString(retailer.getCode());
		return url;
	}
	
	public String buildOAuthConnectUrl(final RequestData requestData, final String socialNetworkCode) throws Exception {
		return buildContextPath(requestData) + "/sc/connect-oauth-" + socialNetworkCode + ".html";
	}
	
	public String buildOAuthCallBackUrl(final RequestData requestData, String socialNetworkCode) throws Exception {
		return buildContextPath(requestData) + "/sc/callback-oauth-" + socialNetworkCode + ".html";
	}
	
	public String buildOpenIdConnectUrl(final RequestData requestData, final String socialNetworkCode) throws Exception {
		return buildContextPath(requestData) + "/sc/connect-openid-" + socialNetworkCode + ".html";
	}
	
	public String buildOpenIdCallBackUrl(final RequestData requestData) throws Exception {
		return buildContextPath(requestData) + "/sc/callback-openid.html";
	}
	
	public String buildAbsoluteUrl(final RequestData requestData, final String relativeUrl) throws Exception {
		String absoluteUrl = buildDomainePathUrl(requestData);
		if(!relativeUrl.startsWith("/")){
			absoluteUrl = absoluteUrl + "/" + relativeUrl;
		} else {
			absoluteUrl = absoluteUrl + relativeUrl;
		}
		if(!absoluteUrl.startsWith("http://")){
			absoluteUrl = "http://" + absoluteUrl;
		}
		return absoluteUrl;
	}
	
	public String buildDomainePathUrl(final RequestData requestData) throws Exception {
		final HttpServletRequest request = requestData.getRequest();
		final MarketArea marketArea = requestData.getMarketArea();
		final String contextNameValue = requestData.getContextNameValue();
		
		String domainePathUrl = "";
		if(marketArea != null){
			String domainName = marketArea.getDomainName(contextNameValue);
			if(StringUtils.isNotEmpty(domainName)){
				domainePathUrl = domainName;
			}
		}
		if(StringUtils.isEmpty(domainePathUrl)){
			String requestUrl = request.getRequestURL().toString();
			requestUrl = requestUrl.replace("http://", "");
			String[] urlBlock = requestUrl.split("/");
			domainePathUrl = urlBlock[0];
		}
		if(!domainePathUrl.startsWith("http")){
			String scheme = request.getScheme();
			domainePathUrl = scheme + "://" + domainePathUrl;
		}
		return domainePathUrl;
	}
	
    @SuppressWarnings("unchecked")
    public String generateUrl(final FoUrls url, final RequestData requestData, Object... params) {
    	String urlStr = null;
    	Map<String, String> getParams = new HashMap<String, String>();
    	Map<String, String> urlParams = new HashMap<String, String>();
    	try {
        	if(params != null){
                for (Object param : params) {
                    if (param == null) continue;
                    if (param instanceof Retailer) {
                    	Retailer retailer = (Retailer) param;
                    	urlParams.put(RequestConstants.URL_PATTERN_RETAILER_CODE, handleParamValue(retailer.getCode()));
                    	urlStr = getFullPrefixUrl(requestData) + handleString(retailer.getName());
                    	break;
                    } else if (param instanceof ProductSku) {
                    	ProductSku productSku = (ProductSku) param;
                    	urlParams.put(RequestConstants.URL_PATTERN_PRODUCT_SKU_CODE, handleParamValue(productSku.getCode()));
                    	urlStr = getFullPrefixUrl(requestData);
                    	for (Object subParam : params) {
                    		if (subParam instanceof CatalogCategoryVirtual) {
                            	CatalogCategoryVirtual category = (CatalogCategoryVirtual) subParam;
                            	urlParams.put(RequestConstants.URL_PATTERN_CATEGORY_CODE, handleParamValue(category.getCode()));
                            	urlStr = urlStr + handleString(category.getBusinessName()) + "/";
                            } else if (subParam instanceof ProductMarketing) {
                            	ProductMarketing productMarketing = (ProductMarketing) subParam;
                            	urlParams.put(RequestConstants.URL_PATTERN_PRODUCT_MARKETING_CODE, handleParamValue(productMarketing.getCode()));
                            	urlStr = urlStr + handleString(productMarketing.getBusinessName());
                            	break;
                            } 
                    	}
                    	break;
                    } else if (param instanceof CatalogCategoryVirtual) {
                    	CatalogCategoryVirtual category = (CatalogCategoryVirtual) param;
                    	urlParams.put(RequestConstants.URL_PATTERN_CATEGORY_CODE, handleParamValue(category.getCode()));
                    	urlStr = getFullPrefixUrl(requestData) + handleString(category.getBusinessName());
                    	break;
                    } else if (param instanceof ProductBrand) {
                    	ProductBrand productBrand = (ProductBrand) param;
                    	urlParams.put(RequestConstants.URL_PATTERN_BRAND_CODE, handleParamValue(productBrand.getCode()));
                    	urlStr = getFullPrefixUrl(requestData) + handleString(productBrand.getName());
                    	break;
                    } else if (param instanceof CartItem) {
                    	CartItem cartItem = (CartItem) param;
                    	urlParams.put(RequestConstants.URL_PATTERN_CART_ITEM_CODE, handleParamValue(cartItem.getId().toString()));
                    	break;
                    } else if (param instanceof Map) {
                        getParams = (Map<String, String>) param;
                        break;
                    } else {
                        LOG.warn("Unknowned url parameter : [{}]", param);
                    }
                }    		
        	}
        	
        	if(StringUtils.isEmpty(urlStr)){
        		urlStr = buildDefaultPrefix(requestData);
        	}
        	
        	urlStr = urlStr + url.getUrl();
	        
        } catch (Exception e) {
        	LOG.error("Can't build Url!", e);
        }
    	return handleUrlParameters(urlStr, urlParams, getParams);
    }
    
	public String buildSpringSecurityCheckUrl(final RequestData requestData) throws Exception {
		return buildContextPath(requestData) + FoUrls.SPRING_SECURITY_URL;
	}
	
	protected String getFullPrefixUrl(final RequestData requestData) throws Exception {
		String fullPrefixUrl = getSeoPrefixUrl(requestData) + "/";
		return fullPrefixUrl;
	}

	protected String getSeoPrefixUrl(final RequestData requestData) throws Exception {
		final MarketPlace marketPlace = requestData.getMarketPlace();
		final Market market = requestData.getMarket();
		final MarketArea marketArea = requestData.getMarketArea();
		final Localization localization = requestData.getLocalization();
        final Retailer retailer = requestData.getRetailer();
		final Locale locale = localization.getLocale();
		String seoPrefixUrl = buildContextPath(requestData) + "/" + getMarketPlacePrefixUrl(marketPlace) + getMarketPrefixUrl(market)
		        + getMarketModePrefixUrl(marketArea) + getLocalizationPrefixUrl(localization) + getRetailerPrefixUrl(retailer);
		
		seoPrefixUrl = seoPrefixUrl + handleString(coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO, ScopeWebMessage.SEO, "seo.url.main", locale));
		if (StringUtils.isNotEmpty(seoPrefixUrl)) {
			seoPrefixUrl = seoPrefixUrl.replace(" ", "-");
		}
		
		return seoPrefixUrl;
	}

	protected String getMarketPlacePrefixUrl(final MarketPlace marketPlace) throws Exception {
		String marketPlacePrefixUrl = marketPlace.getCode().toLowerCase() + "/";
		return marketPlacePrefixUrl;
	}

	protected String getMarketPrefixUrl(final Market market) throws Exception {
		String marketPrefixUrl = market.getCode().toLowerCase() + "/";
		return marketPrefixUrl;
	}

	protected String getMarketModePrefixUrl(final MarketArea marketArea) throws Exception {
		String marketAreaPrefixUrl = marketArea.getCode().toLowerCase() + "/";
		return marketAreaPrefixUrl;
	}

	protected String getLocalizationPrefixUrl(final Localization localization) throws Exception {
		String localizationPrefixUrl = localization.getCode().toLowerCase() + "/";
		return localizationPrefixUrl;
	}

	protected String getRetailerPrefixUrl(final Retailer retailer) throws Exception {
		String retailerPrefixUrl = retailer.getCode().toLowerCase() + "/";
		return retailerPrefixUrl;
	}

}