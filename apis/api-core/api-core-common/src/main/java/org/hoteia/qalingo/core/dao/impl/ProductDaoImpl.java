/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.dao.ProductDao;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerComment;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerRate;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.catalog.FetchPlanGraphProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("productDao")
public class ProductDaoImpl extends AbstractGenericDaoImpl implements ProductDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    // PRODUCT MARKETING
	
	public ProductMarketing getProductMarketingById(final Long productMarketingId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        FetchPlan fetchPlan = handleSpecificProductMarketingFetchMode(criteria, params);
        criteria.add(Restrictions.eq("id", productMarketingId));
        ProductMarketing productMarketing = (ProductMarketing) criteria.uniqueResult();
        if(productMarketing != null){
            productMarketing.setFetchPlan(fetchPlan);
        }
        return productMarketing;
	}

	public ProductMarketing getProductMarketingByCode(final String productMarketingCode, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        FetchPlan fetchPlan = handleSpecificProductMarketingFetchMode(criteria, params);
        criteria.add(Restrictions.eq("code", productMarketingCode));
        ProductMarketing productMarketing = (ProductMarketing) criteria.uniqueResult();
        if(productMarketing != null){
            productMarketing.setFetchPlan(fetchPlan);
        }
		return productMarketing;
	}
	
	public List<ProductMarketing> findProductMarketings(Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
		return productMarketings;
	}
	
	public List<ProductMarketing> findProductMarketings(final String text, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);

        criteria.add(Restrictions.or(Restrictions.eq("code", "%" + text + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("name", "%" + text + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("description", "%" + text + "%")));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
		return productMarketings;
	}

    public List<ProductMarketing> findProductMarketingsByBrandId(final Long brandId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);

        criteria.setFetchMode("productBrand", FetchMode.JOIN);
        criteria.createAlias("productBrand", "pb", JoinType.LEFT_OUTER_JOIN);
        
        criteria.add(Restrictions.eq("pb.id", brandId));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
    }

	public List<ProductMarketing> findProductMarketingsByBrandCode(final String brandCode, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);

        criteria.setFetchMode("productBrand", FetchMode.JOIN);
        criteria.createAlias("productBrand", "pb", JoinType.LEFT_OUTER_JOIN);
        
        criteria.add(Restrictions.eq("pb.code", brandCode));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
	}
	
	public List<ProductMarketing> findProductMarketingsByCatalogCategoryCode(final String categoryCode, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);

        criteria.setFetchMode("defaultCatalogCategory", FetchMode.JOIN);
        criteria.createAlias("defaultCatalogCategory", "dc", JoinType.LEFT_OUTER_JOIN);
        
        criteria.add(Restrictions.eq("dc.code", categoryCode));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
	}
	
    public List<ProductMarketing> findProductMarketingsByCatalogCategoryCodeAndSortAndPagintion(final String categoryCode, int pageNumber, int pageSize, String sortBy, String orderBy, Object... params) {
        Criteria criteria = getSession().createCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);

        criteria.setFetchMode("defaultCatalogCategory", FetchMode.JOIN);
        criteria.createAlias("defaultCatalogCategory", "dc", JoinType.LEFT_OUTER_JOIN);

        criteria.add(Restrictions.eq("dc.code", categoryCode));
        if ("desc".equals(orderBy)) {
            criteria.addOrder(Order.desc(sortBy));
        } else {
            criteria.addOrder(Order.asc(sortBy));
        }

        criteria.setFirstResult((pageNumber - 1) * pageSize);
        criteria.setMaxResults(pageSize);

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
    }
	
	public ProductMarketing saveOrUpdateProductMarketing(final ProductMarketing productMarketing) {
		if(productMarketing.getDateCreate() == null){
			productMarketing.setDateCreate(new Date());
		}
		productMarketing.setDateUpdate(new Date());
        if (productMarketing.getId() != null) {
            if(em.contains(productMarketing)){
                em.refresh(productMarketing);
            }
            ProductMarketing mergedProductMarketing = em.merge(productMarketing);
            em.flush();
            return mergedProductMarketing;
        } else {
            em.persist(productMarketing);
            return productMarketing;
        }
	}

	public void deleteProductMarketing(final ProductMarketing productMarketing) {
		em.remove(productMarketing);
	}
	
    // PRODUCT MARKETING COMMENT/RATE
	
    @SuppressWarnings("unchecked")
    public List<ProductMarketingCustomerComment> findProductMarketingCustomerCommentsByProductCode(final Long productMarketingId, Object... params) {
        Criteria  criteria = createDefaultCriteria(ProductMarketingCustomerComment.class);
        criteria.add(Restrictions.eq("productMarketingId", productMarketingId));
        
        return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<ProductMarketingCustomerRate> findProductMarketingCustomerRatesByProductCode(final Long productMarketingId, final String type, Object... params) {
        Criteria  criteria = createDefaultCriteria(ProductMarketingCustomerRate.class);
        criteria.add(Restrictions.eq("productMarketingId", productMarketingId));
        criteria.add(Restrictions.eq("type", type));
        
        return criteria.list();
    }
    
    public Float calculateProductMarketingCustomerRatesByProductCode(final Long productMarketingId) {
        String sql = "select avg(rate) from ProductMarketingCustomerRate where productMarketingId=:productMarketingId";
        Query query = getSession().createQuery(sql);
        query.setLong("productMarketingId", productMarketingId);
        Double value = (Double) query.uniqueResult();
        
        if(value != null){
            return value.floatValue();
        }
        
        return 0F;
    }
    
    public ProductMarketingCustomerRate saveOrUpdateProductMarketingCustomerRate(final ProductMarketingCustomerRate productMarketingCustomerRate) {
        if(productMarketingCustomerRate.getDateCreate() == null){
            productMarketingCustomerRate.setDateCreate(new Date());
        }
        productMarketingCustomerRate.setDateUpdate(new Date());
        if (productMarketingCustomerRate.getId() != null) {
            if(em.contains(productMarketingCustomerRate)){
                em.refresh(productMarketingCustomerRate);
            }
            ProductMarketingCustomerRate mergedProductMarketingCustomerRate = em.merge(productMarketingCustomerRate);
            em.flush();
            return mergedProductMarketingCustomerRate;
        } else {
            em.persist(productMarketingCustomerRate);
            return productMarketingCustomerRate;
        }
    }

    public void deleteProductMarketingCustomerRate(final ProductMarketingCustomerRate productMarketingCustomerRate) {
        em.remove(productMarketingCustomerRate);
    }
    
    public ProductMarketingCustomerComment saveOrUpdateProductMarketingCustomerComment(final ProductMarketingCustomerComment productMarketingCustomerComment) {
        if(productMarketingCustomerComment.getDateCreate() == null){
            productMarketingCustomerComment.setDateCreate(new Date());
        }
        productMarketingCustomerComment.setDateUpdate(new Date());
        if (productMarketingCustomerComment.getId() != null) {
            if(em.contains(productMarketingCustomerComment)){
                em.refresh(productMarketingCustomerComment);
            }
            ProductMarketingCustomerComment mergedProductMarketingCustomerComment = em.merge(productMarketingCustomerComment);
            em.flush();
            return mergedProductMarketingCustomerComment;
        } else {
            em.persist(productMarketingCustomerComment);
            return productMarketingCustomerComment;
        }
    }

    public void deleteProductMarketingCustomerComment(final ProductMarketingCustomerComment productMarketingCustomerComment) {
        em.remove(productMarketingCustomerComment);
    }
    
	// PRODUCT MARKETING ASSET
	
	public Asset getProductMarketingAssetById(final Long productMarketingAssetId, Object... params) {
        Criteria criteria = createDefaultCriteria(Asset.class);
        criteria.add(Restrictions.eq("id", productMarketingAssetId));
        Asset productMarketingAsset = (Asset) criteria.uniqueResult();
        return productMarketingAsset;
	}

	public Asset getProductMarketingAssetByCode(final String assetCode, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        criteria.add(Restrictions.eq("code", assetCode));
        Asset productMarketingAsset = (Asset) criteria.uniqueResult();
		return productMarketingAsset;
	}
	
	public Asset saveOrUpdateProductMarketingAsset(final Asset productMarketingAsset) {
		if(productMarketingAsset.getDateCreate() == null){
			productMarketingAsset.setDateCreate(new Date());
		}
		productMarketingAsset.setDateUpdate(new Date());
        if (productMarketingAsset.getId() != null) {
            if(em.contains(productMarketingAsset)){
                em.refresh(productMarketingAsset);
            }
            Asset mergedAsset = em.merge(productMarketingAsset);
            em.flush();
            return mergedAsset;
        } else {
            em.persist(productMarketingAsset);
            return productMarketingAsset;
        }
	}

	public void deleteProductMarketingAsset(final Asset productMarketingAsset) {
		em.remove(productMarketingAsset);
	}
	
    protected FetchPlan handleSpecificProductMarketingFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphProduct.productMarketingDefaultFetchPlan());
        }
    }
    
    // PRODUCT SKU
	
    public ProductSku getProductSkuById(final Long productSkuId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        FetchPlan fetchPlan = handleSpecificProductSkuFetchMode(criteria, params);
        criteria.add(Restrictions.eq("id", productSkuId));
        ProductSku productSku = (ProductSku) criteria.uniqueResult();
        if(productSku != null){
            productSku.setFetchPlan(fetchPlan);
        }
        return productSku;
    }
    
    public ProductSku getProductSkuByCode(final String skuCode, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        FetchPlan fetchPlan = handleSpecificProductSkuFetchMode(criteria, params);
        criteria.add(Restrictions.eq("code", skuCode));
        ProductSku productSku = (ProductSku) criteria.uniqueResult();
        if(productSku != null){
            productSku.setFetchPlan(fetchPlan);
        }
        return productSku;
    }
        
    public List<ProductSku> findProductSkusByproductMarketingId(final Long productMarketing, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        handleSpecificProductSkuFetchMode(criteria, params);

        criteria.add(Restrictions.eq("productMarketing", productMarketing));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> productSkus = criteria.list();
        return productSkus;
    }
    
    public List<ProductSku> findProductSkus(final String text, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        handleSpecificProductSkuFetchMode(criteria, params);
        
        criteria.add(Restrictions.or(Restrictions.eq("code", "%" + text + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("name", "%" + text + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("description", "%" + text + "%")));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> productSkus = criteria.list();
        return productSkus;
    }
    
    public ProductSku saveOrUpdateProductSku(final ProductSku productSku) {
        if(productSku.getDateCreate() == null){
            productSku.setDateCreate(new Date());
        }
        productSku.setDateUpdate(new Date());
        if (productSku.getId() != null) {
            if(em.contains(productSku)){
                em.refresh(productSku);
            }
            ProductSku mergedProductSku = em.merge(productSku);
            em.flush();
            return mergedProductSku;
        } else {
            em.persist(productSku);
            return productSku;
        }
    }

    public void deleteProductSku(final ProductSku productSku) {
        em.remove(productSku);
    }
    
    protected FetchPlan handleSpecificProductSkuFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphProduct.productSkuDefaultFetchPlan());
        }
    }
    
    // ASSET
    
    public Asset getProductSkuAssetById(final Long productSkuAssetId, Object... params) {
        Criteria criteria = createDefaultCriteria(Asset.class);
        criteria.add(Restrictions.eq("id", productSkuAssetId));
        Asset productSkuAsset = (Asset) criteria.uniqueResult();
        return productSkuAsset;
    }

    public Asset getProductSkuAssetByCode(final String assetCode, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        criteria.add(Restrictions.eq("code", assetCode));
        Asset productSkuAsset = (Asset) criteria.uniqueResult();
        return productSkuAsset;
    }
    
    public Asset saveOrUpdateProductSkuAsset(final Asset productSkuAsset) {
        if(productSkuAsset.getDateCreate() == null){
            productSkuAsset.setDateCreate(new Date());
        }
        productSkuAsset.setDateUpdate(new Date());
        if (productSkuAsset.getId() != null) {
            if(em.contains(productSkuAsset)){
                em.refresh(productSkuAsset);
            }
            Asset mergedAsset = em.merge(productSkuAsset);
            em.flush();
            return mergedAsset;
        } else {
            em.persist(productSkuAsset);
            return productSkuAsset;
        }
    }

    public void deleteProductSkuAsset(final Asset productSkuAsset) {
        em.remove(productSkuAsset);
    }
    
    // PRODUCT BRAND
    
    public ProductBrand getProductBrandById(final Long productBrandId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductBrand.class);
        FetchPlan fetchPlan = handleSpecificProductBrandFetchMode(criteria, params);
        criteria.add(Restrictions.eq("id", productBrandId));
        ProductBrand productBrand = (ProductBrand) criteria.uniqueResult();
        if(productBrand != null){
            productBrand.setFetchPlan(fetchPlan);
        }
        return productBrand;
    }

    public ProductBrand getProductBrandByCode(final Long marketAreaId, final String productBrandCode, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductBrand.class);
        FetchPlan fetchPlan = handleSpecificProductBrandFetchMode(criteria, params);
        criteria.add(Restrictions.eq("code", productBrandCode));
        ProductBrand productBrand = (ProductBrand) criteria.uniqueResult();
        if(productBrand != null){
            productBrand.setFetchPlan(fetchPlan);
        }
        return productBrand;
    }
    
    public List<ProductBrand> findProductBrandsByCatalogCategoryCode(final String categoryCode, Object... params) {
        Criteria criteria = getSession().createCriteria(ProductBrand.class);
        handleSpecificProductBrandFetchMode(criteria, params);
        
        criteria.setFetchMode("productMarketings", FetchMode.JOIN);
        criteria.createAlias("productMarketings.defaultCatalogCategory", "defaultCatalogCategory", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("defaultCatalogCategory", FetchMode.JOIN);

        criteria.add(Restrictions.eq("defaultCatalogCategory.code", categoryCode));

        @SuppressWarnings("unchecked")
        List<ProductBrand> productBrands = criteria.list();
        return productBrands;
    }
    
    public ProductBrand saveOrUpdateProductBrand(final ProductBrand productBrand) {
        if(productBrand.getDateCreate() == null){
            productBrand.setDateCreate(new Date());
        }
        productBrand.setDateUpdate(new Date());
        if (productBrand.getId() != null) {
            if(em.contains(productBrand)){
                em.refresh(productBrand);
            }
            ProductBrand mergedProductBrand = em.merge(productBrand);
            em.flush();
            return mergedProductBrand;
        } else {
            em.persist(productBrand);
            return productBrand;
        }
    }

    public void deleteProductBrand(final ProductBrand productBrand) {
        em.remove(productBrand);
    }
    
    protected FetchPlan handleSpecificProductBrandFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphProduct.defaultProductBrandFetchPlan());
        }
    }
    
}