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
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("productDao")
public class ProductDaoImpl extends AbstractGenericDaoImpl implements ProductDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    // PRODUCT MARKETING
	
	public ProductMarketing getProductMarketingById(final Long productMarketingId) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        
        addDefaultProductMarketingFetch(criteria);
        
        criteria.add(Restrictions.eq("id", productMarketingId));
        ProductMarketing productMarketing = (ProductMarketing) criteria.uniqueResult();
        return productMarketing;
	}

	public ProductMarketing getProductMarketingByCode(final String productMarketingCode) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        
        addDefaultProductMarketingFetch(criteria);

        criteria.add(Restrictions.eq("code", productMarketingCode));
        ProductMarketing productMarketing = (ProductMarketing) criteria.uniqueResult();
		return productMarketing;
	}
	
	public List<ProductMarketing> findProductMarketings() {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        
        addDefaultProductMarketingFetch(criteria);

        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
		return productMarketings;
	}
	
	public List<ProductMarketing> findProductMarketings(final String text) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        
        addDefaultProductMarketingFetch(criteria);

        criteria.add(Restrictions.or(Restrictions.eq("code", "%" + text + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("businessName", "%" + text + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("description", "%" + text + "%")));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
		return productMarketings;
	}

    public List<ProductMarketing> findProductMarketingsByBrandId(final Long brandId) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        
        addDefaultProductMarketingFetch(criteria);

        criteria.setFetchMode("productBrand", FetchMode.JOIN);
        criteria.createAlias("productBrand", "pb", JoinType.LEFT_OUTER_JOIN);
        
        criteria.add(Restrictions.eq("pb.id", brandId));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
    }

	public List<ProductMarketing> findProductMarketingsByBrandCode(final String brandCode) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        
        addDefaultProductMarketingFetch(criteria);

        criteria.setFetchMode("productBrand", FetchMode.JOIN);
        criteria.createAlias("productBrand", "pb", JoinType.LEFT_OUTER_JOIN);
        
        criteria.add(Restrictions.eq("pb.code", brandCode));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
	}
	
	public List<ProductMarketing> findProductMarketingsByCatalogCategoryCode(final String categoryCode) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        
        addDefaultProductMarketingFetch(criteria);

        criteria.setFetchMode("defaultCatalogCategory", FetchMode.JOIN);
        criteria.createAlias("defaultCatalogCategory", "dc", JoinType.LEFT_OUTER_JOIN);
        
        criteria.add(Restrictions.eq("dc.code", categoryCode));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductMarketing> productMarketings = criteria.list();
        return productMarketings;
	}
	
	public List<ProductMarketing> findProductMarketingsByCatalogCategoryCodeAndSortAndPagintion(String categoryCode,int pageNumber, int pageSize, String sortBy, String orderBy){
		 Criteria criteria = getSession().createCriteria(ProductMarketing.class);
	        
	        addDefaultProductMarketingFetch(criteria);

	        criteria.setFetchMode("defaultCatalogCategory", FetchMode.JOIN);
	        criteria.createAlias("defaultCatalogCategory", "dc", JoinType.LEFT_OUTER_JOIN);
	        
	        criteria.add(Restrictions.eq("dc.code", categoryCode));
	        if("desc".equals(orderBy)){
	        	criteria.addOrder(Order.desc(sortBy));
	        }else{
	        	criteria.addOrder(Order.asc(sortBy));
	        }
	        
	        criteria.setFirstResult((pageNumber - 1) * pageSize);
	        criteria.setMaxResults(pageSize);

	        @SuppressWarnings("unchecked")
	        List<ProductMarketing> productMarketings = criteria.list();
	        return productMarketings;
	}
	
	public void saveOrUpdateProductMarketing(final ProductMarketing productMarketing) {
		if(productMarketing.getDateCreate() == null){
			productMarketing.setDateCreate(new Date());
		}
		productMarketing.setDateUpdate(new Date());
		if(productMarketing.getId() == null){
			em.persist(productMarketing);
		} else {
			em.merge(productMarketing);
		}
	}

	public void deleteProductMarketing(final ProductMarketing productMarketing) {
		em.remove(productMarketing);
	}

	// PRODUCT MARKETING ASSET
	
	public Asset getProductMarketingAssetById(final Long productMarketingAssetId) {
        Criteria criteria = createDefaultCriteria(Asset.class);
        criteria.add(Restrictions.eq("id", productMarketingAssetId));
        Asset productMarketingAsset = (Asset) criteria.uniqueResult();
        return productMarketingAsset;
	}

	public Asset getProductMarketingAssetByCode(final String assetCode) {
        Criteria criteria = createDefaultCriteria(ProductMarketing.class);
        criteria.add(Restrictions.eq("code", assetCode));
        Asset productMarketingAsset = (Asset) criteria.uniqueResult();
		return productMarketingAsset;
	}
	
	public void saveOrUpdateProductMarketingAsset(final Asset productMarketingAsset) {
		if(productMarketingAsset.getDateCreate() == null){
			productMarketingAsset.setDateCreate(new Date());
		}
		productMarketingAsset.setDateUpdate(new Date());
		if(productMarketingAsset.getId() == null){
			em.persist(productMarketingAsset);
		} else {
			em.merge(productMarketingAsset);
		}
	}

	public void deleteProductMarketingAsset(final Asset productMarketingAsset) {
		em.remove(productMarketingAsset);
	}
	
    private void addDefaultProductMarketingFetch(Criteria criteria) {
        criteria.setFetchMode("productBrand", FetchMode.JOIN);
        criteria.setFetchMode("productMarketingType", FetchMode.JOIN); 
        criteria.setFetchMode("productMarketingAttributes", FetchMode.JOIN); 
        criteria.setFetchMode("productSkus", FetchMode.JOIN); 
        criteria.setFetchMode("productAssociationLinks", FetchMode.JOIN); 
        criteria.setFetchMode("assets", FetchMode.JOIN); 
        criteria.setFetchMode("defaultCatalogCategory", FetchMode.JOIN);
    }
    
    // PRODUCT SKU
	
    public ProductSku getProductSkuById(final Long productSkuId) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        
        addDefaultProductSkuFetch(criteria);

        criteria.add(Restrictions.eq("id", productSkuId));
        ProductSku productSku = (ProductSku) criteria.uniqueResult();
        return productSku;
    }
    
    public ProductSku getProductSkuByCode(final String productSkuCode) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        
        addDefaultProductSkuFetch(criteria);

        criteria.add(Restrictions.eq("code", productSkuCode));
        ProductSku productSku = (ProductSku) criteria.uniqueResult();
        return productSku;
    }
        
    public List<ProductSku> findProductSkusByproductMarketingId(final Long productMarketing) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        
        addDefaultProductSkuFetch(criteria);

        criteria.add(Restrictions.eq("productMarketing", productMarketing));
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> productSkus = criteria.list();
        return productSkus;
    }
    
    public List<ProductSku> findProductSkus(final String text) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        
        addDefaultProductSkuFetch(criteria);
        
        criteria.add(Restrictions.or(Restrictions.eq("code", "%" + text + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("businessName", "%" + text + "%")));
        criteria.add(Restrictions.or(Restrictions.eq("description", "%" + text + "%")));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<ProductSku> productSkus = criteria.list();
        return productSkus;
    }
    
    public void saveOrUpdateProductSku(final ProductSku productSku) {
        if(productSku.getDateCreate() == null){
            productSku.setDateCreate(new Date());
        }
        productSku.setDateUpdate(new Date());
        if(productSku.getId() == null){
            em.persist(productSku);
        } else {
            em.merge(productSku);
        }
    }

    public void deleteProductSku(final ProductSku productSku) {
        em.remove(productSku);
    }
    
    // ASSET
    
    public Asset getProductSkuAssetById(final Long productSkuAssetId) {
        Criteria criteria = createDefaultCriteria(Asset.class);
        criteria.add(Restrictions.eq("id", productSkuAssetId));
        Asset productSkuAsset = (Asset) criteria.uniqueResult();
        return productSkuAsset;
    }

    public Asset getProductSkuAssetByCode(final String assetCode) {
        Criteria criteria = createDefaultCriteria(ProductSku.class);
        criteria.add(Restrictions.eq("code", assetCode));
        Asset productSkuAsset = (Asset) criteria.uniqueResult();
        return productSkuAsset;
    }
    
    public void saveOrUpdateProductSkuAsset(final Asset productSkuAsset) {
        if(productSkuAsset.getDateCreate() == null){
            productSkuAsset.setDateCreate(new Date());
        }
        productSkuAsset.setDateUpdate(new Date());
        if(productSkuAsset.getId() == null){
            em.persist(productSkuAsset);
        } else {
            em.merge(productSkuAsset);
        }
    }

    public void deleteProductSkuAsset(final Asset productSkuAsset) {
        em.remove(productSkuAsset);
    }
    
    private void addDefaultProductSkuFetch(Criteria criteria) {
        criteria.setFetchMode("productSkuAttributes", FetchMode.JOIN); 
        criteria.setFetchMode("productMarketing", FetchMode.JOIN); 
        criteria.setFetchMode("assets", FetchMode.JOIN);
        
        criteria.setFetchMode("prices", FetchMode.JOIN); 
        
        criteria.createAlias("prices.currency", "currency", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("currency", FetchMode.JOIN);

        criteria.setFetchMode("stocks", FetchMode.JOIN); 
        criteria.setFetchMode("retailers", FetchMode.JOIN); 
    }
    
    @Override
    public List<ProductBrand> findProductBrandsByCatalogCategoryCode(final String categoryCode) {
    	StringBuilder queryString = new StringBuilder("select distinct mk.productBrand from ProductMarketing mk where ")
    									.append("mk.defaultCatalogCategory.code = :code or mk.defaultCatalogCategory.defaultParentCatalogCategory.code = :code ");
    	
    	Query query = getSession().createQuery(queryString.toString());
    	query.setString("code", categoryCode);
    	
    	return query.list();
    }
    
}