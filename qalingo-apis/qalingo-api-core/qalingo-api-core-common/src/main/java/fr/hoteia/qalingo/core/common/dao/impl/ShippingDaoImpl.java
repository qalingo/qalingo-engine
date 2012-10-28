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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.ShippingDao;
import fr.hoteia.qalingo.core.common.domain.Shipping;

@Transactional
@Repository("shippingDao")
public class ShippingDaoImpl extends AbstractGenericDaoImpl implements ShippingDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public Shipping getShippingById(Long shippingId) {
		return em.find(Shipping.class, shippingId);
	}

	public List<Shipping> findByExample(Shipping shippingExample) {
		return super.findByExample(shippingExample);
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
