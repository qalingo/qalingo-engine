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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.persistence.NonUniqueResultException;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.CatalogCategoryMasterProductSkuRel;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtualProductSkuRel;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductBrandCustomerComment;
import org.hoteia.qalingo.core.domain.ProductBrandCustomerRate;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerComment;
import org.hoteia.qalingo.core.domain.ProductMarketingCustomerRate;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuOptionDefinition;
import org.hoteia.qalingo.core.domain.ProductSkuOptionDefinitionType;
import org.hoteia.qalingo.core.domain.ProductSkuStoreRel;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.catalog.FetchPlanGraphProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("productDao")
public class ProductDao extends AbstractGenericDao {

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
        criteria.add(Restrictions.eq("code", handleCodeValue(productMarketingCode)));
        ProductMarketing productMarketing = (ProductMarketing) criteria.uniqueResult();
        if(productMarketing != null){
            productMarketing.setFetchPlan(fetchPlan);
        }
		return productMarketing;
	}
	
    public Long countProductMarketing() {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
    
	public List<ProductMarketing> findAllProductMarketings(Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
		return productMarketings;
	}
	
	public List<Long> findAllProductMarketingIds(int maxResults) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.asc("id"));
        
        if(maxResults != 0){
            criteria.setMaxResults(maxResults);
        }
        
        @SuppressWarnings("unchecked")
        List<Long> productMarketingIds = criteria.list();
		return productMarketingIds;
	}

    public List<ProductMarketing> findProductMarketingByRandom(int maxResults, Object... params) {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(1000);
        
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);
        criteria.add(Restrictions.sqlRestriction("1=1 ORDER BY RAND(" + randomInt + ")"));
        criteria.setMaxResults(maxResults);

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingEnableB2CByRandom(int maxResults, Object... params) {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(1000);
        
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);
        criteria.add(Restrictions.eq("enabledB2C", true));
        criteria.add(Restrictions.sqlRestriction("1=1 ORDER BY RAND(" + randomInt + ")"));
        criteria.setMaxResults(maxResults);

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
    }
	
	public List<ProductMarketing> findProductMarketings(final String text, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);

        criteria.add(Restrictions.or(Restrictions.like("code", text, MatchMode.ANYWHERE), Restrictions.like("name", text, MatchMode.ANYWHERE), Restrictions.like("description", text, MatchMode.ANYWHERE)));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
		return productMarketings;
	}

    public List<Long> findProductMarketingIdsByBrandId(final Long brandId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);

        criteria.setFetchMode("productBrand", FetchMode.JOIN);
        criteria.createAlias("productBrand", "productBrand", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("productBrand.id", brandId));
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<Long> productMarketings = criteria.list();
        return productMarketings;
    }
    
    public List<ProductMarketing> findProductMarketingsByBrandId(final Long brandId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);

        criteria.setFetchMode("productBrand", FetchMode.JOIN);
        criteria.createAlias("productBrand", "productBrand", JoinType.LEFT_OUTER_JOIN);
        
        criteria.add(Restrictions.eq("productBrand.id", brandId));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
    }

	public List<ProductMarketing> findProductMarketingsByBrandCode(final String brandCode, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);

        criteria.setFetchMode("productBrand", FetchMode.JOIN);
        criteria.createAlias("productBrand", "productBrand", JoinType.LEFT_OUTER_JOIN);
        
        criteria.add(Restrictions.eq("productBrand.code", handleCodeValue(brandCode)));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
	}

    public List<ProductMarketing> findProductMarketingsByMasterCatalogCategoryId(final Long categoryId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);

        criteria.createAlias("productSkus", "productSku", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("productSku.catalogCategoryMasterProductSkuRels", "catalogCategoryProductSkuRel", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalogCategoryProductSkuRel.pk.catalogCategoryMaster.id", categoryId));

        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
    }

    public List<ProductMarketing> findProductMarketingsNotInThisMasterCatalogCategoryId(final Long categoryId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);

        criteria.createAlias("productSkus", "productSku", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("productSku.catalogCategoryMasterProductSkuRels", "catalogCategoryProductSkuRel", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.ne("catalogCategoryProductSkuRel.pk.catalogCategoryMaster.id", categoryId));

        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
    }
	    
	public List<ProductMarketing> findProductMarketingsByVirtualCatalogCategoryId(final Long categoryId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);

        criteria.createAlias("productSkus", "productSku", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("productSku.catalogCategoryVirtualProductSkuRels", "catalogCategoryProductSkuRel", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalogCategoryProductSkuRel.pk.catalogCategoryVirtual.id", categoryId));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
	}
	
    public List<ProductMarketing> findProductMarketingsNotInThisVirtualCatalogCategoryId(final Long categoryId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        handleSpecificProductMarketingFetchMode(criteria, params);

        criteria.createAlias("productSkus", "productSku", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("productSku.catalogCategoryVirtualProductSkuRels", "catalogCategoryProductSkuRel", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.ne("catalogCategoryProductSkuRel.pk.catalogCategoryVirtual.id", categoryId));
        
        criteria.addOrder(Order.asc("id"));

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
	
    public ProductMarketing createProductMarketing(final ProductMarketing productMarketing) {
    	productMarketing.setDateCreate(new Date());
		productMarketing.setDateUpdate(new Date());
        em.persist(productMarketing);
        return productMarketing;
    }
    
    public ProductMarketing updateProductMarketing(final ProductMarketing productMarketing) {
        productMarketing.setDateUpdate(new Date());
        return em.merge(productMarketing);
    }

	public void deleteProductMarketing(final ProductMarketing productMarketing) {
		em.remove(productMarketing);
	}
	
    // PRODUCT MARKETING COMMENT/RATE
	
    @SuppressWarnings("unchecked")
    public List<ProductMarketingCustomerComment> findProductMarketingCustomerCommentsByProductMarketingId(final Long productMarketingId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketingCustomerComment.class);
        criteria.createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("productMarketing", "productMarketing", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("productMarketing.id", productMarketingId));
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<ProductMarketingCustomerComment> findProductMarketingCustomerCommentsByProductMarketingIdAndMarketAreaId(final Long productMarketingId, final Long marketAreaId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketingCustomerComment.class);
        criteria.createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("productMarketing", "productMarketing", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("productMarketing.id", productMarketingId));
        criteria.add(Restrictions.eq("marketAreaId", marketAreaId));
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<ProductMarketingCustomerComment> findProductMarketingCustomerCommentsByCustomerId(final Long customerId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketingCustomerComment.class);
        criteria.createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("customer.id", customerId));
        criteria.createAlias("productMarketing", "productMarketing", JoinType.LEFT_OUTER_JOIN);
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<ProductMarketingCustomerRate> findProductMarketingCustomerRatesByProductMarketingId(final Long productMarketingId, final String type, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductMarketingCustomerRate.class);
        criteria.createAlias("productMarketing", "productMarketing", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("productMarketing.id", productMarketingId));
        criteria.add(Restrictions.eq("type", type));
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }
    
    public Float calculateProductMarketingCustomerRatesByProductMarketingId(final Long productMarketingId) {
        String sql = "select avg(rate) from ProductMarketingCustomerRate where productMarketingId = :productMarketingId";
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
        criteria.add(Restrictions.eq("code", handleCodeValue(skuCode)));
        ProductSku productSku = (ProductSku) criteria.uniqueResult();
        if(productSku != null){
            productSku.setFetchPlan(fetchPlan);
        }
        return productSku;
    }
    
    public ProductSku getProductSkuByEAN(final String skuEAN, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        FetchPlan fetchPlan = handleSpecificProductSkuFetchMode(criteria, params);
        criteria.add(Restrictions.eq("ean", handleCodeValue(skuEAN)));
        ProductSku productSku = (ProductSku) criteria.uniqueResult();
        if(productSku != null){
            productSku.setFetchPlan(fetchPlan);
        }
        return productSku;
    }

    public Long countProductSku() {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
    
    public List<Long> findProductSkuIds(int maxResults) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.asc("id"));
        
        if(maxResults != 0){
            criteria.setMaxResults(maxResults);
        }
        
        @SuppressWarnings("unchecked")
        List<Long> productSkuIds = criteria.list();
        return productSkuIds;
    }

    public List<String> findProductSkuCode(int maxResults) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        criteria.setProjection(Projections.property("code"));
        criteria.addOrder(Order.asc("code"));

        if(maxResults != 0){
            criteria.setMaxResults(maxResults);
        }

        @SuppressWarnings("unchecked")
        List<String> productSkuIds = criteria.list();
        return productSkuIds;
    }
    
    public List<Long> findProductSkuIdsEnableB2COrderByDateUpdate(int maxResults) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        criteria.setProjection(Projections.property("id"));
        criteria.add(Restrictions.eq("enabledB2C", true));
        criteria.addOrder(Order.asc("dateUpdate"));
        
        if(maxResults != 0){
            criteria.setMaxResults(maxResults);
        }
        
        @SuppressWarnings("unchecked")
        List<Long> productSkuIds = criteria.list();
        return productSkuIds;
    }
    
    public List<ProductSku> findProductSkusByproductMarketingId(final Long productMarketingId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        handleSpecificProductSkuFetchMode(criteria, params);

        criteria.add(Restrictions.eq("productMarketing.id", productMarketingId));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> productSkus = criteria.list();
        return productSkus;
    }
    
    public List<Long> findProductSkuIdsByBrandId(final Long brandId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);

        criteria.createCriteria("productMarketing", "productMarketing", JoinType.LEFT_OUTER_JOIN).add(Restrictions.eq("productBrand.id", brandId));
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<Long> productMarketings = criteria.list();
        return productMarketings;
    }
    
    public List<Long> findProductSkuIdsByBrandIdOrderByDateUpdate(final Long brandId, int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);

        criteria.createCriteria("productMarketing", "productMarketing", JoinType.LEFT_OUTER_JOIN).add(Restrictions.eq("productBrand.id", brandId));
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.asc("dateUpdate"));

        if(maxResults != 0){
            criteria.setMaxResults(maxResults);
        }
        
        @SuppressWarnings("unchecked")
        List<Long> productMarketings = criteria.list();
        return productMarketings;
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

    public List<ProductSku> findProductSkusByMasterCatalogCategoryId(final Long categoryId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        handleSpecificProductSkuFetchMode(criteria, params);

        criteria.createAlias("catalogCategoryMasterProductSkuRels", "catalogCategoryProductSkuRel", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalogCategoryProductSkuRel.pk.catalogCategoryMaster.id", categoryId));

        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> productSkus = criteria.list();
        return productSkus;
    }

    public List<ProductSku> findProductSkusNotInThisMasterCatalogCategoryId(final Long categoryId, Object... params) {
        DetachedCriteria subquery = DetachedCriteria.forClass(ProductSku.class);
        subquery.createAlias("catalogCategoryMasterProductSkuRels", "catalogCategoryProductSkuRel", JoinType.LEFT_OUTER_JOIN);
        subquery.add(Restrictions.eq("catalogCategoryProductSkuRel.pk.catalogCategoryMaster.id", categoryId));
        subquery.setProjection(Projections.property("id"));

        Criteria criteria = createDefaultCriteria(ProductSku.class);
        handleSpecificProductSkuFetchMode(criteria, params);
        criteria.add(Subqueries.notIn("id", subquery));

        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> productSkus = criteria.list();
        return productSkus;
    }
    
    public List<ProductSku> findProductSkusByVirtualCatalogCategoryId(final Long categoryId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        handleSpecificProductSkuFetchMode(criteria, params);

        criteria.createAlias("catalogCategoryVirtualProductSkuRels", "catalogCategoryProductSkuRel", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("catalogCategoryProductSkuRel.pk.catalogCategoryVirtual.id", categoryId));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> productSkus = criteria.list();
        return productSkus;
    }
    
    public List<ProductSku> findProductSkusNotInThisVirtualCatalogCategoryId(final Long categoryId, Object... params) {
        Criteria criteriaSubListId = createDefaultCriteria(ProductSku.class);
        criteriaSubListId.createAlias("catalogCategoryVirtualProductSkuRels", "catalogCategoryProductSkuRel", JoinType.LEFT_OUTER_JOIN);
        criteriaSubListId.add(Restrictions.eq("catalogCategoryProductSkuRel.pk.catalogCategoryVirtual.id", categoryId));
//        criteriaSubListId.setProjection(Projections.property("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> subProductSkus = criteriaSubListId.list();
        
        List<Long> productSkuIds = new ArrayList<Long>();
        for (Iterator<ProductSku> iterator = subProductSkus.iterator(); iterator.hasNext();) {
            ProductSku productSku = (ProductSku) iterator.next();
            productSkuIds.add(productSku.getId());
        }
        
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        handleSpecificProductSkuFetchMode(criteria, params);
        criteria.add(Restrictions.not(Restrictions.in("id", productSkuIds)));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> productSkus = criteria.list();
        return productSkus;
    }
    
    public List<ProductSku> findProductSkusByStoreId(final Long storeId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        handleSpecificProductSkuFetchMode(criteria, params);

        criteria.createAlias("stores", "stores", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("stores.id", storeId));

        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> productSkus = criteria.list();
        return productSkus;
    }
    
    public List<Long> findProductSkuIdsActiveByStoreId(final Long storeId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        criteria.setProjection(Projections.property("id"));

        criteria.createAlias("stores", "stores", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("stores.id", storeId));

        criteria.add(Restrictions.eq("enabledB2C", true));

        @SuppressWarnings("unchecked")
        List<Long> productSkuIds = criteria.list();
        return productSkuIds;
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

	public ProductSku createProductSku(final ProductSku productSku) {
		productSku.setDateCreate(new Date());
		productSku.setDateUpdate(new Date());
		em.persist(productSku);
		return productSku;
	}
    
    public ProductSku updateProductSku(final ProductSku productSku) {
        productSku.setDateUpdate(new Date());
        ProductSku mergedProductSku = em.merge(productSku);
        return mergedProductSku;
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
    
    // PRODUCT SKU STORE
    
    public ProductSkuStoreRel findProductSkuStoreRelByStoreIdAndSpecificCode(final Long storeId, final String specificCode, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSkuStoreRel.class);

        criteria.createAlias("pk.store", "store", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("pk.store.id", storeId));
        criteria.add(Restrictions.eq("specificCode", specificCode));

        handleSpecificProductSkuStoreRelFetchMode(criteria, params);

        ProductSkuStoreRel productSkuStoreRel = null;
        try {
            productSkuStoreRel = (ProductSkuStoreRel) criteria.uniqueResult();
        } catch (NonUniqueResultException e) {
            logger.error("NonUniqueResultException: storeId='" + storeId + "', specificCode: '" + specificCode + "'");
            @SuppressWarnings("unchecked")
            List<ProductSkuStoreRel> productSkuStoreRels = (List<ProductSkuStoreRel>) criteria.list();
            for (Iterator<ProductSkuStoreRel> iterator = productSkuStoreRels.iterator(); iterator.hasNext();) {
                ProductSkuStoreRel productSkuStoreRelLog = (ProductSkuStoreRel) iterator.next();
                logger.error("productSkuStoreRel: " + productSkuStoreRelLog.toString());
            }
        }
        return productSkuStoreRel;
    }
    
    public List<ProductSkuStoreRel> findProductSkuStoreRelByProductSkuId(final Long productSkuId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSkuStoreRel.class);

        criteria.createAlias("pk.productSku", "productSku", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("pk.productSku.id", productSkuId));

        handleSpecificProductSkuStoreRelFetchMode(criteria, params);

        @SuppressWarnings("unchecked")
        List<ProductSkuStoreRel> productSkuStoreRels = criteria.list();
        return productSkuStoreRels;
    }

    public List<ProductSkuStoreRel> getProductSkuStoreRelB2B(Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSkuStoreRel.class);

        criteria.add(Restrictions.eq("salableOnlineB2B", true));

        handleSpecificProductSkuStoreRelFetchMode(criteria, params);

        @SuppressWarnings("unchecked")
        List<ProductSkuStoreRel> productSkuStoreRels = criteria.list();
        return productSkuStoreRels;
    }
    
    public ProductSkuStoreRel findProductSkuStoreRelByStoreIdAndProductId(final Long storeId, final Long productSkuId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSkuStoreRel.class);

        criteria.createAlias("pk.store", "store", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("pk.store.id", storeId));

        criteria.createAlias("pk.productSku", "productSku", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("pk.productSku.id", productSkuId));

        handleSpecificProductSkuStoreRelFetchMode(criteria, params);

        ProductSkuStoreRel productSkuStoreRel = null;
        try {
            productSkuStoreRel = (ProductSkuStoreRel) criteria.uniqueResult();
        } catch (NonUniqueResultException e) {
            logger.error("NonUniqueResultException: storeId='" + storeId + "', productSkuId: '" + productSkuId + "'");
            List<ProductSkuStoreRel> productSkuStoreRels = (List<ProductSkuStoreRel>) criteria.list();
            for (Iterator<ProductSkuStoreRel> iterator = productSkuStoreRels.iterator(); iterator.hasNext();) {
                ProductSkuStoreRel productSkuStoreRelExp = (ProductSkuStoreRel) iterator.next();
                logger.error("productSkuStoreRel: " + productSkuStoreRelExp.toString());
            }
        }
        return productSkuStoreRel;
    }
    
    public List<ProductSkuStoreRel> findProductSkuStoreRelByStoreId(final Long storeId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSkuStoreRel.class);

        criteria.createAlias("pk.store", "store", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("pk.store.id", storeId));

        handleSpecificProductSkuStoreRelFetchMode(criteria, params);

        @SuppressWarnings("unchecked")
        List<ProductSkuStoreRel> productSkuStoreRels = criteria.list();
        return productSkuStoreRels;
    }

    public void saveNewProductSkuStoreRel(final ProductSkuStoreRel productSkuStoreRel) {
        productSkuStoreRel.setDateCreate(new Date());
        productSkuStoreRel.setDateUpdate(new Date());
        em.persist(productSkuStoreRel);
    }
    
    public ProductSkuStoreRel updateProductSkuStoreRel(final ProductSkuStoreRel productSkuStoreRel) {
        productSkuStoreRel.setDateUpdate(new Date());
        if(em.contains(productSkuStoreRel)){
            em.refresh(productSkuStoreRel);
        }
        ProductSkuStoreRel mergedProductSkuStoreRel = em.merge(productSkuStoreRel);
        em.flush();
        return mergedProductSkuStoreRel;
    }

    public void deleteProductSkuStoreRel(final ProductSkuStoreRel productSkuStoreRel) {
        em.remove(productSkuStoreRel);
    }
    
    protected FetchPlan handleSpecificProductSkuStoreRelFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphProduct.productSkuStoreRelDefaultFetchPlan());
        }
    }
    
    // ASSET
    
    public Asset getProductSkuAssetById(final Long productSkuAssetId, Object... params) {
        Criteria criteria = createDefaultCriteria(Asset.class);
        criteria.add(Restrictions.eq("id", productSkuAssetId));
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
    
    // PRODUCT SKU OPTION
    
    public ProductSkuOptionDefinition getProductSkuOptionDefinitionById(final Long productSkuOptionDefinitionId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSkuOptionDefinition.class);
        FetchPlan fetchPlan = handleSpecificProductSkuOptionDefinitionFetchMode(criteria, params);
        criteria.add(Restrictions.eq("id", productSkuOptionDefinitionId));
        ProductSkuOptionDefinition productSkuOptionDefinition = (ProductSkuOptionDefinition) criteria.uniqueResult();
        if(productSkuOptionDefinition != null){
            productSkuOptionDefinition.setFetchPlan(fetchPlan);
        }
        return productSkuOptionDefinition;
    }

    public ProductSkuOptionDefinition getProductSkuOptionDefinitionByCode(final String productSkuOptionDefinitionCode, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSkuOptionDefinition.class);
        FetchPlan fetchPlan = handleSpecificProductSkuOptionDefinitionFetchMode(criteria, params);
        criteria.add(Restrictions.eq("code", handleCodeValue(productSkuOptionDefinitionCode)));
        ProductSkuOptionDefinition productSkuOptionDefinition = (ProductSkuOptionDefinition) criteria.uniqueResult();
        if(productSkuOptionDefinition != null){
            productSkuOptionDefinition.setFetchPlan(fetchPlan);
        }
        return productSkuOptionDefinition;
    }
    
    public List<ProductSkuOptionDefinition> findAllProductSkuOptionDefinitions(Object... params) {
        Criteria criteria = getSession().createCriteria(ProductSkuOptionDefinition.class);
        handleSpecificProductSkuOptionDefinitionFetchMode(criteria, params);
        
        @SuppressWarnings("unchecked")
        List<ProductSkuOptionDefinition> productSkuOptionDefinitions = criteria.list();
        return productSkuOptionDefinitions;
    }
    
    public ProductSkuOptionDefinition saveOrUpdateProductSkuOptionDefinition(final ProductSkuOptionDefinition productSkuOptionDefinition) {
        if(productSkuOptionDefinition.getDateCreate() == null){
            productSkuOptionDefinition.setDateCreate(new Date());
        }
        productSkuOptionDefinition.setDateUpdate(new Date());
        if (productSkuOptionDefinition.getId() != null) {
            if(em.contains(productSkuOptionDefinition)){
                em.refresh(productSkuOptionDefinition);
            }
            ProductSkuOptionDefinition mergedProductSkuOptionDefinition = em.merge(productSkuOptionDefinition);
            em.flush();
            return mergedProductSkuOptionDefinition;
        } else {
            em.persist(productSkuOptionDefinition);
            return productSkuOptionDefinition;
        }
    }

    public void deleteProductSkuOptionDefinition(final ProductSkuOptionDefinition productSkuOptionDefinition) {
        em.remove(productSkuOptionDefinition);
    }
    
    protected FetchPlan handleSpecificProductSkuOptionDefinitionFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphProduct.productSkuOptionDefinitionDefaultFetchPlan());
        }
    }
    
    public ProductSkuOptionDefinitionType getProductSkuOptionDefinitionTypeById(final Long productSkuOptionDefinitionTypeId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSkuOptionDefinitionType.class);
        FetchPlan fetchPlan = handleSpecificProductSkuOptionDefinitionTypeFetchMode(criteria, params);
        criteria.add(Restrictions.eq("id", productSkuOptionDefinitionTypeId));
        ProductSkuOptionDefinitionType productSkuOptionDefinitionType = (ProductSkuOptionDefinitionType) criteria.uniqueResult();
        if(productSkuOptionDefinitionType != null){
            productSkuOptionDefinitionType.setFetchPlan(fetchPlan);
        }
        return productSkuOptionDefinitionType;
    }

    public ProductSkuOptionDefinitionType getProductSkuOptionDefinitionTypeByCode(final String productSkuOptionDefinitionTypeCode, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSkuOptionDefinitionType.class);
        FetchPlan fetchPlan = handleSpecificProductSkuOptionDefinitionTypeFetchMode(criteria, params);
        criteria.add(Restrictions.eq("code", handleCodeValue(productSkuOptionDefinitionTypeCode)));
        ProductSkuOptionDefinitionType productSkuOptionDefinitionType = (ProductSkuOptionDefinitionType) criteria.uniqueResult();
        if(productSkuOptionDefinitionType != null){
            productSkuOptionDefinitionType.setFetchPlan(fetchPlan);
        }
        return productSkuOptionDefinitionType;
    }
    
    public List<ProductSkuOptionDefinitionType> findAllProductSkuOptionDefinitionTypes(Object... params) {
        Criteria criteria = getSession().createCriteria(ProductSkuOptionDefinitionType.class);
        handleSpecificProductSkuOptionDefinitionTypeFetchMode(criteria, params);
        
        @SuppressWarnings("unchecked")
        List<ProductSkuOptionDefinitionType> productSkuOptionDefinitionTypes = criteria.list();
        return productSkuOptionDefinitionTypes;
    }
    
    public ProductSkuOptionDefinitionType saveOrUpdateProductSkuOptionDefinitionType(final ProductSkuOptionDefinitionType productSkuOptionDefinitionType) {
        if(productSkuOptionDefinitionType.getDateCreate() == null){
            productSkuOptionDefinitionType.setDateCreate(new Date());
        }
        productSkuOptionDefinitionType.setDateUpdate(new Date());
        if (productSkuOptionDefinitionType.getId() != null) {
            if(em.contains(productSkuOptionDefinitionType)){
                em.refresh(productSkuOptionDefinitionType);
            }
            ProductSkuOptionDefinitionType mergedProductSkuOptionDefinitionType = em.merge(productSkuOptionDefinitionType);
            em.flush();
            return mergedProductSkuOptionDefinitionType;
        } else {
            em.persist(productSkuOptionDefinitionType);
            return productSkuOptionDefinitionType;
        }
    }

    public void deleteProductSkuOptionDefinitionType(final ProductSkuOptionDefinitionType productSkuOptionDefinitionType) {
        em.remove(productSkuOptionDefinitionType);
    }
    
    protected FetchPlan handleSpecificProductSkuOptionDefinitionTypeFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphProduct.productSkuOptionDefinitionTypeDefaultFetchPlan());
        }
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

    public ProductBrand getProductBrandByCode(final String productBrandCode, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductBrand.class);
        FetchPlan fetchPlan = handleSpecificProductBrandFetchMode(criteria, params);
        criteria.add(Restrictions.eq("code", handleCodeValue(productBrandCode)));
        ProductBrand productBrand = (ProductBrand) criteria.uniqueResult();
        if(productBrand != null){
            productBrand.setFetchPlan(fetchPlan);
        }
        return productBrand;
    }
    
    public Long countProductBrand() {
        Criteria criteria = createDefaultCriteria(ProductBrand.class);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
    
    public List<ProductBrand> findAllProductBrands(Object... params) {
        Criteria criteria = getSession().createCriteria(ProductBrand.class);
        handleSpecificProductBrandFetchMode(criteria, params);
        criteria.addOrder(Order.asc("name"));
        
        @SuppressWarnings("unchecked")
        List<ProductBrand> productBrands = criteria.list();
        return productBrands;
    }
    
    public List<Long> findAllProductBrandIds() {
        Criteria criteria = getSession().createCriteria(ProductBrand.class);
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.asc("name"));
        
        @SuppressWarnings("unchecked")
        List<Long> productBrandIds = criteria.list();
        return productBrandIds;
    }
    
    public List<Long> findAllProductBrandIdsEnabled(Object... params) {
        Criteria criteria = getSession().createCriteria(ProductBrand.class);
        criteria.setProjection(Projections.property("id"));
        
        criteria.add(Restrictions.eq("enabled", true));
        criteria.addOrder(Order.asc("name"));
        
        @SuppressWarnings("unchecked")
        List<Long> productBrands = criteria.list();
        return productBrands;
    }
    
    public List<ProductBrand> findAllProductBrandsEnabled(Object... params) {
        Criteria criteria = getSession().createCriteria(ProductBrand.class);
        handleSpecificProductBrandFetchMode(criteria, params);
        criteria.add(Restrictions.eq("enabled", true));
        criteria.addOrder(Order.asc("name"));
        
        @SuppressWarnings("unchecked")
        List<ProductBrand> productBrands = criteria.list();
        return productBrands;
    }
    
    public List<ProductBrand> findProductBrandsByCatalogCategoryCode(final String categoryCode, Object... params) {
        Criteria criteria = getSession().createCriteria(ProductBrand.class);
        handleSpecificProductBrandFetchMode(criteria, params);
        
        criteria.createAlias("productMarketings", "productMarketing", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("productMarketing.productSkus", "productSku", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("productSku.defaultCatalogCategory", FetchMode.JOIN);
        criteria.createAlias("productSku.defaultCatalogCategory", "defaultCatalogCategory", JoinType.LEFT_OUTER_JOIN);
        
        criteria.add(Restrictions.eq("defaultCatalogCategory.code", handleCodeValue(categoryCode)));

        
        @SuppressWarnings("unchecked")
        List<ProductBrand> productBrands = criteria.list();
        return productBrands;
    }
    
    public List<ProductBrand> findProductBrandsByText(String text, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductBrand.class);
        handleSpecificProductBrandFetchMode(criteria, params);
        criteria.add(Restrictions.or(Restrictions.like("name", text, MatchMode.ANYWHERE), Restrictions.like("description", text, MatchMode.ANYWHERE)));

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
    
    public ProductBrand updateProductBrand(final ProductBrand productBrand) {
        productBrand.setDateUpdate(new Date());
        return em.merge(productBrand);
    }

    public void deleteProductBrand(final ProductBrand productBrand) {
        em.remove(productBrand);
    }
    
    
    // PRODUCT MARKETING COMMENT/RATE
    
    @SuppressWarnings("unchecked")
    public List<ProductBrandCustomerComment> findProductBrandCustomerCommentsByProductBrandId(final Long productBrandId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductBrandCustomerComment.class);
        criteria.createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("productBrand", "productBrand", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("productBrand.id", productBrandId));
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<ProductBrandCustomerComment> findProductBrandCustomerCommentsByProductBrandIdAndMarketAreaId(final Long productBrandId, final Long marketAreaId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductBrandCustomerComment.class);
        criteria.createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("productBrand", "productBrand", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("productBrand.id", productBrandId));
        criteria.add(Restrictions.eq("marketAreaId", marketAreaId));
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<ProductBrandCustomerComment> findProductBrandCustomerCommentsByCustomerId(final Long customerId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductBrandCustomerComment.class);
        criteria.createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("customer.id", customerId));
        criteria.createAlias("productBrand", "productBrand", JoinType.LEFT_OUTER_JOIN);
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<ProductBrandCustomerRate> findProductBrandCustomerRatesByProductBrandId(final Long productBrandId, final String type, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductBrandCustomerRate.class);
        criteria.createAlias("productBrand", "productBrand", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("productBrand.id", productBrandId));
        criteria.add(Restrictions.eq("type", type));
        criteria.addOrder(Order.asc("dateCreate"));
        return criteria.list();
    }
    
    public ProductBrandCustomerRate saveOrUpdateProductBrandCustomerRate(final ProductBrandCustomerRate productMarketingCustomerRate) {
        if(productMarketingCustomerRate.getDateCreate() == null){
            productMarketingCustomerRate.setDateCreate(new Date());
        }
        productMarketingCustomerRate.setDateUpdate(new Date());
        if (productMarketingCustomerRate.getId() != null) {
            if(em.contains(productMarketingCustomerRate)){
                em.refresh(productMarketingCustomerRate);
            }
            ProductBrandCustomerRate mergedProductBrandCustomerRate = em.merge(productMarketingCustomerRate);
            em.flush();
            return mergedProductBrandCustomerRate;
        } else {
            em.persist(productMarketingCustomerRate);
            return productMarketingCustomerRate;
        }
    }

    public void deleteProductBrandCustomerRate(final ProductBrandCustomerRate productMarketingCustomerRate) {
        em.remove(productMarketingCustomerRate);
    }
    
    public ProductBrandCustomerComment saveOrUpdateProductBrandCustomerComment(final ProductBrandCustomerComment customerComment) {
        if(customerComment.getDateCreate() == null){
            customerComment.setDateCreate(new Date());
        }
        customerComment.setDateUpdate(new Date());
        if (customerComment.getId() != null) {
            if(em.contains(customerComment)){
                em.refresh(customerComment);
            }
            ProductBrandCustomerComment mergedProductBrandCustomerComment = em.merge(customerComment);
            em.flush();
            return mergedProductBrandCustomerComment;
        } else {
            em.persist(customerComment);
            return customerComment;
        }
    }

    public void deleteProductBrandCustomerComment(final ProductBrandCustomerComment customerComment) {
        em.remove(customerComment);
    }
    
    protected FetchPlan handleSpecificProductBrandFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphProduct.productBrandDefaultFetchPlan());
        }
    }
    
    public void createCatalogCategoryMasterProductSkuRel(final CatalogCategoryMasterProductSkuRel catalogCategoryMasterProductSkuRel) {
        em.persist(catalogCategoryMasterProductSkuRel);
    }
    
    public void createCatalogCategoryVirtualProductSkuRel(final CatalogCategoryVirtualProductSkuRel catalogCategoryVirtualProductSkuRel) {
        em.persist(catalogCategoryVirtualProductSkuRel);
    }
    
}