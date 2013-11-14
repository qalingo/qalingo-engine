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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

public abstract class AbstractGenericDaoImpl {

	@PersistenceContext
	protected EntityManager em;

//	/**
//	 * 
//	 * @param <T>
//	 * @param example - a not null example of required instance
//	 * @return
//	 */
//	public <T> List<T> findByExample(T example) {
//		if (example == null) {
//			throw new IllegalArgumentException("findByExample can not be null");
//		}
//		Session session = (Session) em.getDelegate();
//		Criteria crit = session.createCriteria(example.getClass());
//		crit.add(Example.create(example));
//		return (List<T>) crit.list();
//	}
	
	void initProductSkuFilter(Session session, Long marketAreaId, Long retailerId){
		session.enableFilter("filterProductSkuAttributeIsGlobal");
		session.enableFilter("filterProductSkuAttributeByMarketArea").setParameter("marketAreaId", marketAreaId);

		session.enableFilter("filterProductSkuAssetIsGlobal");
		session.enableFilter("filterProductSkuAssetByMarketArea").setParameter("marketAreaId", marketAreaId);

		session.enableFilter("filterProductSkuPriceByMarketAreaAndRetailer");
		session.getEnabledFilter("filterProductSkuPriceByMarketAreaAndRetailer").setParameter("marketAreaId", marketAreaId);
		session.getEnabledFilter("filterProductSkuPriceByMarketAreaAndRetailer").setParameter("retailerId", retailerId);

		session.enableFilter("filterProductSkuStockByMarketAreaAndRetailer");
		session.getEnabledFilter("filterProductSkuStockByMarketAreaAndRetailer").setParameter("marketAreaId", marketAreaId);
		session.getEnabledFilter("filterProductSkuStockByMarketAreaAndRetailer").setParameter("retailerId", retailerId);
	}
	
	void initProductMarketingFilter(Session session, Long marketAreaId, Long retailerId){
		initProductSkuFilter(session, marketAreaId, retailerId);
		
		session.enableFilter("filterProductMarketingAttributeIsGlobal");
		session.enableFilter("filterProductMarketingAttributeByMarketArea").setParameter("marketAreaId", marketAreaId);
		
		session.enableFilter("filterProductMarketingAssetIsGlobal");
		session.enableFilter("filterProductMarketingAssetByMarketArea").setParameter("marketAreaId", marketAreaId);
	}
	
	void initCategoryVirtualFilter(Session session, Long marketAreaId, Long retailerId){
		initProductMarketingFilter(session, marketAreaId, retailerId);
		
		session.enableFilter("filterCatalogVirtualCategoryAttributeIsGlobal");
		session.enableFilter("filterCatalogVirtualCategoryAttributeByMarketArea").setParameter("marketAreaId", marketAreaId);
		
		session.enableFilter("filterCatalogVirtualCategoryAssetIsGlobal");
		session.enableFilter("filterCatalogVirtualCategoryAssetByMarketArea").setParameter("marketAreaId", marketAreaId);
	}
	
	void initCategoryMasterFilter(Session session, Long marketAreaId, Long retailerId){
		initProductMarketingFilter(session, marketAreaId, retailerId);
		
		session.enableFilter("filterCatalogMasterCategoryAttributeIsGlobal");
		session.enableFilter("filterCatalogMasterCategoryAttributeByMarketArea").setParameter("marketAreaId", marketAreaId);
		
		session.enableFilter("filterCatalogMasterCategoryAssetIsGlobal");
		session.enableFilter("filterCatalogMasterCategoryAssetByMarketArea").setParameter("marketAreaId", marketAreaId);
	}
	
	void initRetailerFilter(Session session, Long marketAreaId, Long retailerId){
		initProductSkuFilter(session, marketAreaId, retailerId);
		
		session.enableFilter("filterRetailerAttributeIsGlobal");
		session.enableFilter("filterRetailerAttributeByMarketArea").setParameter("marketAreaId", marketAreaId);
		
		session.enableFilter("filterRetailerAssetIsGlobal");
		session.enableFilter("filterRetailerAssetByMarketArea").setParameter("marketAreaId", marketAreaId);
	}
	
	void initCatalogVirtual(Session session, Long marketAreaId, Long retailerId){
		initCategoryVirtualFilter(session, marketAreaId, retailerId);
	}
}