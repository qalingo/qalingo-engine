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
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.FoPageConstants;
import fr.hoteia.qalingo.core.RequestConstants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.service.EngineSettingService;
import fr.hoteia.qalingo.core.service.UrlService;

@Service(value = "urlService")
public class UrlServiceImpl implements UrlService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	public CoreMessageSource coreMessageSource;

	@Autowired
	public EngineSettingService engineSettingService;
	
	// COMMON

	public String buildRootUrl(final HttpServletRequest request) throws Exception {
		return getContextPrefixUrl(request, null) + "";
	}

	public String buildHomeUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildHomeUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildHomeUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.home") + FoPageConstants.HOME_URL;
	}

	public String buildOurCompanyUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildOurCompanyUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildOurCompanyUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.our.company") + "/" + "our-company.html";
	}

	public String buildClpUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildClpUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildClpUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.clp") + "/" + "clp.html";
	}

	public String buildFollowUsUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildFollowUsUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildFollowUsUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.follow.us") + "/" + "follow-us.html";
	}
	
	public String buildNewsletterRegisterUrl(final HttpServletRequest request, final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/newsletter-register.html";
	}

	public String buildContactUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildContactUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildContactUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.contact") + "/" + "contact.html";
	}

	public String buildLegalTermsUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildLegalTermsUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildLegalTermsUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.legal.terms") + "/" + "legal-terms.html";
	}

	public String buildFaqUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildFaqUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildFaqUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.faq") + "/" + "faq.html";
	}

	public String buildStoreLocationUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildStoreLocationUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildStoreLocationUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.store") + "/" + "store-location.html";
	}
	
	public String buildRetailerDetailsUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String retailerName, final String retailerCode) throws Exception {
		return buildRetailerDetailsUrl(request, marketPlace, market, marketArea, localization, retailer, retailerName, retailerCode, true);
	}

	public String buildRetailerDetailsUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String retailerName, final String retailerCode, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.store") + "-" + handleString(retailerName) + "/" + "retailer-details-" + retailerCode.toLowerCase() + ".html";
	}
	
	public String buildRetailerCreateUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildRetailerCreateUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildRetailerCreateUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.store") + "/" + "retailer-create.html";
	}
	
	public String buildRetailerVoteUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String retailerName, final String retailerCode) throws Exception {
		return buildRetailerVoteUrl(request, marketPlace, market, marketArea, localization, retailer, retailerName, retailerCode, true);
	}

	public String buildRetailerVoteUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String retailerName, final String retailerCode, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.store") + "-" +handleString(retailerName) + "/" + "retailer-vote-" + retailerCode.toLowerCase() + ".html";
	}
	
	public String buildRetailerCommentUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String retailerName, final String retailerCode) throws Exception {
		return buildRetailerCommentUrl(request, marketPlace, market, marketArea, localization, retailer, retailerName, retailerCode, true);
	}

	public String buildRetailerCommentUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String retailerName, final String retailerCode, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.store")+ "-" +handleString(retailerName) + "/" + "retailer-comment-" + retailerCode.toLowerCase() + ".html";
	}

	public String buildConditionOfUseUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildConditionOfUseUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildConditionOfUseUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.conditions.of.use")
		        + FoPageConstants.CONDITIONS_OF_USE_URL;
	}

	public String buildSearchUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildSearchUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildSearchUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/search.html";
	}

	public String buildChangeLanguageUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildChangeLanguageUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildChangeLanguageUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getContextPrefixUrl(request, marketArea, keepCurrentDomainName) + getMarketPlacePrefixUrl(marketPlace) + getMarketPrefixUrl(market) + getMarketModePrefixUrl(marketArea)
		        + getLocalizationPrefixUrl(localization) + getRetailerPrefixUrl(retailer) + getSiteDefaultPrefixUrl(localization) + "/" + getMessage(localization, "seo.url.home") + "/"
		        + "home.html";
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

	// CATALOG

	public String buildProductBrandLineUrlAsProductAxeUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final String brandName, final String brandCode) throws Exception {
		return buildProductBrandLineUrlAsProductAxeUrl(request, marketPlace, market, marketArea, localization, retailer, brandName, brandCode, true);
	}

	public String buildProductBrandLineUrlAsProductAxeUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final String brandName, final String brandCode, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + handleString(brandName) + "/" + "brand-line-"
		        + brandCode.toLowerCase() + ".html";
	}

	public String buildProductBrandDetailsUrlAsProductAxeUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final String brandName, final String brandCode) throws Exception {
		return buildProductBrandDetailsUrlAsProductAxeUrl(request, marketPlace, market, marketArea, localization, retailer, brandName, brandCode, true);
	}

	public String buildProductBrandDetailsUrlAsProductAxeUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final String brandName, final String brandCode, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + handleString(brandName) + "/" + "brand-details-"
		        + brandCode.toLowerCase() + ".html";
	}

	public String buildProductCategoryUrlAsProductAxeUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final String categoryName, final String categoryCode) throws Exception {
		return buildProductCategoryUrlAsProductAxeUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, true);
	}

	public String buildProductCategoryUrlAsProductAxeUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final String categoryName, final String categoryCode, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + handleString(categoryName) + "/" + "product-axe-"
		        + categoryCode.toLowerCase() + ".html";
	}

	public String buildProductCategoryUrlAsProductLineUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final String categoryName, final String categoryCode) throws Exception {
		return buildProductCategoryUrlAsProductLineUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, true);
	}

	public String buildProductCategoryUrlAsProductLineUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final String categoryName, final String categoryCode, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + handleString(categoryName) + "/" + "product-line-"
		        + categoryCode.toLowerCase() + ".html";
	}

	public String buildProductUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String categoryName, final String categoryCode, final String productName, final String productCode) throws Exception {
		return buildProductUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName, productCode, true);
	}

	public String buildProductUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String categoryName, final String categoryCode, final String productName, final String productCode, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + handleString(categoryName) + "/" + handleString(productName)
		        + "/" + "product-details-" + categoryCode.toLowerCase() + "-" + productCode.toLowerCase() + ".html";
	}

	public String buildProductAddToCartUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String categoryName, final String categoryCode, final String productName, final String productCode, final String productSkuName, final String productSkuCode)
	        throws Exception {
		return buildProductAddToCartUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName, productCode, productSkuName,
		        productSkuCode, true);
	}

	public String buildProductAddToCartUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String categoryName, final String categoryCode, final String productName, final String productCode, final String productSkuName,
	        final String productSkuCode, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + handleString(categoryName) + "/" + handleString(productName)
		        + "/" + "add-to-cart-" + categoryCode.toLowerCase() + "-" + productCode.toLowerCase() + "-" + productSkuCode.toLowerCase() + ".html";
	}

	public String buildProductRemoveFromCartUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String skuCode) throws Exception {
		return buildProductRemoveFromCartUrl(request, marketPlace, market, marketArea, localization, retailer, skuCode, true);
	}

	public String buildProductRemoveFromCartUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String skuCode, final boolean keepCurrentDomainName) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/remove-from-cart.html" + "?" + RequestConstants.REQUEST_PARAM_PRODUCT_SKU_CODE + "=" + skuCode.toLowerCase();
	}

	public String buildProductAddToWishlistUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String categoryName, final String categoryCode, final String productName, final String productCode, final String productSkuName, final String productSkuCode)
	        throws Exception {
		return buildProductAddToWishlistUrl(request, marketPlace, market, marketArea, localization, retailer, categoryName, categoryCode, productName, productCode, productSkuName,
		        productSkuCode, true);
	}

	public String buildProductAddToWishlistUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String categoryName, final String categoryCode, final String productName, final String productCode, final String productSkuName,
	        final String productSkuCode, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + handleString(categoryName) + "/" + handleString(productName)
		        + "/" + "add-to-wishlist-" + categoryCode.toLowerCase() + "-" + productCode.toLowerCase() + "-" + productSkuCode.toLowerCase() + ".html";
	}

	public String buildProductRemoveFromWishlistUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String skuCode) throws Exception {
		return buildProductRemoveFromWishlistUrl(request, marketPlace, market, marketArea, localization, retailer, skuCode, true);
	}

	public String buildProductRemoveFromWishlistUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final String skuCode, final boolean keepCurrentDomainName) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/remove-from-wishlist.html" + "?" + RequestConstants.REQUEST_PARAM_PRODUCT_SKU_CODE + "=" + skuCode.toLowerCase();
	}

	// CART

	public String buildCartDetailsUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildCartDetailsUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildCartDetailsUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.shoppingcart") + "/" + "cart-details.html";
	}

	public String buildCartDetailsUpdateUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildCartDetailsUpdateUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildCartDetailsUpdateUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.shoppingcart") + "/"
		        + "cart-details-update.html";
	}

	public String buildCartAuthUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildCartAuthUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildCartAuthUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.shoppingcart") + "/" + "cart-auth.html";
	}

	public String buildCartDeliveryAndOrderDetailsUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer) throws Exception {
		return buildCartDeliveryAndOrderDetailsUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildCartDeliveryAndOrderDetailsUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea,
	        final Localization localization, final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.shoppingcart") + "/"
		        + "cart-delivery-order-information.html";
	}

	public String buildCartOrderPaymentUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildCartOrderPaymentUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildCartOrderPaymentUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.shoppingcart") + "/"
		        + "cart-order-payment.html";
	}

	public String buildCartOrderConfirmationUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildCartOrderConfirmationUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildCartOrderConfirmationUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.shoppingcart") + "/"
		        + "cart-order-confirmation.html";
	}

	// CUSTOMER

	public String buildCustomerCreateAccountUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildCustomerCreateAccountUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	public String buildCustomerCreateAccountUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, final boolean keepCurrentDomainName) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getMessage(localization, "seo.url.customer") + "/customer-create-account.html";
	}
	
	public String buildCustomerDetailsUrl(final HttpServletRequest request, final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/customer-details.html";
	}
	
	public String buildCustomerEditUrl(final HttpServletRequest request, final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/customer-edit.html";
	}

	public String buildCustomerOrderListUrl(final HttpServletRequest request, final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/customer-order-list.html";
	}

	public String buildCustomerOrderDetailsUrl(final HttpServletRequest request, final MarketArea marketArea, final String orderId) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/customer-order-details.html?" + RequestConstants.REQUEST_PARAM_CUSTOMER_ORDER_ID + "=" + orderId;
	}

	public String buildCustomerWishlistUrl(final HttpServletRequest request, final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/customer-wishlist.html";
	}

	public String buildCustomerProductCommentUrl(final HttpServletRequest request, final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/customer-product-comment-list.html";
	}

	public String buildCustomerAddressListUrl(final HttpServletRequest request, final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/customer-address-list.html";
	}

	public String buildCustomerAddAddressUrl(final HttpServletRequest request, final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/customer-add-address.html";
	}

	public String buildCustomerEditAddressUrl(final HttpServletRequest request, final MarketArea marketArea, final String customerAddressId) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/customer-edit-address.html?" + RequestConstants.REQUEST_PARAM_CUSTOMER_ADDRESS_ID + "=" + customerAddressId;
	}

	public String buildCustomerDeleteAddressUrl(final HttpServletRequest request, final MarketArea marketArea, final String customerAddressId) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/customer-delete-address.html?" + RequestConstants.REQUEST_PARAM_CUSTOMER_ADDRESS_ID + "=" + customerAddressId;
	}

	// SECURITY

	public String buildLoginUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/login.html";
	}

	public String buildLoginSuccesUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return buildHomeUrl(request, marketPlace, market, marketArea, localization, retailer);
	}

	public String buildLogoutUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/logout-session.html";
	}

	public String buildForbiddenUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "sc/forbidden.html";
	}

	public String buildTimeoutUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.timeout") + "/" + "timeout.html";
	}

	public String buildForgottenPasswordUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer) + getMessage(localization, "seo.url.forgotten.password") + "/" + "forgotten-password.html";
	}

	public String buildSpringSecurityCheckUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getContextPrefixUrl(request, marketArea) + "" + "j_spring_security_check";
	}

	// PREFIX

	protected String getFullPrefixUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer) throws Exception {
		return getFullPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, true);
	}

	protected String getFullPrefixUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, boolean keepCurrentDomainName) throws Exception {
		String fullPrefixUrl = getBasicPrefixUrl(request, marketPlace, market, marketArea, localization, retailer, keepCurrentDomainName) + getSiteDefaultPrefixUrl(localization) + "/";
		return fullPrefixUrl;
	}

	protected String getBasicPrefixUrl(final HttpServletRequest request, final MarketPlace marketPlace, final Market market, final MarketArea marketArea, final Localization localization,
	        final Retailer retailer, boolean keepCurrentDomainName) throws Exception {
		String basicPrefixUrl = getContextPrefixUrl(request, marketArea, keepCurrentDomainName) + getMarketPlacePrefixUrl(marketPlace) + getMarketPrefixUrl(market)
		        + getMarketModePrefixUrl(marketArea) + getLocalizationPrefixUrl(localization) + getRetailerPrefixUrl(retailer);
		return basicPrefixUrl;
	}

	protected String getContextPrefixUrl(final HttpServletRequest request, final MarketArea marketArea) throws Exception {
		return getContextPrefixUrl(request, marketArea, true);
	}

	protected String getContextPrefixUrl(final HttpServletRequest request, final MarketArea marketArea, boolean keepCurrentDomainName) throws Exception {
		String contextPrefixUrl = "";
		if (BooleanUtils.negate(keepCurrentDomainName)){
			if (request.getRequestURL().toString().contains("localhost") || request.getRequestURL().toString().contains("127.0.0.1")){
				String domainName = "localhost:18080/fo-mcommerce";
				contextPrefixUrl = "http://" + domainName + "/";
			} else {
				String domainName = marketArea.getDomainName();
				contextPrefixUrl = "http://" + domainName + "/";
			}
		} else {
			if (request.getRequestURL().toString().contains("localhost") || request.getRequestURL().toString().contains("127.0.0.1")){
				contextPrefixUrl = contextPrefixUrl + request.getContextPath() + "/";
			} else {
				contextPrefixUrl = "/";
			}
		}
		return contextPrefixUrl;
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

	protected String handleString(String string) throws Exception {
		if(StringUtils.isNotEmpty(string)) {
			string = string.replaceAll(" ", "-");
			string = string.replaceAll("_", "-");
			String escapeAccent = engineSettingService.withEscapeAccent().getDefaultValue();
			if(BooleanUtils.toBoolean(escapeAccent)){
				string = string.replaceAll("[àáâãäå]", "a");
				string = string.replaceAll("[ç]", "c");
				string = string.replaceAll("[èéêë]", "e");
				string = string.replaceAll("[ìíîï]", "i");
				string = string.replaceAll("[ðòóôõö]", "o");
				string = string.replaceAll("[ùúûü]", "u");
				string = string.replaceAll("[ýÿ]", "y");
			}
			string = encodeString(string).toLowerCase().trim();
		 }
		return string;
	}

	protected String encodeString(String url) throws Exception {
		if(StringUtils.isNotEmpty(url)){
			return URLEncoder.encode(url, Constants.UTF8);
		}
		return url;
	}

	protected String getMessage(final Localization localization, final String key) throws Exception {
		final Locale locale = localization.getLocale();
		return handleString(coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO, ScopeWebMessage.SEO, key, locale));
	}

	protected MessageSource getMessageSource() throws Exception {
		return coreMessageSource;
	}

	protected String getSiteDefaultPrefixUrl(final Localization localization) throws Exception {
		final Locale locale = localization.getLocale();
		String seoUrl = handleString(coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO, ScopeWebMessage.SEO, "seo.url.main", locale));
		if (StringUtils.isNotEmpty(seoUrl)) {
			seoUrl = seoUrl.replace(" ", "-");
		}
		return seoUrl;
	}

}
