/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerComment;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerRate;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerProductRatesViewBean;

public interface ProductService {

    // PRODUCT MARKETING
    
    ProductMarketing getProductMarketingById(Long productMarketingId, Object... params);

    ProductMarketing getProductMarketingById(String productMarketingId, Object... params);

	ProductMarketing getProductMarketingByCode(String productMarketingCode, Object... params);
	
	CatalogCategoryVirtual getDefaultVirtualCatalogCategory(ProductMarketing productMarketing, List<CatalogCategoryVirtual> catalogCategories, boolean withFallback);
	
	CatalogCategoryVirtual getDefaultVirtualCatalogCategory(ProductSku productSku, List<CatalogCategoryVirtual> catalogCategories, boolean withFallback);
	
	List<ProductMarketing> findProductMarketings(Object... params);
	
	List<ProductMarketing> findProductMarketings(String text, Object... params);

    List<ProductMarketing> findProductMarketingsByBrandId(Long brandId, Object... params);

    List<ProductMarketing> findProductMarketingsByBrandCode(String brandCode, Object... params);
    
    List<ProductMarketing> findProductMarketingsByVirtualCatalogCategoryId(Long categoryId, Object... params);
    
    List<ProductMarketing> findProductMarketingsNotInThisVirtualCatalogCategoryId(Long categoryId, Object... params);
    
    ProductMarketing saveOrUpdateProductMarketing(ProductMarketing productMarketing);
	
	void deleteProductMarketing(ProductMarketing productMarketing);

    // PRODUCT MARKETING COMMENT/RATE
    
    CustomerProductRatesViewBean getProductMarketingCustomerRateDetails(Long productMarketingId, Object... params);
    
    CustomerProductRatesViewBean calculateProductMarketingCustomerRatesByProductId(Long productMarketingId);
    
    ProductMarketingCustomerRate saveOrUpdateProductMarketingCustomerRate(ProductMarketingCustomerRate productMarketingCustomerRate);
    
    void deleteProductMarketingCustomerRate(ProductMarketingCustomerRate productMarketingCustomerRate);
    
    ProductMarketingCustomerComment saveOrUpdateProductMarketingCustomerComment(ProductMarketingCustomerComment productMarketingCustomerComment);
    
    void deleteProductMarketingCustomerComment(ProductMarketingCustomerComment productMarketingCustomerComment);
    
	// PRODUCT MARKETING ASSET
	
    Asset getProductMarketingAssetById(Long assetId, Object... params);
    
	Asset getProductMarketingAssetById(String assetId, Object... params);

	Asset saveOrUpdateProductMarketingAsset(Asset productMarketingAsset);
	
	void deleteProductMarketingAsset(Asset productMarketingAsset);

    // PRODUCT SKU
    
    ProductSku getProductSkuById(Long productSkuId, Object... params);
    
    ProductSku getProductSkuById(String productSkuId, Object... params);
    
    ProductSku getProductSkuByCode(String skuCode, Object... params);
    
    List<ProductSku> findProductSkusByProductMarketingId(Long productMarketingId, Object... params);
    
    List<ProductSku> findProductSkus(String text, Object... params);

    List<ProductSku> findProductSkusByMasterCatalogCategoryId(Long categoryId, Object... params);

    List<ProductSku> findProductSkusNotInThisMasterCatalogCategoryId(Long categoryId, Object... params);

    List<ProductSku> findProductSkusByVirtualCatalogCategoryId(Long categoryId, Object... params);
    
    List<ProductSku> findProductSkusNotInThisVirtualCatalogCategoryId(Long categoryId, Object... params);
    
    List<Long> getProductIds(List<ProductSku> productSkus);
    
    ProductSku saveOrUpdateProductSku(ProductSku productSku);
    
    void deleteProductSku(ProductSku productSku);
    
    // PRODUCT SKU ASSET
    
    Asset getProductSkuAssetById(Long assetId, Object... params);
    
    Asset getProductSkuAssetById(String assetId, Object... params);

    Asset saveOrUpdateProductSkuAsset(Asset productSkuAsset);
    
    void deleteProductSkuAsset(Asset productSkuAsset);
    
    // PRODUCT BRAND
    
    ProductBrand getProductBrandById(Long productBrandId, Object... params);

    ProductBrand getProductBrandById(String productBrandId, Object... params);

    ProductBrand getProductBrandByCode(Long marketAreaId, String productBrandCode, Object... params);

    List<ProductBrand> findProductBrandsByCatalogCategoryCode(String categoryCode, Object... params);

    ProductBrand saveOrUpdateProductBrand(ProductBrand productBrand);

    void deleteProductBrand(ProductBrand productBrand);

}