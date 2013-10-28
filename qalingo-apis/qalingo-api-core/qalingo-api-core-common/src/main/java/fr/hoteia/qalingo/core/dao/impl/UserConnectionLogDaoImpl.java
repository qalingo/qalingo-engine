/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.UserConnectionLogDao;
import fr.hoteia.qalingo.core.domain.UserConnectionLog;

@Transactional
@Repository("userConnectionLogDao")
public class UserConnectionLogDaoImpl extends AbstractGenericDaoImpl implements UserConnectionLogDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public UserConnectionLog getUserConnectionLogById(final Long userConnectionLogId) {
		return em.find(UserConnectionLog.class, userConnectionLogId);
	}

//	public List<UserConnectionLog> findByExample(UserConnectionLog userConnectionLogExample) {
//		return super.findByExample(userConnectionLogExample);
//	}
	
	public List<UserConnectionLog> findUserConnectionLogsByUserId(final Long userId) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM UserConnectionLog WHERE userId = :userId ORDER BY loginDate";
		Query query = session.createQuery(sql);
		query.setLong("userId", userId);
		List<UserConnectionLog> userConnectionLogs = (List<UserConnectionLog>) query.list();
		return userConnectionLogs;
	}
	
	public List<UserConnectionLog> findUserConnectionLogsByUserIdAndAppCode(final Long userId, final String appCode) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM UserConnectionLog WHERE userId = :userId AND app = :appCode ORDER BY loginDate";
		Query query = session.createQuery(sql);
		query.setLong("userId", userId);
		query.setString("appCode", appCode);
		List<UserConnectionLog> userConnectionLogs = (List<UserConnectionLog>) query.list();
		return userConnectionLogs;
	}

	public void saveOrUpdateUserConnectionLog(final UserConnectionLog userConnectionLog) {
		if(userConnectionLog.getId() == null){
			em.persist(userConnectionLog);
		} else {
			em.merge(userConnectionLog);
		}
	}

	public void deleteUserConnectionLog(final UserConnectionLog userConnectionLog) {
		em.remove(userConnectionLog);
	}

}
