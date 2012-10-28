/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.ShippingDao;
import fr.hoteia.qalingo.core.common.domain.Shipping;
import fr.hoteia.qalingo.core.common.service.ShippingService;

@Service("shippingService")
@Transactional
public class ShippingServiceImpl implements ShippingService {

	@Autowired
	private ShippingDao shippingDao;

	public Shipping getShippingById(String rawShippingId) {
		long shippingId = -1;
		try {
			shippingId = Long.parseLong(rawShippingId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return shippingDao.getShippingById(shippingId);
	}

	public List<Shipping> findShipping(Shipping criteria) {
		return shippingDao.findByExample(criteria);
	}

	public void saveOrUpdateShipping(Shipping shipping) {
		shippingDao.saveOrUpdateShipping(shipping);
	}

	public void deleteShipping(Shipping shipping) {
		shippingDao.deleteShipping(shipping);
	}

}
