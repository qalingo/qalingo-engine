/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.service;

import javax.servlet.http.HttpServletRequest;

import fr.hoteia.qalingo.core.common.domain.Localization;
import fr.hoteia.qalingo.core.common.domain.Market;
import fr.hoteia.qalingo.core.common.domain.MarketArea;
import fr.hoteia.qalingo.core.common.domain.MarketPlace;
import fr.hoteia.qalingo.core.common.domain.Order;
import fr.hoteia.qalingo.core.common.domain.Retailer;

public interface UrlService {

	// COMMON
	
	String buildRootUrl(HttpServletRequest request);
	
	String buildHomeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildHomeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);
	
	String buildOurCompanyUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildOurCompanyUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildClpUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildClpUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildFollowUsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildFollowUsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildContactUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildContactUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildLegacyUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildLegacyUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildFaqUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildFaqUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildStoreLocationUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildStoreLocationUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);
	
	String buildConditionOfUseUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildConditionOfUseUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildSearchUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildSearchUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildChangeLanguageUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildChangeLanguageUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	// CATALOG
	
	String buildProductBrandLineUrlAsProductAxeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String brandName, String brandCode);
	
	String buildProductBrandLineUrlAsProductAxeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String brandName, String brandCode, boolean keepCurrentDomainName);

	String buildProductBrandDetailsUrlAsProductAxeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String brandName, String brandCode);
	
	String buildProductBrandDetailsUrlAsProductAxeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String brandName, String brandCode, boolean keepCurrentDomainName);
	
	String buildProductCategoryUrlAsProductAxeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName, String categoryCode);

	String buildProductCategoryUrlAsProductAxeUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName, String categoryCode, boolean keepCurrentDomainName);
	
	String buildProductCategoryUrlAsProductLineUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName, String categoryCode);

	String buildProductCategoryUrlAsProductLineUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName, String categoryCode, boolean keepCurrentDomainName);
	
	String buildProductUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName, String categoryCode, String productName, String productCode);

	String buildProductUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName, String categoryCode, String productName, String productCode, boolean keepCurrentDomainName);

	String buildProductAddToCartUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName, String categoryCode, String productName, String productCode, String productSkuName, String productSkuCode);

	String buildProductAddToCartUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName, String categoryCode, String productName, String productCode, String productSkuName, String productSkuCode, boolean keepCurrentDomainName);

	String buildProductRemoveFromCartUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String skuCode);

	String buildProductRemoveFromCartUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String skuCode, boolean keepCurrentDomainName);

	String buildProductAddToWishlistUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName, String categoryCode, String productName, String productCode, String productSkuName, String productSkuCode);

	String buildProductAddToWishlistUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String categoryName, String categoryCode, String productName, String productCode, String productSkuName, String productSkuCode, boolean keepCurrentDomainName);

	String buildProductRemoveFromWishlistUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String skuCode);

	String buildProductRemoveFromWishlistUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String skuCode, boolean keepCurrentDomainName);

	// CART
	
	String buildCartDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildCartDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildCartDetailsUpdateUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildCartDetailsUpdateUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildCartAuthUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildCartAuthUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildCartDeliveryAndOrderDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);
	
	String buildCartDeliveryAndOrderDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildCartOrderPaymentUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildCartOrderPaymentUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);
	
	String buildCartOrderConfirmationUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildCartOrderConfirmationUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);
	
	// CUSTOMER
	
	String buildCustomerDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildCustomerDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildCustomerOrderListUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);
	
	String buildCustomerOrderListUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);
	
	String buildCustomerOrderDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String orderId);
	
	String buildCustomerOrderDetailsUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String orderId, boolean keepCurrentDomainName);
	
	String buildCustomerWishlistUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildCustomerWishlistUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);

	String buildCustomerCreateAccountUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);
	
	String buildCustomerCreateAccountUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);
	
	String buildCustomerAddressListUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);
	
	String buildCustomerAddressListUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);
	
	String buildCustomerAddAddressUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);
	
	String buildCustomerAddAddressUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, boolean keepCurrentDomainName);
	
	String buildCustomerEditAddressUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String customerAddressId);

	String buildCustomerDeleteAddressUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer, String customerAddressId);

	// SECURITY
	
	String buildLoginUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);
	
	String buildLogoutUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);
	
	String buildForbiddenUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildTimeoutUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildForgottenPasswordUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);
	
	String buildSpringSecurityCheckUrl(HttpServletRequest request, MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);
	
}
