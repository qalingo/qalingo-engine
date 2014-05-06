/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.UserConnectionLogDao;
import org.hoteia.qalingo.core.domain.UserConnectionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("userConnectionLogDao")
public class UserConnectionLogDaoImpl extends AbstractGenericDaoImpl implements UserConnectionLogDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public UserConnectionLog getUserConnectionLogById(final Long userConnectionLogId, Object... params) {
        Criteria criteria = createDefaultCriteria(UserConnectionLog.class);
        criteria.add(Restrictions.eq("id", userConnectionLogId));
        UserConnectionLog userConnectionLog = (UserConnectionLog) criteria.uniqueResult();
        return userConnectionLog;
	}

	public List<UserConnectionLog> findUserConnectionLogsByUserId(final Long userId, Object... params) {
        Criteria criteria = createDefaultCriteria(UserConnectionLog.class);
        criteria.add(Restrictions.eq("userId", userId));

        criteria.addOrder(Order.asc("loginDate"));

        @SuppressWarnings("unchecked")
        List<UserConnectionLog> userConnectionLogs = criteria.list();
		return userConnectionLogs;
	}
	
	public List<UserConnectionLog> findUserConnectionLogsByUserIdAndAppCode(final Long userId, final String appCode, Object... params) {
        Criteria criteria = createDefaultCriteria(UserConnectionLog.class);
        criteria.add(Restrictions.eq("userId", userId));
        criteria.add(Restrictions.eq("app", appCode));
        
        criteria.addOrder(Order.asc("loginDate"));

        @SuppressWarnings("unchecked")
        List<UserConnectionLog> userConnectionLogs = criteria.list();
		return userConnectionLogs;
	}

	public UserConnectionLog saveOrUpdateUserConnectionLog(final UserConnectionLog userConnectionLog) {
        if (userConnectionLog.getId() != null) {
            if(em.contains(userConnectionLog)){
                em.refresh(userConnectionLog);
            }
            UserConnectionLog mergedUserConnectionLog = em.merge(userConnectionLog);
            em.flush();
            return mergedUserConnectionLog;
        } else {
            em.persist(userConnectionLog);
            return userConnectionLog;
        }
	}

	public void deleteUserConnectionLog(final UserConnectionLog userConnectionLog) {
		em.remove(userConnectionLog);
	}

}