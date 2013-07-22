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

import fr.hoteia.qalingo.core.dao.RetailerDao;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.domain.Store;

@Transactional
@Repository("retailerDao")
public class RetailerDaoImpl extends AbstractGenericDaoImpl implements RetailerDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	// RETAILER
	
	public Retailer getRetailerById(final Long retailerId) {
		return em.find(Retailer.class, retailerId);
	}

	public Retailer getRetailerByCode(final Long marketAreaId, final Long retailerId, final String retailerCode) {
		Session session = (Session) em.getDelegate();
		initRetailerFilter(session, marketAreaId, retailerId);
		String sql = "FROM Retailer WHERE code = :retailerCode";
		Query query = session.createQuery(sql);
		query.setString("retailerCode", retailerCode);
		Retailer retailer = (Retailer) query.uniqueResult();
		return retailer;
	}
	
	public List<Retailer> findRetailers(final Long marketAreaId, final Long retailerId) {
		Session session = (Session) em.getDelegate();
		initRetailerFilter(session, marketAreaId, retailerId);
		String sql = "FROM Retailer ORDER BY code";
		Query query = session.createQuery(sql);
		List<Retailer> retailers = (List<Retailer>) query.list();
		return retailers;
	}
	
	public List<Retailer> findRetailersByTag(final Long marketAreaId, final Long retailerId, final String tag) {
		Session session = (Session) em.getDelegate();
		initRetailerFilter(session, marketAreaId, retailerId);
		String sql = "FROM Retailer r WHERE r.retailerTags.code = :tag ORDER BY code";
		Query query = session.createQuery(sql);
		query.setString("tag", tag);
		List<Retailer> retailers = (List<Retailer>) query.list();
		return retailers;
	}

	public void saveOrUpdateRetailer(final Retailer retailer) {
		if(retailer.getDateCreate() == null){
			retailer.setDateCreate(new Date());
		}
		retailer.setDateUpdate(new Date());
		if(retailer.getId() == null){
			em.persist(retailer);
		} else {
			em.merge(retailer);
		}
	}

	public void deleteRetailer(final Retailer retailer) {
		em.remove(retailer);
	}
	
	// STORE
	
	public Store getStoreById(final Long storeId) {
		return em.find(Store.class, storeId);
	}

	public Store getStoreByCode(final String storeCode) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Store WHERE code = :storeCode";
		Query query = session.createQuery(sql);
		query.setString("storeCode", storeCode);
		Store store = (Store) query.uniqueResult();
		return store;
	}
	
	public List<Store> findStores() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Store ORDER BY code";
		Query query = session.createQuery(sql);
		List<Store> stores = (List<Store>) query.list();
		return stores;
	}

	public void saveOrUpdateStore(final Store store) {
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

	public void deleteStore(final Store store) {
		em.remove(store);
	}

}