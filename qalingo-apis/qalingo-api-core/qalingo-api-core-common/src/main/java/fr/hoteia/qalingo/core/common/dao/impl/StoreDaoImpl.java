/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.StoreDao;
import fr.hoteia.qalingo.core.common.domain.Store;

@Transactional
@Repository("storeDao")
public class StoreDaoImpl extends AbstractGenericDaoImpl implements StoreDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public Store getStoreById(Long storeId) {
		return em.find(Store.class, storeId);
	}

	public Store getStoreByCode(String storeCode) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Store WHERE code = :storeCode";
		Query query = session.createQuery(sql);
		query.setString("storeCode", storeCode);
		Store store = (Store) query.uniqueResult();
		return store;
	}
	
	public List<Store> findByExample(Store storeExample) {
		return super.findByExample(storeExample);
	}
	
	public List<Store> findStores() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Store ORDER BY code";
		Query query = session.createQuery(sql);
		List<Store> stores = (List<Store>) query.list();
		return stores;
	}

	public void saveOrUpdateStore(Store store) {
		if(store.getDateCreate() == null){
			store.setDateCreate(new Date());
		}
		store.setDateUpdate(new Date());
		if(store.getId() == null){
			em.persist(store);
		} else {
			em.merge(store);
		}
	}

	public void deleteStore(Store store) {
		em.remove(store);
	}

}
