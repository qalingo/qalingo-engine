/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.hoteia.qalingo.core.dao.UserDao;
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	// USER
	
    public User getUserById(Long userId, Object... params) {
        return userDao.getUserById(userId, params);
    }
    
	public User getUserById(String rawUserId, Object... params) {
		long userId = -1;
		try {
			userId = Long.parseLong(rawUserId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return getUserById(userId, params);
	}
	
    public User getUserByCode(String userCode, Object... params) {
        return userDao.getUserByCode(userCode, params);
    }
    
	public User getUserByLoginOrEmail(String usernameOrEmail, Object... params) {
		return userDao.getUserByLoginOrEmail(usernameOrEmail, params);
	}
	
	public List<User> findUsers(Object... params) {
		return userDao.findUsers(params);
	}

	public User saveOrUpdateUser(User user) {
	    return userDao.saveOrUpdateUser(user);
	}

	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}
	
	// COMPANY
	
	public Company getCompanyById(Long companyId, Object... params) {
	    return userDao.getCompanyById(companyId, params);
	}
	
    public Company getCompanyById(String rawCompanyId, Object... params) {
        long companyId = -1;
        try {
            companyId = Long.parseLong(rawCompanyId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getCompanyById(companyId);
    }
    
    public List<Company> findCompanies(Object... params) {
        return userDao.findCompanies(params);
    }

    public Company saveOrUpdateCompany(Company company) {
        return userDao.saveOrUpdateCompany(company);
    }

    public void deleteCompany(Company company) {
        userDao.deleteCompany(company);
    }

}