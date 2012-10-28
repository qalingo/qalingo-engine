/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.UserDao;
import fr.hoteia.qalingo.core.common.domain.User;

@Transactional
@Repository("userDao")
public class UserDaoImpl extends AbstractGenericDaoImpl implements UserDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public User getUserById(Long userId) {
		return em.find(User.class, userId);
	}

	public User getUserByLoginOrEmail(String usernameOrEmail) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM User WHERE (login = :usernameOrEmail OR email = :usernameOrEmail) AND active = true";
		Query query = session.createQuery(sql);
		query.setString("usernameOrEmail", usernameOrEmail);
		User user = (User) query.uniqueResult();
		return user;
	}
	
	public List<User> findUsers() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM User ORDER BY firstname, lastname";
		Query query = session.createQuery(sql);
		List<User> users = (List<User>) query.list();
		return users;
	}
	
	public void saveOrUpdateUser(User user) {
		if(user.getDateCreate() == null){
			user.setDateCreate(new Date());
		}
		user.setDateUpdate(new Date());
		if(user.getId() == null){
			em.persist(user);
		} else {
			em.merge(user);
		}
	}

	public void deleteUser(User user) {
		em.remove(user);
	}

}
