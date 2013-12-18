/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;

public interface ProductService {

    // PRODUCT MARKETING
    
	ProductMarketing getProductMarketingById(String productMarketingId);

	ProductMarketing getProductMarketingByCode(String productMarketingCode);
	
	List<ProductMarketing> findProductMarketings(Long marketAreaId);
	
	List<ProductMarketing> findProductMarketings(Long marketAreaId, String text);

    List<ProductMarketing> findProductMarketingsByBrandId(Long marketAreaId, Long brandId);

    List<ProductMarketing> findProductMarketingsByBrandCode(Long marketAreaId, String brandCode);
    
    List<ProductMarketing> findProductMarketingsByCatalogCategoryCode(Long marketAreaId,String categoryCode);
    
    List<ProductBrand> findProductBrandsByCatalogCategoryCode(String categoryCode);
    
	void saveOrUpdateProductMarketing(ProductMarketing productMarketing);
	
	void deleteProductMarketing(ProductMarketing productMarketing);

	// PRODUCT MARKETING ASSET
	
	Asset getProductMarketingAssetById(String productMarketingId);

	Asset getProductMarketingAssetByCode(String assetCode);

	void saveOrUpdateProductMarketingAsset(Asset productMarketingAsset);
	
	void deleteProductMarketingAsset(Asset productMarketingAsset);

    // PRODUCT SKU
    
    ProductSku getProductSkuById(String productSkuId);
    
    ProductSku getProductSkuByCode(String productSkuCode);
    
    List<ProductSku> findProductSkusByproductMarketingId(Long marketAreaId, Long productMarketingId);
    
    List<ProductSku> findProductSkus(Long marketAreaId, String text);

    void saveOrUpdateProductSku(ProductSku productSku);
    
    void deleteProductSku(ProductSku productSku);
    
    // PRODUCT SKU ASSET
    
    Asset getProductSkuAssetById(String productSkuId);

    Asset getProductSkuAssetByCode(String assetCode);

    void saveOrUpdateProductSkuAsset(Asset productSkuAsset);
    
    void deleteProductSkuAsset(Asset productSkuAsset);
    
}