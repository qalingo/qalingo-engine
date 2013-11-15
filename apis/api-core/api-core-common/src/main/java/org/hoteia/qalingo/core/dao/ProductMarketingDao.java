/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.List;

import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.Asset;

public interface ProductMarketingDao {

	ProductMarketing getProductMarketingById(Long productMarketingId);
	
	ProductMarketing getProductMarketingByCode(Long marketAreaId, Long retailerId, String productMarketingCode);

	List<ProductMarketing> findProductMarketings(Long marketAreaId, Long retailerId);

	List<ProductMarketing> findProductMarketings(Long marketAreaId, Long retailerId, String text);

	void saveOrUpdateProductMarketing(ProductMarketing productMarketing);

	void deleteProductMarketing(ProductMarketing productMarketing);

	// ASSET
	Asset getProductMarketingAssetById(Long productMarketingId);

	Asset getProductMarketingAssetByCode(String assetCode);

	void saveOrUpdateProductMarketingAsset(Asset productMarketingAsset);
	
	void deleteProductMarketingAsset(Asset productMarketingAsset);
}
