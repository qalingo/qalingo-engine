/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.dao.UserDao;
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("userDao")
public class UserDaoImpl extends AbstractGenericDaoImpl<User, Long> implements UserDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // USER
    
    public User getUserById(final Long userId) {
        Criteria criteria = createDefaultCriteria(User.class);
        addDefaultUserFetch(criteria);
        criteria.add(Restrictions.eq("id", userId));
        
        User user = (User) criteria.uniqueResult();
        return user;
    }

    public User getUserByLoginOrEmail(final String usernameOrEmail) {
        Criteria criteria = createDefaultCriteria(User.class);
        addDefaultUserFetch(criteria);
        criteria.add(Restrictions.or(Restrictions.eq("login", usernameOrEmail), Restrictions.eq("email", usernameOrEmail)));
        criteria.add(Restrictions.eq("active", true));

        User user = (User) criteria.uniqueResult();
        return user;
    }

    public List<User> findUsers() {
        Criteria criteria = createDefaultCriteria(User.class);
        addDefaultUserFetch(criteria);
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));

        @SuppressWarnings("unchecked")
        List<User> users = criteria.list();
        return users;
    }

    public void saveOrUpdateUser(User user) {
        if (user.getDateCreate() == null) {
            user.setDateCreate(new Date());
        }
        user.setDateUpdate(new Date());
        em.merge(user);
    }

    public void deleteUser(User user) {
        em.remove(user);
    }
    
    private void addDefaultUserFetch(Criteria criteria) {
        criteria.setFetchMode("defaultLocalization", FetchMode.JOIN); 
        criteria.setFetchMode("company", FetchMode.JOIN); 
        criteria.setFetchMode("userGroups", FetchMode.JOIN); 
        
        criteria.createAlias("userGroups.groupRoles", "roles", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("roles", FetchMode.JOIN);
        
        criteria.createAlias("userGroups.groupRoles.rolePermissions", "rolePermissions", JoinType.LEFT_OUTER_JOIN);
        criteria.setFetchMode("rolePermissions", FetchMode.JOIN);

        criteria.setFetchMode("connectionLogs", FetchMode.JOIN); 
    }
    
    // COMPANY
    
    public Company getCompanyById(final Long companyId) {
        Criteria criteria = createDefaultCriteria(Company.class);
        addDefaultCompanyFetch(criteria);
        criteria.add(Restrictions.eq("id", companyId));
        Company company = (Company) criteria.uniqueResult();
        return company;
    }
    
    public List<Company> findCompanies() {
        Criteria criteria = createDefaultCriteria(Company.class);
        addDefaultCompanyFetch(criteria);
        criteria.addOrder(Order.asc("name"));

        @SuppressWarnings("unchecked")
        List<Company> companies = criteria.list();
        return companies;
    }

    private void addDefaultCompanyFetch(Criteria criteria) {
        criteria.setFetchMode("localizations", FetchMode.JOIN); 
    }
    
    public void saveOrUpdateCompany(Company company) {
        if (company.getDateCreate() == null) {
            company.setDateCreate(new Date());
        }
        company.setDateUpdate(new Date());
        em.merge(company);
    }

    public void deleteCompany(Company company) {
        em.remove(company);
    }

}