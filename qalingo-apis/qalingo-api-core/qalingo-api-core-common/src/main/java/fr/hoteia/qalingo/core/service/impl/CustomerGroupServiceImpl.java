/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.CustomerGroupDao;
import fr.hoteia.qalingo.core.domain.CustomerGroup;
import fr.hoteia.qalingo.core.service.CustomerGroupService;

@Service("customerGroupService")
@Transactional
public class CustomerGroupServiceImpl implements CustomerGroupService {

	@Autowired
	private CustomerGroupDao customerGroupDao;

	public CustomerGroup getCustomerGroupById(String rawCustomerGroupId) {
		long customerGroupId = -1;
		try {
			customerGroupId = Long.parseLong(rawCustomerGroupId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return customerGroupDao.getCustomerGroupById(customerGroupId);
	}

	public CustomerGroup getCustomerGroupByCode(String code) {
		return customerGroupDao.getCustomerGroupByCode(code);
	}
	
	public List<CustomerGroup> findCustomerGroup(CustomerGroup criteria) {
		return customerGroupDao.findByExample(criteria);
	}

	public void saveOrUpdateCustomerGroup(CustomerGroup customerGroup) {
		customerGroupDao.saveOrUpdateCustomerGroup(customerGroup);
	}

	public void deleteCustomerGroup(CustomerGroup customerGroup) {
		customerGroupDao.deleteCustomerGroup(customerGroup);
	}

}
