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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.ProductDao;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.service.ProductService;

@Service("productMarketingService")
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productMarketingDao;

    // PRODUCT MARKETING

    public ProductMarketing getProductMarketingById(final String rawProductMarketingId) {
        long productMarketingId = -1;
        try {
            productMarketingId = Long.parseLong(rawProductMarketingId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return productMarketingDao.getProductMarketingById(productMarketingId);
    }

    public ProductMarketing getProductMarketingByCode(final String productMarketingCode) {
        return productMarketingDao.getProductMarketingByCode(productMarketingCode);
    }

    public List<ProductMarketing> findProductMarketings(final Long marketAreaId) {
        List<ProductMarketing> productMarketings = productMarketingDao.findProductMarketings();
        return orderProductMarketingList(marketAreaId, productMarketings);
    }

    public List<ProductMarketing> findProductMarketings(final Long marketAreaId, final String text) {
        List<ProductMarketing> productMarketings = productMarketingDao.findProductMarketings(text);
        return orderProductMarketingList(marketAreaId, productMarketings);
    }

    public List<ProductMarketing> findProductMarketingsByBrandId(final Long marketAreaId, final Long brandId) {
        List<ProductMarketing> productMarketings = productMarketingDao.findProductMarketingsByBrandId(brandId);
        return orderProductMarketingList(marketAreaId, productMarketings);
    }

    public List<ProductMarketing> findProductMarketingsByBrandCode(final Long marketAreaId, final String brandCode) {
        List<ProductMarketing> productMarketings = productMarketingDao.findProductMarketingsByBrandCode(brandCode);
        return orderProductMarketingList(marketAreaId, productMarketings);
    }

    public void saveOrUpdateProductMarketing(ProductMarketing productMarketing) {
        productMarketingDao.saveOrUpdateProductMarketing(productMarketing);
    }

    public void deleteProductMarketing(ProductMarketing productMarketing) {
        productMarketingDao.deleteProductMarketing(productMarketing);
    }

    protected List<ProductMarketing> orderProductMarketingList(final Long marketAreaId, final List<ProductMarketing> productMarketings) {
        if (productMarketings != null) {
            List<ProductMarketing> sortedObjects = new LinkedList<ProductMarketing>(productMarketings);
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
            return sortedObjects;
        }
        return null;
    }

    // PRODUCT MARKETING ASSET

    public Asset getProductMarketingAssetById(final String rawProductMarketingAssetId) {
        long productMarketingId = -1;
        try {
            productMarketingId = Long.parseLong(rawProductMarketingAssetId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return productMarketingDao.getProductMarketingAssetById(productMarketingId);
    }

    public Asset getProductMarketingAssetByCode(String assetCode) {
        return productMarketingDao.getProductMarketingAssetByCode(assetCode);
    }

    public void saveOrUpdateProductMarketingAsset(Asset productMarketingAsset) {
        productMarketingDao.saveOrUpdateProductMarketingAsset(productMarketingAsset);
    }

    public void deleteProductMarketingAsset(Asset productMarketingAsset) {
        productMarketingDao.deleteProductMarketingAsset(productMarketingAsset);
    }

    // PRODUCT SKU

    public ProductSku getProductSkuById(final String rawProductSkuId) {
        long productSkuId = -1;
        try {
            productSkuId = Long.parseLong(rawProductSkuId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return productMarketingDao.getProductSkuById(productSkuId);
    }

    public ProductSku getProductSkuByCode(final String productSkuCode) {
        return productMarketingDao.getProductSkuByCode(productSkuCode);
    }

    public List<ProductSku> findProductSkusByproductMarketingId(final Long marketAreaId, final Long productMarketing) {
        List<ProductSku> skus = productMarketingDao.findProductSkusByproductMarketingId(productMarketing);
        return orderProductSkuList(marketAreaId, skus);
    }

    public List<ProductSku> findProductSkus(final Long marketAreaId, final String text) {
        List<ProductSku> skus = productMarketingDao.findProductSkus(text);
        return orderProductSkuList(marketAreaId, skus);
    }

    public void saveOrUpdateProductSku(ProductSku productSku) {
        productMarketingDao.saveOrUpdateProductSku(productSku);
    }

    public void deleteProductSku(ProductSku productSku) {
        productMarketingDao.deleteProductSku(productSku);
    }

    protected List<ProductSku> orderProductSkuList(final Long marketAreaId, final List<ProductSku> skus) {
        List<ProductSku> sortedObjects = new LinkedList<ProductSku>(skus);
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
        return sortedObjects;
    }

    // PRODUCT SKU ASSET

    public Asset getProductSkuAssetById(final String rawProductSkuAssetId) {
        long productSkuId = -1;
        try {
            productSkuId = Long.parseLong(rawProductSkuAssetId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return productMarketingDao.getProductSkuAssetById(productSkuId);
    }

    public Asset getProductSkuAssetByCode(String assetCode) {
        return productMarketingDao.getProductSkuAssetByCode(assetCode);
    }

    public void saveOrUpdateProductSkuAsset(Asset productSkuAsset) {
        productMarketingDao.saveOrUpdateProductSkuAsset(productSkuAsset);
    }

    public void deleteProductSkuAsset(Asset productSkuAsset) {
        productMarketingDao.deleteProductSkuAsset(productSkuAsset);
    }

}