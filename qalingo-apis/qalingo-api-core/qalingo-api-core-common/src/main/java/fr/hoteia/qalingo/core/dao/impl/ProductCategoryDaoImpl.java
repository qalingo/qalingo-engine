/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.ProductCategoryDao;
import fr.hoteia.qalingo.core.domain.ProductCategoryMaster;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtual;

@Transactional
@Repository("productCategoryDao")
public class ProductCategoryDaoImpl extends AbstractGenericDaoImpl implements ProductCategoryDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	// MASTER
	public ProductCategoryMaster getMasterProductCategoryById(Long productCategoryId) {
		return em.find(ProductCategoryMaster.class, productCategoryId);
	}
	
	public ProductCategoryMaster getMasterProductCategoryByCode(String productCategoryCode) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM ProductCategoryMaster WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", productCategoryCode);
		ProductCategoryMaster productCategory = (ProductCategoryMaster) query.uniqueResult();
		return productCategory;
	}
	
	public ProductCategoryMaster getMasterProductCategoryByCode(final Long marketAreaId, final Long retailerId, String productCategoryCode) {
		Session session = (Session) em.getDelegate();
		initCategoryMasterFilter(session, marketAreaId, retailerId);
		String sql = "FROM ProductCategoryMaster WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", productCategoryCode);
		ProductCategoryMaster productCategory = (ProductCategoryMaster) query.uniqueResult();
		return productCategory;
	}
	
	public List<ProductCategoryMaster> findRootProductCategories() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM ProductCategoryMaster WHERE defaultParentProductCategory is null";
		Query query = session.createQuery(sql);
		List<ProductCategoryMaster> categories = (List<ProductCategoryMaster>) query.list();
		return categories;
	}
	
	public List<ProductCategoryMaster> findMasterCategoriesByMarketIdAndRetailerId(final Long marketAreaId, final Long retailerId) {
		Session session = (Session) em.getDelegate();
		initCategoryVirtualFilter(session, marketAreaId, retailerId);
		String sql = "SELECT productCategoryMaster FROM ProductCategoryMaster productCategoryMaster";
		Query query = session.createQuery(sql);
		List<ProductCategoryMaster> categories = (List<ProductCategoryMaster>) query.list();
		return categories;
	}
	
	public void saveOrUpdateProductCategory(ProductCategoryMaster productCategory) {
		
		// TODO : Denis : child object dates ?
		
		if(productCategory.getDateCreate() == null){
			productCategory.setDateCreate(new Date());
		}
		productCategory.setDateUpdate(new Date());
		if(productCategory.getId() == null){
			em.persist(productCategory);
		} else {
			em.merge(productCategory);
		}
	}

	public void deleteProductCategory(ProductCategoryMaster productCategory) {
		em.remove(productCategory);
	}
	
	// VIRTUAL
	public ProductCategoryVirtual getVirtualProductCategoryById(Long productCategoryId) {
		return em.find(ProductCategoryVirtual.class, productCategoryId);
	}
	
	public ProductCategoryVirtual getVirtualProductCategoryByCode(String productCategoryCode) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM ProductCategoryVirtual WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", productCategoryCode);
		ProductCategoryVirtual productCategory = (ProductCategoryVirtual) query.uniqueResult();
		return productCategory;
	}
	
	public ProductCategoryVirtual getVirtualProductCategoryByCode(final Long marketAreaId, final Long retailerId, String productCategoryCode) {
		Session session = (Session) em.getDelegate();
		initCategoryVirtualFilter(session, marketAreaId, retailerId);
		String sql = "FROM ProductCategoryVirtual WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", productCategoryCode);
		ProductCategoryVirtual productCategory = (ProductCategoryVirtual) query.uniqueResult();
		return productCategory;
	}
	
	public List<ProductCategoryVirtual> findRootProductCategories(final Long marketAreaId, final Long retailerId) {
		Session session = (Session) em.getDelegate();
		initCategoryVirtualFilter(session, marketAreaId, retailerId);
		String sql = "FROM ProductCategoryVirtual WHERE defaultParentProductCategory is null";
		Query query = session.createQuery(sql);
		List<ProductCategoryVirtual> categories = (List<ProductCategoryVirtual>) query.list();
		return categories;
	}
	
	public List<ProductCategoryVirtual> findProductCategories(final Long marketAreaId, final Long retailerId) {
		Session session = (Session) em.getDelegate();
		initCategoryVirtualFilter(session, marketAreaId, retailerId);
		String sql = "FROM ProductCategoryVirtual";
		Query query = session.createQuery(sql);
		List<ProductCategoryVirtual> categories = (List<ProductCategoryVirtual>) query.list();
		return categories;
	}
	
	public List<ProductCategoryVirtual> findProductCategoriesByProductMarketingId(final Long marketAreaId, final Long retailerId, final Long productMarketingId) {
		Session session = (Session) em.getDelegate();
		initCategoryVirtualFilter(session, marketAreaId, retailerId);
		String sql = "SELECT productCategoryVirtual FROM ProductCategoryVirtual productCategoryVirtual, IN (productCategoryVirtual.productMarketings) AS productMarketing WHERE productMarketing.id = :productMarketingId";
		Query query = session.createQuery(sql);
		query.setLong("productMarketingId", productMarketingId);
		List<ProductCategoryVirtual> categories = (List<ProductCategoryVirtual>) query.list();
		return categories;
	}
	
	public void saveOrUpdateProductCategory(ProductCategoryVirtual productCategory) {
		if(productCategory.getDateCreate() == null){
			productCategory.setDateCreate(new Date());
		}
		productCategory.setDateUpdate(new Date());
		if(productCategory.getId() == null){
			em.persist(productCategory);
		} else {
			em.merge(productCategory);
		}
	}

	public void deleteProductCategory(ProductCategoryVirtual productCategory) {
		em.remove(productCategory);
	}

}
