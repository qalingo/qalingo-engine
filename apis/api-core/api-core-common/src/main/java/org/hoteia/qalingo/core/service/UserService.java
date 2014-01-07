/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.User;

public interface UserService {

    // USER
    
    User getUserById(Long userId);

    User getUserById(String userId);

	User getUserByLoginOrEmail(String loginOrEmail);

	List<User> findUsers();
	
	void saveOrUpdateUser(User user);
	
	void deleteUser(User user);
	
    // COMPANY

    Company getCompanyById(Long companyId);

    Company getCompanyById(String companyId);

    List<Company> findCompanies();

    void saveOrUpdateCompany(Company company);

    void deleteCompany(Company company);

}
