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

import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.User;

public interface UserService {

    // USER
    
    User getUserById(Long userId, Object... params);

    User getUserById(String userId, Object... params);

    User getUserByCode(String userCode, Object... params);

    User getUserByLoginOrEmail(String loginOrEmail, Object... params);

	List<User> findUsers(Object... params);
	
	User saveOrUpdateUser(User user);
	
	void deleteUser(User user);
	
    // COMPANY

    Company getCompanyById(Long companyId, Object... params);

    Company getCompanyById(String companyId, Object... params);

    List<Company> findCompanies(Object... params);

    Company saveOrUpdateCompany(Company company);

    void deleteCompany(Company company);

}
