/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.service.UserService;
import fr.hoteia.qalingo.web.mvc.form.UserForm;
import fr.hoteia.qalingo.web.service.WebBackofficeService;

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
