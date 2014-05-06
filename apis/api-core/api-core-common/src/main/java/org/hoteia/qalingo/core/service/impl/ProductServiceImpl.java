/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.dao.ProductDao;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtualProductSkuRel;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerComment;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerRate;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerProductRatesViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    // PRODUCT MARKETING

    public ProductMarketing getProductMarketingById(final Long productMarketingId, Object... params) {
        return productDao.getProductMarketingById(productMarketingId, params);
    }

    public ProductMarketing getProductMarketingById(final String rawProductMarketingId, Object... params) {
        long productMarketingId = -1;
        try {
            productMarketingId = Long.parseLong(rawProductMarketingId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getProductMarketingById(productMarketingId, params);
    }

    public ProductMarketing getProductMarketingByCode(final String productMarketingCode, Object... params) {
        return productDao.getProductMarketingByCode(productMarketingCode, params);
    }
    
    public CatalogCategoryVirtual getDefaultVirtualCatalogCategory(final ProductMarketing productMarketing, final List<CatalogCategoryVirtual> catalogCategories, boolean withFallback) {
        return getDefaultVirtualCatalogCategory(productMarketing.getDefaultProductSku(), catalogCategories, withFallback);
    }

    public CatalogCategoryVirtual getDefaultVirtualCatalogCategory(final ProductSku productSku, final List<CatalogCategoryVirtual> catalogCategories, boolean withFallback) {
        if(catalogCategories != null){
            for (Iterator<CatalogCategoryVirtual> iteratorCatalogCategoryVirtual = catalogCategories.iterator(); iteratorCatalogCategoryVirtual.hasNext();) {
                CatalogCategoryVirtual catalogCategoryVirtual = (CatalogCategoryVirtual) iteratorCatalogCategoryVirtual.next();
                for (Iterator<CatalogCategoryVirtualProductSkuRel> iteratorCatalogCategoryProductSkuRel = catalogCategoryVirtual.getCatalogCategoryProductSkuRels().iterator(); iteratorCatalogCategoryProductSkuRel.hasNext();) {
                    CatalogCategoryVirtualProductSkuRel catalogCategoryVirtualProductSkuRel = (CatalogCategoryVirtualProductSkuRel) iteratorCatalogCategoryProductSkuRel.next();
                    if(productSku.getCode().equals(catalogCategoryVirtualProductSkuRel.getProductSku().getCode()) 
                            && catalogCategoryVirtualProductSkuRel.isDefaultCategory()){
                        return catalogCategoryVirtual;
                    }
                }
            }
            if(withFallback
                    && catalogCategories.size() > 0){
                return catalogCategories.iterator().next();
            }
        }
        return null;
    }
    
    public List<ProductMarketing> findProductMarketings(Object... params) {
        List<ProductMarketing> productMarketings = productDao.findProductMarketings(params);
        return productMarketings;
    }

    public List<ProductMarketing> findProductMarketings(final String text, Object... params) {
        List<ProductMarketing> productMarketings = productDao.findProductMarketings(text, params);
        return productMarketings;
    }

    public List<ProductMarketing> findProductMarketingsByBrandId(final Long brandId, Object... params) {
        List<ProductMarketing> productMarketings = productDao.findProductMarketingsByBrandId(brandId, params);
        return productMarketings;
    }

    public List<ProductMarketing> findProductMarketingsByBrandCode(final String brandCode, Object... params) {
        List<ProductMarketing> productMarketings = productDao.findProductMarketingsByBrandCode(brandCode, params);
        return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingsNotInThisMasterCatalogCategoryId(final Long categoryId, Object... params){
        List<ProductMarketing> productMarketings = productDao.findProductMarketingsNotInThisMasterCatalogCategoryId(categoryId, params);
        return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingsByMasterCatalogCategoryId(final Long categoryId, Object... params){
        List<ProductMarketing> productMarketings = productDao.findProductMarketingsByMasterCatalogCategoryId(categoryId, params);
        return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingsNotInThisVirtualCatalogCategoryId(final Long categoryId, Object... params){
        List<ProductMarketing> productMarketings = productDao.findProductMarketingsNotInThisVirtualCatalogCategoryId(categoryId, params);
        return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingsByVirtualCatalogCategoryId(final Long categoryId, Object... params){
    	List<ProductMarketing> productMarketings = productDao.findProductMarketingsByVirtualCatalogCategoryId(categoryId, params);
    	return productMarketings;
    }

    public ProductMarketing saveOrUpdateProductMarketing(final ProductMarketing productMarketing) {
        return productDao.saveOrUpdateProductMarketing(productMarketing);
    }

    public void deleteProductMarketing(final ProductMarketing productMarketing) {
        productDao.deleteProductMarketing(productMarketing);
    }

    // PRODUCT MARKETING COMMENT/RATE
    
    public ProductMarketingCustomerRate saveOrUpdateProductMarketingCustomerRate(final ProductMarketingCustomerRate productMarketingCustomerRate) {
        return productDao.saveOrUpdateProductMarketingCustomerRate(productMarketingCustomerRate);
    }

    public void deleteProductMarketingCustomerRate(final ProductMarketingCustomerRate productMarketingCustomerRate) {
        productDao.deleteProductMarketingCustomerRate(productMarketingCustomerRate);
    }
    
    public ProductMarketingCustomerComment saveOrUpdateProductMarketingCustomerComment(final ProductMarketingCustomerComment productMarketingCustomerRate) {
        return productDao.saveOrUpdateProductMarketingCustomerComment(productMarketingCustomerRate);
    }

    public void deleteProductMarketingCustomerComment(final ProductMarketingCustomerComment productMarketingCustomerRate) {
        productDao.deleteProductMarketingCustomerComment(productMarketingCustomerRate);
    }
    
    //TODO: Denis: should cache?
    public CustomerProductRatesViewBean getProductMarketingCustomerRateDetails(final Long productMarketingId, Object... params){
    	List<ProductMarketingCustomerRate> qualityRates = productDao.findProductMarketingCustomerRatesByProductCode(productMarketingId, Constants.PRODUCT_QUALITY_RATING_TYPE);
    	List<ProductMarketingCustomerRate> priceRates = productDao.findProductMarketingCustomerRatesByProductCode(productMarketingId, Constants.PRODUCT_PRICE_RATING_TYPE);
    	List<ProductMarketingCustomerRate> valueRates = productDao.findProductMarketingCustomerRatesByProductCode(productMarketingId, Constants.PRODUCT_VALUE_RATING_TYPE);
    	
    	Float avgQualityRates = 0F;
    	Float avgPriceRates = 0F;
    	Float avgValueRates = 0F;
    	Float avgRate = 0F;
    	
    	for (ProductMarketingCustomerRate productMarketingCustomerRate : qualityRates) {
    		avgQualityRates += productMarketingCustomerRate.getRate();    		
		}
    	
    	for (ProductMarketingCustomerRate productMarketingCustomerRate : priceRates) {
			avgPriceRates += productMarketingCustomerRate.getRate();
		}
    	
    	for (ProductMarketingCustomerRate productMarketingCustomerRate : valueRates) {
			avgValueRates += productMarketingCustomerRate.getRate();
		}
    	
    	if(qualityRates.size() > 0){
    		avgQualityRates = avgQualityRates/qualityRates.size();
    	}
    	
    	if(priceRates.size() > 0){
    		avgPriceRates = avgPriceRates/priceRates.size();
    	}
    	
    	if(valueRates.size() > 0){
    		avgValueRates = avgValueRates/valueRates.size();
    	}
    	
    	avgRate = (avgQualityRates + avgPriceRates + avgValueRates) / 3;
    	
    	CustomerProductRatesViewBean customerProductRatesViewBean = new CustomerProductRatesViewBean();
    	customerProductRatesViewBean.setAvgPriceRates(avgPriceRates);
    	customerProductRatesViewBean.setAvgQualityRates(avgQualityRates);
    	customerProductRatesViewBean.setAvgValueRates(avgValueRates);
    	
    	customerProductRatesViewBean.setPriceRateCount(priceRates.size());
    	customerProductRatesViewBean.setQualityRateCount(qualityRates.size());
    	customerProductRatesViewBean.setValueRateCount(valueRates.size());
    	customerProductRatesViewBean.setAvgRate(avgRate);
    	
    	return customerProductRatesViewBean;
    }
    
    //TODO: Denis: should cache?
    public CustomerProductRatesViewBean calculateProductMarketingCustomerRatesByProductId(final Long productMarketingId) {
    	Float avgRate = productDao.calculateProductMarketingCustomerRatesByProductId(productMarketingId);
    	CustomerProductRatesViewBean customerProductRatesViewBean = new CustomerProductRatesViewBean();
    	customerProductRatesViewBean.setAvgRate(avgRate);
    	return customerProductRatesViewBean;
    }

    // PRODUCT MARKETING ASSET

    public Asset getProductMarketingAssetById(final Long assetId, Object... params) {
        return productDao.getProductMarketingAssetById(assetId, params);
    }

    public Asset getProductMarketingAssetById(final String rawAssetId, Object... params) {
        long assetId = -1;
        try {
            assetId = Long.parseLong(rawAssetId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getProductMarketingAssetById(assetId, params);
    }

    public Asset saveOrUpdateProductMarketingAsset(final Asset productMarketingAsset) {
        return productDao.saveOrUpdateProductMarketingAsset(productMarketingAsset);
    }

    public void deleteProductMarketingAsset(final Asset productMarketingAsset) {
        productDao.deleteProductMarketingAsset(productMarketingAsset);
    }

    // PRODUCT SKU

    public ProductSku getProductSkuById(final Long productSkuId, Object... params) {
        return productDao.getProductSkuById(productSkuId, params);
    }

    public ProductSku getProductSkuById(final String rawProductSkuId, Object... params) {
        long productSkuId = -1;
        try {
            productSkuId = Long.parseLong(rawProductSkuId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getProductSkuById(productSkuId, params);
    }

    public ProductSku getProductSkuByCode(final String skuCode, Object... params) {
        return productDao.getProductSkuByCode(skuCode, params);
    }

    public List<ProductSku> findProductSkusByProductMarketingId(final Long productMarketing, Object... params) {
        List<ProductSku> skus = productDao.findProductSkusByproductMarketingId(productMarketing, params);
        return skus;
    }

    public List<ProductSku> findProductSkus(final String text, Object... params) {
        List<ProductSku> skus = productDao.findProductSkus(text, params);
        return skus;
    }

    public List<ProductSku> findProductSkusByMasterCatalogCategoryId(Long categoryId, Object... params) {
        List<ProductSku> skus = productDao.findProductSkusByMasterCatalogCategoryId(categoryId, params);
        return skus;
    }
    
    public List<ProductSku> findProductSkusNotInThisMasterCatalogCategoryId(Long categoryId, Object... params) {
        List<ProductSku> skus = productDao.findProductSkusNotInThisMasterCatalogCategoryId(categoryId, params);
        return skus;
    }
    
    public List<ProductSku> findProductSkusByVirtualCatalogCategoryId(Long categoryId, Object... params) {
        List<ProductSku> skus = productDao.findProductSkusByVirtualCatalogCategoryId(categoryId, params);
        return skus;
    }
    
    public List<ProductSku> findProductSkusNotInThisVirtualCatalogCategoryId(Long categoryId, Object... params) {
        List<ProductSku> skus = productDao.findProductSkusNotInThisVirtualCatalogCategoryId(categoryId, params);
        return skus;
    }

    public List<Long> getProductIds(List<ProductSku> productSkus) {
        List<Long> productSkuIds = new ArrayList<Long>();
        for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
            ProductSku productSku = (ProductSku) iterator.next();
            productSkuIds.add(productSku.getId());
        }
        return productSkuIds;
    }

    public ProductSku saveOrUpdateProductSku(final ProductSku productSku) {
        return productDao.saveOrUpdateProductSku(productSku);
    }

    public void deleteProductSku(final ProductSku productSku) {
        productDao.deleteProductSku(productSku);
    }

    // PRODUCT SKU ASSET

    public Asset getProductSkuAssetById(final Long assetId, Object... params) {
        return productDao.getProductSkuAssetById(assetId, params);
    }

    public Asset getProductSkuAssetById(final String rawAssetId, Object... params) {
        long assetId = -1;
        try {
            assetId = Long.parseLong(rawAssetId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getProductSkuAssetById(assetId);
    }

    public Asset saveOrUpdateProductSkuAsset(final Asset productSkuAsset) {
        return productDao.saveOrUpdateProductSkuAsset(productSkuAsset);
    }

    public void deleteProductSkuAsset(final Asset productSkuAsset) {
        productDao.deleteProductSkuAsset(productSkuAsset);
    }
    
    // PRODUCT BRAND

    public ProductBrand getProductBrandById(final Long productBrandId, Object... params) {
        return productDao.getProductBrandById(productBrandId, params);
    }

    public ProductBrand getProductBrandById(final String rawProductBrandId, Object... params) {
        long productBrandId = -1;
        try {
            productBrandId = Long.parseLong(rawProductBrandId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getProductBrandById(productBrandId, params);
    }

    public ProductBrand getProductBrandByCode(final Long marketAreaId, final String productBrandCode, Object... params) {
        return productDao.getProductBrandByCode(marketAreaId, productBrandCode, params);
    }
    
    public List<ProductBrand> findProductBrandsByCatalogCategoryCode(final String categoryCode, Object... params) {
        return productDao.findProductBrandsByCatalogCategoryCode(categoryCode, params);
    }

    public ProductBrand saveOrUpdateProductBrand(final ProductBrand productBrand) {
        return productDao.saveOrUpdateProductBrand(productBrand);
    }

    public void deleteProductBrand(final ProductBrand productBrand) {
        productDao.deleteProductBrand(productBrand);
    }

}