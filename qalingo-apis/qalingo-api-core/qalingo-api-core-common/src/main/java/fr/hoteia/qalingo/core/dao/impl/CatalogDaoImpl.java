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

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.CatalogDao;
import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;

@Transactional
@Repository("productCatalogDao")
public class CatalogDaoImpl extends AbstractGenericDaoImpl implements CatalogDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public CatalogMaster getProductCatalogById(final Long catalogMasterId) {
		return em.find(CatalogMaster.class, catalogMasterId);
	}

	public CatalogVirtual getCatalogVirtual(final Long marketAreaId, final Long retailerId) {
		Session session = (Session) em.getDelegate();
		initCatalogVirtual(session, marketAreaId, retailerId);
		String sql = "SELECT cv FROM CatalogVirtual cv, MarketArea ma WHERE cv.id = ma.virtualCatalogId AND ma.id = :marketAreaId";
		Query query = session.createQuery(sql);
		query.setLong("marketAreaId", marketAreaId);
		CatalogVirtual catalogVirtual = (CatalogVirtual) query.uniqueResult();
		return catalogVirtual;
	}
	
	public void saveOrUpdateProductCatalog(final CatalogMaster catalogMaster) {
		if(catalogMaster.getDateCreate() == null){
			catalogMaster.setDateCreate(new Date());
		}
		catalogMaster.setDateUpdate(new Date());
		if(catalogMaster.getId() == null){
			em.persist(catalogMaster);
		} else {
			em.merge(catalogMaster);
		}
	}

	public void deleteProductCatalog(final CatalogMaster catalogMaster) {
		em.remove(catalogMaster);
	}

}
