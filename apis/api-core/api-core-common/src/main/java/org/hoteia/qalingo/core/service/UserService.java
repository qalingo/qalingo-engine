/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.dao.UserDao;
import org.hoteia.qalingo.core.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserService {

	@Autowired
	protected UserDao userDao;

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
    
	public User getUserActivedByLoginOrEmail(String usernameOrEmail, Object... params) {
		return userDao.getUserActivedByLoginOrEmail(usernameOrEmail, params);
	}

    public Long getMaxUserId() {
        return userDao.getMaxUserId();
    }

	public List<User> findUsers(Object... params) {
		return userDao.findUsers(params);
	}

    public List<User> findUsersByCompanyId(final Long companyId, Object... params) {
        return userDao.findUsersByCompanyId(companyId, params);
    }
    
	public User saveOrUpdateUser(User user) {
	    return userDao.saveOrUpdateUser(user);
	}
	
    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}
	
	// USER GROUP
	
    public UserGroup getUserGroupById(final Long userGroupId, Object... params) {
        return userDao.getUserGroupById(userGroupId, params);
    }

    public UserGroup getUserGroupById(final String rawUserGroupId, Object... params) {
        long userGroupId = -1;
        try {
            userGroupId = Long.parseLong(rawUserGroupId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getUserGroupById(userGroupId, params);
    }

    public UserGroup getUserGroupByCode(final String code, Object... params) {
        return userDao.getUserGroupByCode(code, params);
    }

    public void saveOrUpdateUserGroup(final UserGroup userGroup) {
        userDao.saveOrUpdateUserGroup(userGroup);
    }

    public void deleteUserGroup(final UserGroup userGroup) {
        userDao.deleteUserGroup(userGroup);
    }
    
	// COMPANY
	
	public Company getCompanyById(Long companyId, Object... params) {
	    return userDao.getCompanyById(companyId, params);
	}
	
    public Company getCompanyById(String rawCompanyId, Object... params) {
        long companyId;
        try {
            companyId = Long.parseLong(rawCompanyId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getCompanyById(companyId);
    }
    
    public Company getCompanyByCode(String companyCode, Object... params) {
        return userDao.getCompanyByCode(companyCode, params);
    }

    public Company getCompanyByName(final String companyName, Object... params) {
        return userDao.getCompanyByName(companyName, params);
    }
    
    public List<Company> findCompanyByAddress(final String address, Object... params) {
        return userDao.findCompanyByAddress(address, params);
    }
    
    public List<Company> findCompanyByAddressAndPostalCode(final String address, final String postalCode, Object... params) {
        return userDao.findCompanyByAddressAndPostalCode(address, postalCode, params);
    }
    
    public Long getMaxCompanyId() {
        return userDao.getMaxCompanyId();
    }
    
    public List<Company> findCompanies(Object... params) {
        return userDao.findCompanies(params);
    }
    
    public List<Company> findCompaniesByText(String text, Object... params) {
        return userDao.findCompaniesByText(text, params);
    }

    public List<CompanyAttribute> findCompanyAttributeByDefinitionCode(String definitionCode, Object... params) {
        return userDao.findCompanyAttributeByDefinitionCode(definitionCode, params);
    }

    public Company saveOrUpdateCompany(Company company) {
        return userDao.saveOrUpdateCompany(company);
    }
    
    public Company updateCompany(Company company) {
        return userDao.updateCompany(company);
    }

    public void deleteCompany(Company company) {
        userDao.deleteCompany(company);
    }
    
    // CREDENTIAL

    public UserCredential saveOrUpdateUserCredential(final UserCredential userCredential) throws Exception {
        return userDao.saveOrUpdateUserCredential(userCredential);
    }
    
    // TOKEN

    public UserToken saveOrUpdateUserToken(final UserToken userToken) throws Exception {
        return userDao.saveOrUpdateUserToken(userToken);
    }

}