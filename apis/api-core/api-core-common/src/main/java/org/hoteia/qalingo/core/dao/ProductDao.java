/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerComment;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerRate;
import org.hoteia.qalingo.core.domain.ProductSku;

public interface ProductDao {

    // PRODUCT MARKETING
    
	ProductMarketing getProductMarketingById(Long productMarketingId, Object... params);
	
	ProductMarketing getProductMarketingByCode(String productMarketingCode, Object... params);

	List<ProductMarketing> findProductMarketings(Object... params);

	List<ProductMarketing> findProductMarketings(String text, Object... params);

    List<ProductMarketing> findProductMarketingsByBrandId(Long brandId, Object... params);

    List<ProductMarketing> findProductMarketingsByBrandCode(String brandCode, Object... params);

    List<ProductMarketing> findProductMarketingsByMasterCatalogCategoryId(Long categoryId, Object... params);

    List<ProductMarketing> findProductMarketingsNotInThisMasterCatalogCategoryId(Long categoryId, Object... params);

    List<ProductMarketing> findProductMarketingsByVirtualCatalogCategoryId(Long categoryId, Object... params);

    List<ProductMarketing> findProductMarketingsNotInThisVirtualCatalogCategoryId(Long categoryId, Object... params);

    ProductMarketing saveOrUpdateProductMarketing(ProductMarketing productMarketing);

	void deleteProductMarketing(ProductMarketing productMarketing);

    // PRODUCT MARKETING COMMENT/RATE

    List<ProductMarketingCustomerRate> findProductMarketingCustomerRatesByProductCode(Long productMarketingId, String type, Object... params);
    
    Float calculateProductMarketingCustomerRatesByProductId(Long productMarketingId);
    
    List<ProductMarketingCustomerComment> findProductMarketingCustomerCommentsByProductCode(Long productMarketingId, Object... params);
    
	ProductMarketingCustomerRate saveOrUpdateProductMarketingCustomerRate(ProductMarketingCustomerRate productMarketingCustomerRate);
    
    void deleteProductMarketingCustomerRate(ProductMarketingCustomerRate productMarketingCustomerRate);
    
    ProductMarketingCustomerComment saveOrUpdateProductMarketingCustomerComment(ProductMarketingCustomerComment productMarketingCustomerComment);
    
    void deleteProductMarketingCustomerComment(ProductMarketingCustomerComment productMarketingCustomerComment);
    
	// PRODUCT MARKETING ASSET
	
	Asset getProductMarketingAssetById(Long productMarketingId, Object... params);

	Asset saveOrUpdateProductMarketingAsset(Asset productMarketingAsset);
	
	void deleteProductMarketingAsset(Asset productMarketingAsset);

    // PRODUCT SKU
	   
    ProductSku getProductSkuById(Long productSkuId, Object... params);

    ProductSku getProductSkuByCode(String skuCode, Object... params);
    
    List<ProductSku> findProductSkusByproductMarketingId(Long productMarkettingId, Object... params);
    
    List<ProductSku> findProductSkus(String text, Object... params);

    List<ProductSku> findProductSkusByMasterCatalogCategoryId(Long categoryId, Object... params);
    
    List<ProductSku> findProductSkusNotInThisMasterCatalogCategoryId(Long categoryId, Object... params);

    List<ProductSku> findProductSkusByVirtualCatalogCategoryId(Long categoryId, Object... params);
    
    List<ProductSku> findProductSkusNotInThisVirtualCatalogCategoryId(Long categoryId, Object... params);
    
    ProductSku saveOrUpdateProductSku(ProductSku productSku);

    void deleteProductSku(ProductSku productSku);

   // PRODUCT SKU ASSET
    
    Asset getProductSkuAssetById(Long productSkuId, Object... params);

    Asset saveOrUpdateProductSkuAsset(Asset productSkuAsset);
    
    void deleteProductSkuAsset(Asset productSkuAsset);
    
    // PRODUCT BRAND
    
    ProductBrand getProductBrandById(Long productBrandId, Object... params);

    ProductBrand getProductBrandByCode(Long marketAreaId, String productBrandCode, Object... params);

    List<ProductBrand> findProductBrandsByCatalogCategoryCode(String categoryCode, Object... params);

    ProductBrand saveOrUpdateProductBrand(ProductBrand productBrand);

    void deleteProductBrand(ProductBrand productBrand);

}