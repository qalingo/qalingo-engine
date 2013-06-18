/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.service;

import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.Retailer;

public interface BackofficeUrlService {

	// COMMON
	
	String buildHomeUrl();
	
	String buildLoginUrl();
	
	String buildLogoutUrl();
	
	String buildUserDetailsUrl();

	String buildUserDetailsUrl(String userId);
	
	String buildUserEditUrl();

	String buildUserEditUrl(String userId);
	
	String buildUserFormPostUrl();
	
	String buildUserListUrl();
	
	String buildFaqUrl();
	
	String buildForbiddenUrl();
	
	// REPORTING
	
	String buildCatalogStatsUrl();
	
	String buildPromotionStatsUrl();
	
	String buildShippingStatsUrl();
	
	String buildOrderStatsUrl();
	
	String buildCustomerStatsUrl();
	
	String buildReportingUrl();
	
	String buildChangeLanguageUrl(Localization localization);
	
	String buildChangeContextUrl(MarketPlace marketPlace, Market market, MarketArea marketArea, Localization localization, Retailer retailer);

	String buildSpringSecurityCheckUrl();
	
	String buildForgottenPasswordUrl();
	
	String buildGlobalSearchUrl();
	
	// BUSINESS
	
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

	String buildAssetDetailsUrl(String assetCode);
	
	String buildAssetEditUrl(String assetCode);

	String buildAssetFormPostUrl();
	
	String buildRuleListUrl();
	
	String buildRuleDetailsUrl(String promotionCode);
	
	String buildRuleEditUrl(String promotionCode);
	
	String buildShippingListUrl();
	
	String buildShippingDetailsUrl(String shippingCode);
	
	String buildShippingEditUrl(String shippingCode);
	
	String buildOrderListUrl();
	
	String buildOrderDetailsUrl(String orderNum);
	
	String buildOrderEditUrl(String orderNum);
	
	String buildCustomerListUrl();
	
	String buildCustomerDetailsUrl(String customerCode);
	
	String buildCustomerEditUrl(String customerCode);

	// TECHNICAL
	
	String buildReferenceDataListUrl();
	
	String buildEngineSettingListUrl();
	
	String buildEngineSettingValueEditUrl();

	String buildEngineSettingValueEditUrl(String engineSettingValueId);
	
	String buildCacheUrl();
	
	String buildBatchUrl();
	
	String buildBatchCustomerUrl();
	
	String buildBatchOrderUrl();
	
	String buildBatchEmailUrl();
	
	String buildBatchCmsUrl();
	
	String buildBatchStockUrl();
	
	String buildMonitoringUrl();
	
	String buildSearchConfigUrl();
	
	String buildSearchUrl();
	
	String buildSearchEngineSettingUrl();
	
	String buildSearchUserUrl();
	
	String buildSearchBatchUrl();
	
	String buildEngineSettingDetailsUrl(String engineSettingId);
}
