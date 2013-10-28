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

import fr.hoteia.qalingo.core.dao.UserDao;
import fr.hoteia.qalingo.core.domain.User;
import fr.hoteia.qalingo.core.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public User getUserById(String rawUserId) {
		long userId = -1;
		try {
			userId = Long.parseLong(rawUserId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return userDao.getUserById(userId);
	}
	
	public User getUserByLoginOrEmail(String usernameOrEmail) {
		return userDao.getUserByLoginOrEmail(usernameOrEmail);
	}

//	public List<User> findUser(User criteria) {
//		return userDao.findByExample(criteria);
//	}
	
	public List<User> findUsers() {
		return userDao.findUsers();
	}

	public void saveOrUpdateUser(User user) {
		userDao.saveOrUpdateUser(user);
	}

	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}

}
