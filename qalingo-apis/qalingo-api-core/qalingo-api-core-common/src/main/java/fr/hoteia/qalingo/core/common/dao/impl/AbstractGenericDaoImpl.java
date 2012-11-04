/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

public abstract class AbstractGenericDaoImpl {

	@PersistenceContext
	protected EntityManager em;

	/**
	 * 
	 * @param <T>
	 * @param example - a not null example of required instance
	 * @return
	 */
	public <T> List<T> findByExample(T example) {
		if (example == null) {
			throw new IllegalArgumentException("findByExample can not be null");
		}
		Session session = (Session) em.getDelegate();
		Criteria crit = session.createCriteria(example.getClass());
		crit.add(Example.create(example));
		return (List<T>) crit.list();
	}
	
	void initProductSkuFilter(Session session, Long marketAreaId, Long retailerId){
		session.enableFilter("filterProductSkuAttributeIsGlobal");
		
		session.enableFilter("filterProductSkuAttributeByMarketArea");
		session.getEnabledFilter("filterProductSkuAttributeByMarketArea").setParameter("marketAreaId", marketAreaId);

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
	}
	
	void initCategoryFilter(Session session, Long marketAreaId, Long retailerId){
		initProductMarketingFilter(session, marketAreaId, retailerId);
		
		session.enableFilter("filterProductCategoryVirtualAttributeIsGlobal");
		session.enableFilter("filterProductCategoryVirtualAttributeByMarketArea").setParameter("marketAreaId", marketAreaId);
	}
	
	void initCatalogVirtual(Session session, Long marketAreaId, Long retailerId){
		initCategoryFilter(session, marketAreaId, retailerId);
	}
}
