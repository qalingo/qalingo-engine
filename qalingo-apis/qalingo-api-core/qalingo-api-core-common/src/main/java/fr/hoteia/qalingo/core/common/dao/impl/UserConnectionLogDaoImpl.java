/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.UserConnectionLogDao;
import fr.hoteia.qalingo.core.common.domain.UserConnectionLog;

@Transactional
@Repository("userConnectionLogDao")
public class UserConnectionLogDaoImpl extends AbstractGenericDaoImpl implements UserConnectionLogDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public UserConnectionLog getUserConnectionLogById(Long userConnectionLogId) {
		return em.find(UserConnectionLog.class, userConnectionLogId);
	}

	public List<UserConnectionLog> findByExample(UserConnectionLog userConnectionLogExample) {
		return super.findByExample(userConnectionLogExample);
	}

	public void saveOrUpdateUserConnectionLog(UserConnectionLog userConnectionLog) {
		if(userConnectionLog.getId() == null){
			em.persist(userConnectionLog);
		} else {
			em.merge(userConnectionLog);
		}
	}

	public void deleteUserConnectionLog(UserConnectionLog userConnectionLog) {
		em.remove(userConnectionLog);
	}

}
