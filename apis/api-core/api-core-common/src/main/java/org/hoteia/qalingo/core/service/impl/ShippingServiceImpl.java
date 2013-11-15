/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.ShippingDao;
import org.hoteia.qalingo.core.domain.Shipping;
import org.hoteia.qalingo.core.service.ShippingService;

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

	public Shipping getShippingByCode(String code) {
		return shippingDao.getShippingByCode(code);
	}
	
	public List<Shipping> findShippings() {
		return shippingDao.findShippings();
	}

	public void saveOrUpdateShipping(Shipping shipping) {
		shippingDao.saveOrUpdateShipping(shipping);
	}

	public void deleteShipping(Shipping shipping) {
		shippingDao.deleteShipping(shipping);
	}

}
