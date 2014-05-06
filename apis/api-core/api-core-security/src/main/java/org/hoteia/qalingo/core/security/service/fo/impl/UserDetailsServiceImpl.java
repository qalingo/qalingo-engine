/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.security.service.fo.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.security.helper.Assembler;
import org.hoteia.qalingo.core.service.CustomerService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
	
	@Autowired
	protected CustomerService customerService;
	
	@Resource(name="assembler")
	protected Assembler assembler;

	@Transactional(readOnly = true)
	public org.springframework.security.core.userdetails.User loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException, DataAccessException {
		Customer customerDetails = null;
		customerDetails = customerService.getCustomerByLoginOrEmail(usernameOrEmail);
		if (customerDetails == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return assembler.buildUserFromCustomerEntity(customerDetails);
	}

}