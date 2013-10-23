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
import fr.hoteia.qalingo.core.domain.enumtype.BoUrls;
import fr.hoteia.qalingo.core.pojo.RequestData;

public interface BackofficeUrlService {

	String buildProductMasterCategoryDetailsUrl(String productCategoryCode) throws Exception;

	String buildProductVirtualCategoryDetailsUrl(String productCategoryCode) throws Exception;

	String buildAddMasterProductCategoryUrl(String productCategoryCode) throws Exception;

	String buildAddVirtualProductCategoryUrl(String productCategoryCode) throws Exception;
	
	String buildMasterProductCategoryEditUrl(String productCategoryCode) throws Exception;

	String buildVirtualProductCategoryEditUrl(String productCategoryCode) throws Exception;

	String buildProductMarketingDetailsUrl(String productMarketingCode) throws Exception;
	
	String buildProductMarketingEditUrl(String productMarketingCode) throws Exception;

	String buildProductSkuDetailsUrl(String productSkuCode) throws Exception;
	
	String buildProductSkuEditUrl(String productSkuCode) throws Exception;

	String buildAssetDetailsUrl(String assetCode) throws Exception;
	
	String buildAssetEditUrl(String assetCode) throws Exception;

	String buildRuleDetailsUrl(String promotionCode) throws Exception;
	
	String buildRuleEditUrl(String promotionCode) throws Exception;
	
	String buildShippingDetailsUrl(String shippingCode) throws Exception;
	
	String buildShippingEditUrl(String shippingCode) throws Exception;
	
	String buildOrderDetailsUrl(String orderNum) throws Exception;
	
	String buildOrderEditUrl(String orderNum) throws Exception;
	
	String buildCustomerDetailsUrl(String customerCode) throws Exception;
	
	String buildCustomerEditUrl(String customerCode) throws Exception;

	// KEEP

	String buildChangeLanguageUrl(RequestData requestData) throws Exception;
	
	String buildChangeContextUrl(RequestData requestData) throws Exception;

	String generateUrl(BoUrls url, RequestData requestData);

	String generateUrl(BoUrls url, RequestData requestData, Object... params);
	
	String buildSpringSecurityCheckUrl(RequestData requestData) throws Exception;
}