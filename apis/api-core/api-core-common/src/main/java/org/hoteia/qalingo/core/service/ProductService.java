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
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerComment;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerRate;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerProductRatesViewBean;

public interface ProductService {

    // PRODUCT MARKETING
    
    ProductMarketing getProductMarketingById(Long productMarketingId, Object... params);

    ProductMarketing getProductMarketingById(String productMarketingId, Object... params);

	ProductMarketing getProductMarketingByCode(String productMarketingCode, Object... params);
	
	List<ProductMarketing> findProductMarketings(Long marketAreaId, Object... params);
	
	List<ProductMarketing> findProductMarketings(Long marketAreaId, String text, Object... params);

    List<ProductMarketing> findProductMarketingsByBrandId(Long marketAreaId, Long brandId, Object... params);

    List<ProductMarketing> findProductMarketingsByBrandCode(Long marketAreaId, String brandCode, Object... params);
    
    List<ProductMarketing> findProductMarketingsByCatalogCategoryCode(Long marketAreaId,String categoryCode, Object... params);
    
    List<ProductBrand> findProductBrandsByCatalogCategoryCode(String categoryCode, Object... params);
    
	void saveOrUpdateProductMarketing(ProductMarketing productMarketing);
	
	void deleteProductMarketing(ProductMarketing productMarketing);

    // PRODUCT MARKETING COMMENT/RATE
    
    CustomerProductRatesViewBean getProductMarketingCustomerRateDetails(Long productMarketingId, Object... params);
    
    CustomerProductRatesViewBean calculateProductMarketingCustomerRatesByProductCode(Long productMarketingId);
    
    void saveOrUpdateProductMarketingCustomerRate(ProductMarketingCustomerRate productMarketingCustomerRate);
    
    void deleteProductMarketingCustomerRate(ProductMarketingCustomerRate productMarketingCustomerRate);
    
    void saveOrUpdateProductMarketingCustomerComment(ProductMarketingCustomerComment productMarketingCustomerComment);
    
    void deleteProductMarketingCustomerComment(ProductMarketingCustomerComment productMarketingCustomerComment);
    
	// PRODUCT MARKETING ASSET
	
    Asset getProductMarketingAssetById(Long assetId, Object... params);
    
	Asset getProductMarketingAssetById(String assetId, Object... params);

	Asset getProductMarketingAssetByCode(String assetCode, Object... params);

	void saveOrUpdateProductMarketingAsset(Asset productMarketingAsset);
	
	void deleteProductMarketingAsset(Asset productMarketingAsset);

    // PRODUCT SKU
    
    ProductSku getProductSkuById(Long productSkuId, Object... params);
    
    ProductSku getProductSkuById(String productSkuId, Object... params);
    
    ProductSku getProductSkuByCode(String skuCode, Object... params);
    
    List<ProductSku> findProductSkusByproductMarketingId(Long marketAreaId, Long productMarketingId, Object... params);
    
    List<ProductSku> findProductSkus(Long marketAreaId, String text, Object... params);

    void saveOrUpdateProductSku(ProductSku productSku);
    
    void deleteProductSku(ProductSku productSku);
    
    // PRODUCT SKU ASSET
    
    Asset getProductSkuAssetById(Long assetId, Object... params);
    
    Asset getProductSkuAssetById(String assetId, Object... params);

    Asset getProductSkuAssetByCode(String assetCode, Object... params);

    void saveOrUpdateProductSkuAsset(Asset productSkuAsset);
    
    void deleteProductSkuAsset(Asset productSkuAsset);
    
    // PRODUCT BRAND
    
    ProductBrand getProductBrandById(Long productBrandId, Object... params);

    ProductBrand getProductBrandById(String productBrandId, Object... params);

    ProductBrand getProductBrandByCode(Long marketAreaId, String productBrandCode, Object... params);

    void saveOrUpdateProductBrand(ProductBrand productBrand);

    void deleteProductBrand(ProductBrand productBrand);

}