/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.service;

import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;

public interface BackofficeUrlService {

	String buildHomeUrl();
	
	String buildLoginUrl();
	
	String buildLogoutUrl();
	
	String buildUserDetailsUrl();
	
	String buildUserEditUrl();
	
	String buildManageMasterCatalogUrl();
	
	String buildManageVirtualCatalogUrl();
	
	String buildProductMasterCategoryDetailsUrl(String productCategoryCode);

	String buildProductVirtualCategoryDetailsUrl(String productCategoryCode);

	String buildAddMasterProductCategoryUrl(String productCategoryCode);

	String buildAddVirtualProductCategoryUrl(String productCategoryCode);
	
	String buildMasterProductCategoryEditUrl(String productCategoryCode);

	String buildVirtualProductCategoryEditUrl(String productCategoryCode);

	String buildMasterProductCategoryFormPostUrl();
	
	String buildVirtualProductCategoryFormPostUrl();

	String buildProductMarketingDetailsUrl(String productMarketingCode);
	
	String buildProductMarketingEditUrl(String productMarketingCode);

	String buildProductMarketingFormPostUrl();

	String buildProductSkuDetailsUrl(String productSkuCode);
	
	String buildProductSkuEditUrl(String productSkuCode);

	String buildProductSkuFormPostUrl();

	String buildPromotionUrl();
	
	String buildShippingUrl();

	String buildOrderListUrl();

	String buildCustomerListUrl();

	String buildFaqUrl();
	
	String buildForbiddenUrl();
	
	String buildChangeLanguageUrl(Localization localization);
	
	String buildChangeContextUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildSpringSecurityCheckUrl();
	
	String buildForgottenPasswordUrl();
	
	String buildSearchUrl();
}
