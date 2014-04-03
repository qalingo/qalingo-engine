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
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.UserDao;
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.common.FetchPlanGraphCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends AbstractGenericDaoImpl implements UserDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // USER
    
    public User getUserById(final Long userId, Object... params) {
        Criteria criteria = createDefaultCriteria(User.class);
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);
        criteria.add(Restrictions.eq("id", userId));
        User user = (User) criteria.uniqueResult();
        if(user != null){
            user.setFetchPlan(fetchPlan);
        }
        return user;
    }
    
    public User getUserByCode(final String userCode, Object... params) {
        Criteria criteria = createDefaultCriteria(User.class);
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);
        criteria.add(Restrictions.eq("code", userCode));
        User user = (User) criteria.uniqueResult();
        if(user != null){
            user.setFetchPlan(fetchPlan);
        }
        return user;
    }

    public User getUserByLoginOrEmail(final String usernameOrEmail, Object... params) {
        Criteria criteria = createDefaultCriteria(User.class);
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);
        criteria.add(Restrictions.or(Restrictions.eq("login", usernameOrEmail), Restrictions.eq("email", usernameOrEmail)));
        criteria.add(Restrictions.eq("active", true));
        User user = (User) criteria.uniqueResult();
        if(user != null){
            user.setFetchPlan(fetchPlan);
        }
        return user;
    }

    public List<User> findUsers(Object... params) {
        Criteria criteria = createDefaultCriteria(User.class);
        handleSpecificFetchMode(criteria, params);
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));
        @SuppressWarnings("unchecked")
        List<User> users = criteria.list();
        return users;
    }

    public User saveOrUpdateUser(final User user) {
        if(user.getDateCreate() == null){
            user.setDateCreate(new Date());
            user.setActive(true);
        }
        user.setDateUpdate(new Date());
        if(StringUtils.isEmpty(user.getCode())){
            user.setCode(UUID.randomUUID().toString());
        }

        if (user.getId() != null) {
            if(em.contains(user)){
                em.refresh(user);
            }
            User mergedUser = em.merge(user);
            em.flush();
            return mergedUser;
        } else {
            em.persist(user);
            return user;
        }
    }

    public void deleteUser(final User user) {
        em.remove(user);
    }
    
    @Override
    protected FetchPlan handleSpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCommon.defaultUserFetchPlan());
        }
    }
    
    // COMPANY
    
    public Company getCompanyById(final Long companyId, Object... params) {
        Criteria criteria = createDefaultCriteria(Company.class);
        FetchPlan fetchPlan = handleCompanySpecificFetchMode(criteria, params);
        criteria.add(Restrictions.eq("id", companyId));
        Company company = (Company) criteria.uniqueResult();
        if(company != null){
            company.setFetchPlan(fetchPlan);
        }
        return company;
    }
    
    public List<Company> findCompanies(Object... params) {
        Criteria criteria = createDefaultCriteria(Company.class);
        handleCompanySpecificFetchMode(criteria, params);
        criteria.addOrder(Order.asc("name"));

        @SuppressWarnings("unchecked")
        List<Company> companies = criteria.list();
        return companies;
    }

    protected FetchPlan handleCompanySpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCommon.defaultCompanyFetchPlan());
        }
    }
    
    public Company saveOrUpdateCompany(Company company) {
        if (company.getDateCreate() == null) {
            company.setDateCreate(new Date());
        }
        company.setDateUpdate(new Date());
        if (company.getId() != null) {
            if(em.contains(company)){
                em.refresh(company);
            }
            Company mergedCompany = em.merge(company);
            em.flush();
            return mergedCompany;
        } else {
            em.persist(company);
            return company;
        }
    }

    public void deleteCompany(Company company) {
        em.remove(company);
    }

}