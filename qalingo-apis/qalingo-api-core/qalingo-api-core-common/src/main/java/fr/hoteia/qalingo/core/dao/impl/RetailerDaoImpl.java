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
import fr.hoteia.qalingo.core.domain.RetailerCustomerComment;
import fr.hoteia.qalingo.core.domain.RetailerCustomerRate;
import fr.hoteia.qalingo.core.domain.Store;

@Transactional
@Repository("retailerDao")
public class RetailerDaoImpl extends AbstractGenericDaoImpl implements RetailerDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	// RETAILER

    @Override
    public List<Retailer> findAllRetailers() {
        return em.createQuery("SELECT r FROM Retailer r").getResultList();
    }

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
	
	public List<Retailer> findRetailersByTags(final Long marketAreaId, final Long retailerId, final List<String> tags) {
		Session session = (Session) em.getDelegate();
		initRetailerFilter(session, marketAreaId, retailerId);
		String sql = "SELECT DISTINCT r from  Retailer r JOIN r.retailerTags t WHERE t.code in (:tags) ORDER BY r.name";
		Query query = session.createQuery(sql);
		query.setParameterList("tags", tags);
		List<Retailer> retailers = (List<Retailer>) query.list();
		return retailers;
	}

	public List<Retailer> findLastRetailers(final Long marketAreaId, final Long retailerId, int maxResults) {
		Session session = (Session) em.getDelegate();
		initRetailerFilter(session, marketAreaId, retailerId);
		String sql = "FROM Retailer ORDER BY dateCreate DESC";
		Query query = session.createQuery(sql);
		query.setMaxResults(maxResults);
		List<Retailer> retailers = (List<Retailer>) query.list();
		return retailers;
	}
	
	public List<Retailer> findBestRetailersByQualityOfService(final Long marketAreaId, final Long retailerId, int maxResults) {
		Session session = (Session) em.getDelegate();
		initRetailerFilter(session, marketAreaId, retailerId);
		String sql = "FROM Retailer ORDER BY qualityOfService DESC";
		Query query = session.createQuery(sql);
		query.setMaxResults(maxResults);
		List<Retailer> retailers = (List<Retailer>) query.list();
		return retailers;
	}
	
	public List<Retailer> findBestRetailersByQualityPrice(final Long marketAreaId, final Long retailerId, int maxResults) {
		Session session = (Session) em.getDelegate();
		initRetailerFilter(session, marketAreaId, retailerId);
		String sql = "FROM Retailer ORDER BY ratioQualityPrice DESC";
		Query query = session.createQuery(sql);
		query.setMaxResults(maxResults);
		List<Retailer> retailers = (List<Retailer>) query.list();
		return retailers;
	}
	
	public List<Retailer> findRetailersByTxt(final Long marketAreaId, final Long retailerId, final String searchTxt) {
		Session session = (Session) em.getDelegate();
		initRetailerFilter(session, marketAreaId, retailerId);
		String sql = "FROM Retailer WHERE LOWER(code) LIKE LOWER(:searchTxt) OR LOWER(name) LIKE LOWER(:searchTxt) OR LOWER(description) LIKE LOWER(:searchTxt) ORDER BY name";
		Query query = session.createQuery(sql);
		query.setString("searchTxt", "%" + searchTxt + "%");
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
	
	public void saveOrUpdateRetailerCustomerRate(final RetailerCustomerRate retailerCustomerRate) {
		if(retailerCustomerRate.getId() == null){
			em.persist(retailerCustomerRate);
		} else {
			em.merge(retailerCustomerRate);
		}
	}

	public void deleteRetailerCustomerRate(final RetailerCustomerRate retailerCustomerRate) {
		em.remove(retailerCustomerRate);
	}
	
	public void saveOrUpdateRetailerCustomerComment(final RetailerCustomerComment retailerCustomerComment) {
		if(retailerCustomerComment.getDateCreate() == null){
			retailerCustomerComment.setDateCreate(new Date());
		}
		retailerCustomerComment.setDateUpdate(new Date());
		if(retailerCustomerComment.getId() == null){
			em.persist(retailerCustomerComment);
		} else {
			em.merge(retailerCustomerComment);
		}
	}

	public void deleteRetailerCustomerComment(final RetailerCustomerComment retailerCustomerComment) {
		em.remove(retailerCustomerComment);
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