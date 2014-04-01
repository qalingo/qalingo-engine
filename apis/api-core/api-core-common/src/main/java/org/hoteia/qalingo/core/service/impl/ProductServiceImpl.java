/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.dao.ProductDao;
import org.hoteia.qalingo.core.domain.Asset;
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

    public List<ProductMarketing> findProductMarketings(final Long marketAreaId, Object... params) {
        List<ProductMarketing> productMarketings = productDao.findProductMarketings(params);
        return orderProductMarketingList(marketAreaId, productMarketings);
    }

    public List<ProductMarketing> findProductMarketings(final Long marketAreaId, final String text, Object... params) {
        List<ProductMarketing> productMarketings = productDao.findProductMarketings(text, params);
        return orderProductMarketingList(marketAreaId, productMarketings);
    }

    public List<ProductMarketing> findProductMarketingsByBrandId(final Long marketAreaId, final Long brandId, Object... params) {
        List<ProductMarketing> productMarketings = productDao.findProductMarketingsByBrandId(brandId, params);
        return orderProductMarketingList(marketAreaId, productMarketings);
    }

    public List<ProductMarketing> findProductMarketingsByBrandCode(final Long marketAreaId, final String brandCode, Object... params) {
        List<ProductMarketing> productMarketings = productDao.findProductMarketingsByBrandCode(brandCode, params);
        return orderProductMarketingList(marketAreaId, productMarketings);
    }
    public List<ProductMarketing> findProductMarketingsByCatalogCategoryCode(final Long marketAreaId, final String categoryCode, Object... params){
    	List<ProductMarketing> productMarketings = productDao.findProductMarketingsByCatalogCategoryCode(categoryCode, params);
    	return orderProductMarketingList(marketAreaId, productMarketings);
    }

    public void saveOrUpdateProductMarketing(final ProductMarketing productMarketing) {
        productDao.saveOrUpdateProductMarketing(productMarketing);
    }

    public void deleteProductMarketing(final ProductMarketing productMarketing) {
        productDao.deleteProductMarketing(productMarketing);
    }

    protected List<ProductMarketing> orderProductMarketingList(final Long marketAreaId, final List<ProductMarketing> productMarketings) {
        if (productMarketings != null) {
            List<ProductMarketing> sortedObjects = new LinkedList<ProductMarketing>(productMarketings);
            if (marketAreaId != null) {
                Collections.sort(sortedObjects, new Comparator<ProductMarketing>() {
                    @Override
                    public int compare(ProductMarketing o1, ProductMarketing o2) {
                        if (o1 != null && o2 != null) {
                            Integer order1 = o1.getOrder(marketAreaId);
                            Integer order2 = o2.getOrder(marketAreaId);
                            if (order1 != null && order2 != null) {
                                return order1.compareTo(order2);
                            } else {
                                return o1.getId().compareTo(o2.getId());
                            }
                        }
                        return 0;
                    }
                });
            }
            return sortedObjects;
        }
        return null;
    }
    
    // PRODUCT MARKETING COMMENT/RATE
    
    public void saveOrUpdateProductMarketingCustomerRate(final ProductMarketingCustomerRate productMarketingCustomerRate) {
        productDao.saveOrUpdateProductMarketingCustomerRate(productMarketingCustomerRate);
    }

    public void deleteProductMarketingCustomerRate(final ProductMarketingCustomerRate productMarketingCustomerRate) {
        productDao.deleteProductMarketingCustomerRate(productMarketingCustomerRate);
    }
    
    public void saveOrUpdateProductMarketingCustomerComment(final ProductMarketingCustomerComment productMarketingCustomerRate) {
        productDao.saveOrUpdateProductMarketingCustomerComment(productMarketingCustomerRate);
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
    public CustomerProductRatesViewBean calculateProductMarketingCustomerRatesByProductCode(final Long productMarketingId) {
    	Float avgRate = productDao.calculateProductMarketingCustomerRatesByProductCode(productMarketingId);
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

    public Asset getProductMarketingAssetByCode(final String assetCode, Object... params) {
        return productDao.getProductMarketingAssetByCode(assetCode, params);
    }

    public void saveOrUpdateProductMarketingAsset(final Asset productMarketingAsset) {
        productDao.saveOrUpdateProductMarketingAsset(productMarketingAsset);
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

    public List<ProductSku> findProductSkusByproductMarketingId(final Long marketAreaId, final Long productMarketing, Object... params) {
        List<ProductSku> skus = productDao.findProductSkusByproductMarketingId(productMarketing, params);
        return orderProductSkuList(marketAreaId, skus);
    }

    public List<ProductSku> findProductSkus(final Long marketAreaId, final String text, Object... params) {
        List<ProductSku> skus = productDao.findProductSkus(text, params);
        return orderProductSkuList(marketAreaId, skus);
    }

    public void saveOrUpdateProductSku(final ProductSku productSku) {
        productDao.saveOrUpdateProductSku(productSku);
    }

    public void deleteProductSku(final ProductSku productSku) {
        productDao.deleteProductSku(productSku);
    }

    protected List<ProductSku> orderProductSkuList(final Long marketAreaId, final List<ProductSku> skus) {
        if (skus != null) {
            List<ProductSku> sortedObjects = new LinkedList<ProductSku>(skus);
            if (marketAreaId != null) {
                Collections.sort(sortedObjects, new Comparator<ProductSku>() {
                    @Override
                    public int compare(ProductSku o1, ProductSku o2) {
                        if (o1 != null && o2 != null) {
                            Integer order1 = o1.getOrder(marketAreaId);
                            Integer order2 = o2.getOrder(marketAreaId);
                            if (order1 != null && order2 != null) {
                                return order1.compareTo(order2);
                            } else {
                                return o1.getId().compareTo(o2.getId());
                            }
                        }
                        return 0;
                    }
                });
            }
            return sortedObjects;
        }
        return null;
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

    public Asset getProductSkuAssetByCode(final String assetCode, Object... params) {
        return productDao.getProductSkuAssetByCode(assetCode, params);
    }

    public void saveOrUpdateProductSkuAsset(final Asset productSkuAsset) {
        productDao.saveOrUpdateProductSkuAsset(productSkuAsset);
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

    public void saveOrUpdateProductBrand(final ProductBrand productBrand) {
        productDao.saveOrUpdateProductBrand(productBrand);
    }

    public void deleteProductBrand(final ProductBrand productBrand) {
        productDao.deleteProductBrand(productBrand);
    }

}