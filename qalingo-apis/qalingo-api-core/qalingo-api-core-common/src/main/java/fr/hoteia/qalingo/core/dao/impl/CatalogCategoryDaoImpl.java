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

import fr.hoteia.qalingo.core.dao.CatalogCategoryDao;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;

@Transactional
@Repository("catalogCategoryDao")
public class CatalogCategoryDaoImpl extends AbstractGenericDaoImpl implements CatalogCategoryDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// MASTER
	public CatalogCategoryMaster getMasterCatalogCategoryById(Long catalogCategoryId) {
		return em.find(CatalogCategoryMaster.class, catalogCategoryId);
	}
	
	public CatalogCategoryMaster getMasterCatalogCategoryByCode(String catalogCategoryCode) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM CatalogCategoryMaster WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", catalogCategoryCode);
		CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) query.uniqueResult();
		return catalogCategory;
	}
	
	public CatalogCategoryMaster getMasterCatalogCategoryByCode(final Long marketAreaId, final Long retailerId, String catalogCategoryCode) {
		Session session = (Session) em.getDelegate();
		initCategoryMasterFilter(session, marketAreaId, retailerId);
		String sql = "FROM CatalogCategoryMaster WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", catalogCategoryCode);
		CatalogCategoryMaster catalogCategory = (CatalogCategoryMaster) query.uniqueResult();
		return catalogCategory;
	}
	
	public List<CatalogCategoryMaster> findRootCatalogCategories() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM CatalogCategoryMaster WHERE defaultParentCatalogCategory is null";
		Query query = session.createQuery(sql);
		List<CatalogCategoryMaster> categories = (List<CatalogCategoryMaster>) query.list();
		return categories;
	}
	
	public List<CatalogCategoryMaster> findMasterCategoriesByMarketIdAndRetailerId(final Long marketAreaId, final Long retailerId) {
		Session session = (Session) em.getDelegate();
		initCategoryVirtualFilter(session, marketAreaId, retailerId);
		String sql = "SELECT catalogCategoryMaster FROM CatalogCategoryMaster catalogCategoryMaster";
		Query query = session.createQuery(sql);
		List<CatalogCategoryMaster> categories = (List<CatalogCategoryMaster>) query.list();
		return categories;
	}
	
	public void saveOrUpdateCatalogCategory(CatalogCategoryMaster catalogCategory) {
		
		// TODO : Denis : child object dates ?
		
		if(catalogCategory.getDateCreate() == null){
			catalogCategory.setDateCreate(new Date());
		}
		catalogCategory.setDateUpdate(new Date());
		if(catalogCategory.getId() == null){
			em.persist(catalogCategory);
		} else {
			em.merge(catalogCategory);
		}
	}

	public void deleteCatalogCategory(CatalogCategoryMaster catalogCategory) {
		em.remove(catalogCategory);
	}
	
	// VIRTUAL
	public CatalogCategoryVirtual getVirtualCatalogCategoryById(Long catalogCategoryId) {
		return em.find(CatalogCategoryVirtual.class, catalogCategoryId);
	}
	
	public CatalogCategoryVirtual getVirtualCatalogCategoryByCode(String catalogCategoryCode) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM CatalogCategoryVirtual WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", catalogCategoryCode);
		CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) query.uniqueResult();
		return catalogCategory;
	}
	
	public CatalogCategoryVirtual getVirtualCatalogCategoryByCode(final Long marketAreaId, final Long retailerId, String catalogCategoryCode) {
		Session session = (Session) em.getDelegate();
		initCategoryVirtualFilter(session, marketAreaId, retailerId);
		String sql = "FROM CatalogCategoryVirtual WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", catalogCategoryCode);
		CatalogCategoryVirtual catalogCategory = (CatalogCategoryVirtual) query.uniqueResult();
		return catalogCategory;
	}
	
	public List<CatalogCategoryVirtual> findRootCatalogCategories(final Long marketAreaId, final Long retailerId) {
		Session session = (Session) em.getDelegate();
		initCategoryVirtualFilter(session, marketAreaId, retailerId);
		String sql = "FROM CatalogCategoryVirtual WHERE defaultParentCatalogCategory is null";
		Query query = session.createQuery(sql);
		List<CatalogCategoryVirtual> categories = (List<CatalogCategoryVirtual>) query.list();
		return categories;
	}
	
	public List<CatalogCategoryVirtual> findCatalogCategories(final Long marketAreaId, final Long retailerId) {
		Session session = (Session) em.getDelegate();
		initCategoryVirtualFilter(session, marketAreaId, retailerId);
		String sql = "FROM CatalogCategoryVirtual";
		Query query = session.createQuery(sql);
		List<CatalogCategoryVirtual> categories = (List<CatalogCategoryVirtual>) query.list();
		return categories;
	}
	
	public List<CatalogCategoryVirtual> findCatalogCategoriesByProductMarketingId(final Long marketAreaId, final Long retailerId, final Long productMarketingId) {
		Session session = (Session) em.getDelegate();
		initCategoryVirtualFilter(session, marketAreaId, retailerId);
		String sql = "SELECT catalogCategoryVirtual FROM CatalogCategoryVirtual catalogCategoryVirtual, IN (catalogCategoryVirtual.productMarketings) AS productMarketing WHERE productMarketing.id = :productMarketingId";
		Query query = session.createQuery(sql);
		query.setLong("productMarketingId", productMarketingId);
		List<CatalogCategoryVirtual> categories = (List<CatalogCategoryVirtual>) query.list();
		return categories;
	}
	
	public void saveOrUpdateCatalogCategory(CatalogCategoryVirtual catalogCategory) {
		if(catalogCategory.getDateCreate() == null){
			catalogCategory.setDateCreate(new Date());
		}
		catalogCategory.setDateUpdate(new Date());
		if(catalogCategory.getId() == null){
			em.persist(catalogCategory);
		} else {
			em.merge(catalogCategory);
		}
	}

	public void deleteCatalogCategory(CatalogCategoryVirtual catalogCategory) {
		em.remove(catalogCategory);
	}

}
