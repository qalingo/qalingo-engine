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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.service.UserService;
import org.hoteia.qalingo.core.service.WebBackofficeService;
import org.hoteia.qalingo.web.mvc.form.UserForm;

@Service("webCommerceService")
@Transactional
public class WebBackofficeServiceImpl implements WebBackofficeService {

	@Autowired
	protected UserService userService;
	
	public void updateUser(final User user, final UserForm userForm) {
		user.setLogin(userForm.getLogin());
		user.setLastname(userForm.getLastname());
		user.setFirstname(userForm.getFirstname());
		user.setEmail(userForm.getEmail());
		userService.saveOrUpdateUser(user);
	}
	
}
