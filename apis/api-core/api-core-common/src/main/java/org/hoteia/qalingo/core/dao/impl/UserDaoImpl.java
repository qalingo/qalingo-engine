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
import org.hoteia.qalingo.core.dao.UserDao;
import org.hoteia.qalingo.core.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("userDao")
public class UserDaoImpl extends AbstractGenericDaoImpl<User, Long> implements UserDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public User getUserById(Long userId) {
        return em.find(User.class, userId);
    }

    public User getUserByLoginOrEmail(String usernameOrEmail) {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.or(Restrictions.eq("login", usernameOrEmail), Restrictions.eq("email", usernameOrEmail)));
        criteria.add(Restrictions.eq("active", true));
        
        criteria.setFetchMode("defaultLocalization", FetchMode.JOIN);        
        criteria.setFetchMode("company", FetchMode.JOIN);        

        criteria.setFetchMode("userGroups", FetchMode.JOIN);        
        criteria.setFetchMode("connectionLogs", FetchMode.JOIN);

        User user = (User) criteria.uniqueResult();
        
        // String sql =
        // "FROM User WHERE (login = :usernameOrEmail OR email = :usernameOrEmail) AND active = true";
        // Query query = session.createQuery(sql);
        // query.setString("usernameOrEmail", usernameOrEmail);
        // User user = (User) query.uniqueResult();
        
        return user;
    }

    public List<User> findUsers() {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.addOrder(Order.asc("firstname"));
        criteria.addOrder(Order.asc("lastname"));
        
        criteria.setFetchMode("defaultLocalization", FetchMode.JOIN);        
        criteria.setFetchMode("company", FetchMode.JOIN);        

        @SuppressWarnings("unchecked")
        List<User> users = criteria.list();

        // String sql = "FROM User ORDER BY firstname, lastname";
        // Query query = session.createQuery(sql);
        // List<User> users = (List<User>) query.list();
        
        return users;
    }

    public void saveOrUpdateUser(User user) {
        if (user.getDateCreate() == null) {
            user.setDateCreate(new Date());
        }
        user.setDateUpdate(new Date());
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    public void deleteUser(User user) {
        em.remove(user);
    }

}