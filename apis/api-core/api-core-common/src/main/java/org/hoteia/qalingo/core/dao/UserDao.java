/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.List;

import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.User;

public interface UserDao extends GenericDao<User, Long> {

    // USER
    
	User getUserById(Long userId);

	User getUserByLoginOrEmail(String usernameOrEmail);
	 
	List<User> findUsers();
	
	void saveOrUpdateUser(User user);

	void deleteUser(User user);
	
	// COMPANY

    Company getCompanyById(Long companyId);

    List<Company> findCompanies();

    void saveOrUpdateCompany(Company company);

    void deleteCompany(Company company);

}