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

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.ShippingDao;
import fr.hoteia.qalingo.core.domain.Shipping;

@Transactional
@Repository("shippingDao")
public class ShippingDaoImpl extends AbstractGenericDaoImpl implements ShippingDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public Shipping getShippingById(Long shippingId) {
		return em.find(Shipping.class, shippingId);
	}

	public Shipping getShippingByCode(String code) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Shipping WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", code);
		Shipping shipping = (Shipping) query.uniqueResult();
		return shipping;
	}
	
	public List<Shipping> findShippings() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Shipping";
		Query query = session.createQuery(sql);
		List<Shipping> shippings = (List<Shipping>) query.list();
		return shippings;
	}

	public void saveOrUpdateShipping(Shipping shipping) {
		if(shipping.getId() == null){
			em.persist(shipping);
		} else {
			em.merge(shipping);
		}
	}

	public void deleteShipping(Shipping shipping) {
		em.remove(shipping);
	}

}
