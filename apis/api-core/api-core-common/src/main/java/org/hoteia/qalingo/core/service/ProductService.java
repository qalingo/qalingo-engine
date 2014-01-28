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
    
    ProductMarketing getProductMarketingById(Long productMarketingId);

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

    // PRODUCT MARKETING COMMENT/RATE
    
    void saveOrUpdateProductMarketingCustomerRate(ProductMarketingCustomerRate productMarketingCustomerRate);
    
    void deleteProductMarketingCustomerRate(ProductMarketingCustomerRate productMarketingCustomerRate);
    
    void saveOrUpdateProductMarketingCustomerComment(ProductMarketingCustomerComment productMarketingCustomerComment);
    
    void deleteProductMarketingCustomerComment(ProductMarketingCustomerComment productMarketingCustomerComment);
    
    CustomerProductRatesViewBean getProductMarketingCustomerRateDetails(Long productMarketingId);
    
    CustomerProductRatesViewBean getAVGProductMarketingCustomerRate(Long productMarketingId);
    
    CustomerProductRatesViewBean calculateProductMarketingCustomerRatesByProductCode(Long productMarketingId);
    
	// PRODUCT MARKETING ASSET
	
    Asset getProductMarketingAssetById(Long assetId);
    
	Asset getProductMarketingAssetById(String assetId);

	Asset getProductMarketingAssetByCode(String assetCode);

	void saveOrUpdateProductMarketingAsset(Asset productMarketingAsset);
	
	void deleteProductMarketingAsset(Asset productMarketingAsset);

    // PRODUCT SKU
    
    ProductSku getProductSkuById(Long productSkuId);
    
    ProductSku getProductSkuById(String productSkuId);
    
    ProductSku getProductSkuByCode(String productSkuCode);
    
    List<ProductSku> findProductSkusByproductMarketingId(Long marketAreaId, Long productMarketingId);
    
    List<ProductSku> findProductSkus(Long marketAreaId, String text);

    void saveOrUpdateProductSku(ProductSku productSku);
    
    void deleteProductSku(ProductSku productSku);
    
    // PRODUCT SKU ASSET
    
    Asset getProductSkuAssetById(Long assetId);
    
    Asset getProductSkuAssetById(String assetId);

    Asset getProductSkuAssetByCode(String assetCode);

    void saveOrUpdateProductSkuAsset(Asset productSkuAsset);
    
    void deleteProductSkuAsset(Asset productSkuAsset);
    
    // PRODUCT BRAND
    
    ProductBrand getProductBrandById(Long productBrandId);

    ProductBrand getProductBrandById(String productBrandId);

    ProductBrand getProductBrandByCode(Long marketAreaId, String productBrandCode);

    void saveOrUpdateProductBrand(ProductBrand productBrand);

    void deleteProductBrand(ProductBrand productBrand);

}