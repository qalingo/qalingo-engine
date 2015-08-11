/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.UserCredential;
import org.hoteia.qalingo.core.domain.UserGroup;
import org.hoteia.qalingo.core.domain.UserToken;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.user.FetchPlanGraphUser;
import org.hoteia.qalingo.core.util.CoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao extends AbstractGenericDao {

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
        criteria.add(Restrictions.eq("code", handleCodeValue(userCode)));
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
        User user = (User) criteria.uniqueResult();
        if(user != null){
            user.setFetchPlan(fetchPlan);
        }
        return user;
    }
    
    public User getUserActivedByLoginOrEmail(final String usernameOrEmail, Object... params) {
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
    
    public Long getMaxUserId() {
        Criteria criteria = createDefaultCriteria(User.class);
        criteria.setProjection(Projections.max("id"));
        Long maxId = (Long)criteria.uniqueResult();
        return (maxId == null) ? new Long(0) : maxId;
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
    
    public List<User> findUsersByCompanyId(final Long companyId, Object... params) {
        Criteria criteria = createDefaultCriteria(User.class);
        handleSpecificFetchMode(criteria, params);
        criteria.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("company.id", companyId));
        criteria.addOrder(Order.asc("lastname"));
        criteria.addOrder(Order.asc("firstname"));
        @SuppressWarnings("unchecked")
        List<User> users = criteria.list();
        return users;
    }

    public User saveOrUpdateUser(final User user) {
        if(user.getDateCreate() == null){
            user.setDateCreate(new Date());
        }
        user.setDateUpdate(new Date());
        if(StringUtils.isEmpty(user.getCode())){
            user.setCode(CoreUtil.generateEntityCode());
        }

        if (user.getId() != null) {
//            if(em.contains(user)){
//                em.refresh(user);
//            }
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
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphUser.defaultUserFetchPlan());
        }
    }
    
    // USER GROUP
    
    public UserGroup getUserGroupById(final Long userGroupId, Object... params) {
        Criteria criteria = createDefaultCriteria(UserGroup.class);
        
        FetchPlan fetchPlan = handleSpecificUserGroupFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("id", userGroupId));
        UserGroup userGroup = (UserGroup) criteria.uniqueResult();
        if(userGroup != null){
            userGroup.setFetchPlan(fetchPlan);
        }
        return userGroup;
    }
    
    public UserGroup getUserGroupByCode(final String code, Object... params) {
        Criteria criteria = createDefaultCriteria(UserGroup.class);
        
        FetchPlan fetchPlan = handleSpecificUserGroupFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("code", handleCodeValue(code)));
        UserGroup userGroup = (UserGroup) criteria.uniqueResult();
        if(userGroup != null){
            userGroup.setFetchPlan(fetchPlan);
        }
        return userGroup;
    }
    
    public UserGroup saveOrUpdateUserGroup(UserGroup userGroup) {
        if(userGroup.getDateCreate() == null){
            userGroup.setDateCreate(new Date());
        }
        userGroup.setDateUpdate(new Date());
        if (userGroup.getId() != null) {
//            if(em.contains(userGroup)){
//                em.refresh(userGroup);
//            }
            UserGroup mergedUserGroup = em.merge(userGroup);
            em.flush();
            return mergedUserGroup;
        } else {
            em.persist(userGroup);
            return userGroup;
        }
    }

    public void deleteUserGroup(UserGroup userGroup) {
        em.remove(userGroup);
    }
    
    protected FetchPlan handleSpecificUserGroupFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphUser.defaultUserGroupFetchPlan());
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
    
    public Company getCompanyByCode(final String companyCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Company.class);
        FetchPlan fetchPlan = handleCompanySpecificFetchMode(criteria, params);
        criteria.add(Restrictions.eq("code", handleCodeValue(companyCode)));
        Company company = (Company) criteria.uniqueResult();
        if(company != null){
            company.setFetchPlan(fetchPlan);
        }
        return company;
    }
    
    public Company getCompanyByName(final String companyName, Object... params) {
        Criteria criteria = createDefaultCriteria(Company.class);
        FetchPlan fetchPlan = handleCompanySpecificFetchMode(criteria, params);
        criteria.add(Restrictions.eq("name", companyName));
        Company company = (Company) criteria.uniqueResult();
        if(company != null){
            company.setFetchPlan(fetchPlan);
        }
        return company;
    }
    
    public Company findCompanyByAddress(final String address, Object... params) {
        Criteria criteria = createDefaultCriteria(Company.class);
        FetchPlan fetchPlan = handleCompanySpecificFetchMode(criteria, params);
        criteria.add(Restrictions.eq("address1", address));
        Company company = (Company) criteria.uniqueResult();
        if(company != null){
            company.setFetchPlan(fetchPlan);
        }
        return company;
    }
    
    public Long getMaxCompanyId() {
        Criteria criteria = createDefaultCriteria(Company.class);
        criteria.setProjection(Projections.max("id"));
        Long maxId = (Long)criteria.uniqueResult();
        return (maxId == null) ? new Long(0) : maxId;
    }
    
    public List<Company> findCompanies(Object... params) {
        Criteria criteria = createDefaultCriteria(Company.class);
        handleCompanySpecificFetchMode(criteria, params);
        criteria.addOrder(Order.asc("name"));

        @SuppressWarnings("unchecked")
        List<Company> companies = criteria.list();
        return companies;
    }
    
    public List<Company> findCompaniesByText(String text, Object... params) {
        Criteria criteria = createDefaultCriteria(Company.class);
        handleCompanySpecificFetchMode(criteria, params);
        criteria.add(Restrictions.or(Restrictions.like("code", text, MatchMode.ANYWHERE), Restrictions.like("name", text, MatchMode.ANYWHERE), Restrictions.like("description", text, MatchMode.ANYWHERE)));

        @SuppressWarnings("unchecked")
        List<Company> companies = criteria.list();
        return companies;
    }

    public Company saveOrUpdateCompany(Company company) {
        if (company.getDateCreate() == null) {
            company.setDateCreate(new Date());
        }
        company.setDateUpdate(new Date());
        if(StringUtils.isEmpty(company.getCode())){
            company.setCode(CoreUtil.generateEntityCode());
        }
        
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
    
    // CREDENTIAL
    
    public UserCredential saveOrUpdateUserCredential(final UserCredential userCredential) throws Exception {
        if(userCredential.getDateCreate() == null){
            userCredential.setDateCreate(new Date());
            if(StringUtils.isEmpty(userCredential.getResetToken())){
                userCredential.setResetToken(UUID.randomUUID().toString());
            }
        }
        userCredential.setDateUpdate(new Date());
        if (userCredential.getId() != null) {
            if(em.contains(userCredential)){
                em.refresh(userCredential);
            }
            UserCredential mergedUserCredential = em.merge(userCredential);
            em.flush();
            return mergedUserCredential;
        } else {
            em.persist(userCredential);
            return userCredential;
        }
    }
    
    // TOKEN
    
    public UserToken saveOrUpdateUserToken(final UserToken userToken) throws Exception {
        if(userToken.getDateCreate() == null){
            userToken.setDateCreate(new Date());
        }
        userToken.setDateUpdate(new Date());
        if (userToken.getId() != null) {
            if(em.contains(userToken)){
                em.refresh(userToken);
            }
            UserToken mergedUserToken = em.merge(userToken);
            em.flush();
            return mergedUserToken;
        } else {
            em.persist(userToken);
            return userToken;
        }
    }
    
    protected FetchPlan handleCompanySpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphUser.defaultCompanyFetchPlan());
        }
    }

}