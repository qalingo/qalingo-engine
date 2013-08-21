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
	
	public String buildRetailerDetailsUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String retailerName, final String retailerCode) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.store") + "-" + handleString(retailerName) + "/" + "retailer-details-" + retailerCode.toLowerCase() + ".html";
	}
	
	public String buildRetailerCreateUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.store") + "/" + "retailer-create.html";
	}
	
	public String buildRetailerContactUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String retailerName, final String retailerCode) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.store") + "-" + handleString(retailerName) + "/" + "retailer-contact-" + retailerCode.toLowerCase() + ".html";
	}
	
	public String buildRetailerVoteUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String retailerName, final String retailerCode) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.store") + "-" + handleString(retailerName) + "/" + "retailer-vote-" + retailerCode.toLowerCase() + ".html";
	}
	
	public String buildRetailerCommentUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String retailerName, final String retailerCode) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.store")+ "-" + handleString(retailerName) + "/" + "retailer-comment-" + retailerCode.toLowerCase() + ".html";
	}

	public String buildConditionOfUseUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.conditions.of.use");
	}

	public String buildSearchUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/search.html";
	}

	public String buildChangeLanguageUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getContextPrefixUrl(marketArea) + getMarketPlacePrefixUrl(marketPlace) + getMarketPrefixUrl(market) + getMarketModePrefixUrl(marketArea)
		        + getLocalizationPrefixUrl(localization) + getRetailerPrefixUrl(retailer) + getSiteDefaultPrefixUrl(localization) + "/" + getMessage(localization, "seo.url.home") + "/"
		        + "home.html";
	}

	public String buildProductBrandLineUrlAsProductAxeUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final String brandName, final String brandCode) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + handleString(brandName) + "/" + "brand-line-"
		        + brandCode.toLowerCase() + ".html";
	}

	public String buildProductBrandDetailsUrlAsProductAxeUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final String brandName, final String brandCode) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + handleString(brandName) + "/" + "brand-details-"
		        + brandCode.toLowerCase() + ".html";
	}

	public String buildProductCategoryUrlAsProductAxeUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final String categoryName, final String categoryCode) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + handleString(categoryName) + "/" + "product-axe-"
		        + categoryCode.toLowerCase() + ".html";
	}

	public String buildProductCategoryUrlAsProductLineUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final String categoryName, final String categoryCode) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + handleString(categoryName) + "/" + "product-line-"
		        + categoryCode.toLowerCase() + ".html";
	}

	public String buildProductUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String categoryName, final String categoryCode, final String productName, final String productCode) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + handleString(categoryName) + "/" + handleString(productName)
		        + "/" + "product-details-" + categoryCode.toLowerCase() + "-" + productCode.toLowerCase() + ".html";
	}

	public String buildProductAddToCartUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String categoryName, final String categoryCode, final String productName, final String productCode, final String productSkuName,
	        final String productSkuCode) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + handleString(categoryName) + "/" + handleString(productName)
		        + "/" + "add-to-cart-" + categoryCode.toLowerCase() + "-" + productCode.toLowerCase() + "-" + productSkuCode.toLowerCase() + ".html";
	}

	public String buildProductRemoveFromCartUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String skuCode) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/remove-from-cart.html" + "?" + RequestConstants.REQUEST_PARAM_PRODUCT_SKU_CODE + "=" + skuCode.toLowerCase();
	}

	public String buildProductAddToWishlistUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String categoryName, final String categoryCode, final String productName, final String productCode, final String productSkuName,
	        final String productSkuCode) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + handleString(categoryName) + "/" + handleString(productName)
		        + "/" + "add-to-wishlist-" + categoryCode.toLowerCase() + "-" + productCode.toLowerCase() + "-" + productSkuCode.toLowerCase() + ".html";
	}

	public String buildProductRemoveFromWishlistUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String skuCode) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/remove-from-wishlist.html" + "?" + RequestConstants.REQUEST_PARAM_PRODUCT_SKU_CODE + "=" + skuCode.toLowerCase();
	}

	public String buildCartDetailsUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.shoppingcart") + "/" + "cart-details.html";
	}

	public String buildCartDetailsUpdateUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.shoppingcart") + "/"
		        + "cart-details-update.html";
	}

	public String buildCartAuthUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.shoppingcart") + "/" + "cart-auth.html";
	}

	public String buildCartDeliveryAndOrderDetailsUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.shoppingcart") + "/"
		        + "cart-delivery-order-information.html";
	}

	public String buildCartOrderPaymentUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.shoppingcart") + "/"
		        + "cart-order-payment.html";
	}

	public String buildCartOrderConfirmationUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.shoppingcart") + "/"
		        + "cart-order-confirmation.html";
	}

	public String buildCustomerCreateAccountUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getFullPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.customer") + "/customer-create-account.html";
	}
	
	public String buildCustomerDetailsUrl(final MarketArea marketArea, String permalink) throws Exception {
		return getContextPrefixUrl(marketArea) + "customer/" + permalink;
	}
	
	public String buildCustomerDetailsUrl(final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/personal-details.html";
	}
	
	public String buildCustomerEditUrl(final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/personal-edit.html";
	}

	public String buildCustomerOrderListUrl(final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/personal-order-list.html";
	}

	public String buildCustomerOrderDetailsUrl(final MarketArea marketArea, final String orderId) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/personal-order-details.html?" + RequestConstants.REQUEST_PARAM_CUSTOMER_ORDER_ID + "=" + orderId;
	}

	public String buildCustomerWishlistUrl(final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/personal-wishlist.html";
	}

	public String buildCustomerProductCommentUrl(final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/personal-product-comment-list.html";
	}

	public String buildCustomerAddressListUrl(final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/personal-address-list.html";
	}

	public String buildCustomerAddAddressUrl(final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/personal-add-address.html";
	}

	public String buildCustomerEditAddressUrl(final MarketArea marketArea, final String customerAddressId) throws Exception {
		if(StringUtils.isNotEmpty(customerAddressId)){
			return getContextPrefixUrl(marketArea) + "sc/personal-edit-address.html?" + RequestConstants.REQUEST_PARAM_CUSTOMER_ADDRESS_ID + "=" + customerAddressId;
		} else {
			return getContextPrefixUrl(marketArea) + "sc/personal-edit-address.html";
		}
	}

	public String buildCustomerDeleteAddressUrl(final MarketArea marketArea, final String customerAddressId) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/personal-delete-address.html?" + RequestConstants.REQUEST_PARAM_CUSTOMER_ADDRESS_ID + "=" + customerAddressId;
	}
	
	protected String getContextPrefixUrl(final MarketArea marketArea) throws Exception {
		return "/";
	}

	// TODO : REWRITE
	public String buildOAuthConnectUrl(final MarketArea marketArea, final String socialNetworkCode) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/connect-oauth-" + socialNetworkCode + ".html";
	}
	
	public String buildOAuthCallBackUrl(final MarketArea marketArea, String socialNetworkCode) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/callback-oauth-" + socialNetworkCode + ".html";
	}
	
	public String buildOpenIdConnectUrl(final MarketArea marketArea, final String socialNetworkCode) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/connect-openid-" + socialNetworkCode + ".html";
	}
	
	public String buildOpenIdCallBackUrl(final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/callback-openid.html";
	}
	
	public String buildXrdsUrl(final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(marketArea) + "sc/xrds.html";
	}
	
	public String buildAbsoluteUrl(final MarketArea marketArea, final String contextNameValue, final String relativeUrl) throws Exception {
		String absoluteUrl = buildDomainePathUrl(marketArea, contextNameValue);
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
	
	public String buildDomainePathUrl(final MarketArea marketArea, final String contextNameValue) throws Exception {
		String domainePathUrl = "";
		if(marketArea != null){
			String domainName = marketArea.getDomainName(contextNameValue);
			domainePathUrl = domainName;
		}
//		if(StringUtils.isEmpty(domainePathUrl)){
//			domainePathUrl = request.getServerName();
//		}
		if(!domainePathUrl.startsWith("http://")){
			domainePathUrl = "http://" + domainePathUrl;
		}
		return domainePathUrl;
	}

	// PREFIX
	protected String getFullPrefixUrl(final RequestData requestData) throws Exception {
		String fullPrefixUrl = getBasicPrefixUrl(requestData.getMarketPlace(), requestData.getMarket(), requestData.getMarketArea(), requestData.getLocalization(), requestData.getRetailer())
				+ getSiteDefaultPrefixUrl(requestData.getLocalization()) + "/";
		return fullPrefixUrl;
	}

	protected String getFullPrefixUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		String fullPrefixUrl = getBasicPrefixUrl(marketPlace, market, marketArea, localization, retailer) + getSiteDefaultPrefixUrl(localization) + "/";
		return fullPrefixUrl;
	}

	protected String getBasicPrefixUrl(final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		String basicPrefixUrl = getContextPrefixUrl(marketArea) + getMarketPlacePrefixUrl(marketPlace) + getMarketPrefixUrl(market)
		        + getMarketModePrefixUrl(marketArea) + getLocalizationPrefixUrl(localization) + getRetailerPrefixUrl(retailer);
		return basicPrefixUrl;
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

	protected String getSiteDefaultPrefixUrl(final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		String seoUrl = handleString(coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO, ScopeWebMessage.SEO, "seo.url.main", locale));
		if (StringUtils.isNotEmpty(seoUrl)) {
			seoUrl = seoUrl.replace(" ", "-");
		}
		return seoUrl;
	}
	
	// KEEP
	
    public String generateUrl(final FoUrls url, final RequestData requestData) {
    	return generateUrl(url, requestData, null);
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
    
	public String buildRootUrl() throws Exception {
		return getContextPrefixUrl(null) + "";
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
	
	public String buildSpringSecurityCheckUrl(final RequestData requestData) throws Exception {
		return buildContextPath(requestData) + FoUrls.SPRING_SECURITY_URL;
	}
	
}