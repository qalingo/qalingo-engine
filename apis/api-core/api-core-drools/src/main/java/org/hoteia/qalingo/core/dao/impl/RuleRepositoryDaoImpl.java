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

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.RuleRepositoryDao;
import org.hoteia.qalingo.core.dao.impl.AbstractGenericDaoImpl;
import org.hoteia.qalingo.core.domain.RuleRepository;

@Transactional
@Repository("ruleRepositoryDao")
public class RuleRepositoryDaoImpl extends AbstractGenericDaoImpl implements RuleRepositoryDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public RuleRepository getRuleRepositoryByCode(String ruleConditionRepositoryCode) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM RuleRepository WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", ruleConditionRepositoryCode);
		RuleRepository ruleRepository = (RuleRepository) query.uniqueResult();
		return ruleRepository;
	}
	
	public List<RuleRepository> findRuleRepositories() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM RuleRepository";
		Query query = session.createQuery(sql);
		List<RuleRepository> ruleRepositories = (List<RuleRepository>) query.list();
		return ruleRepositories;
	}
	
	public List<RuleRepository> findActiveRuleRepositories() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM RuleRepository WHERE active = 1 AND startDate <= :currentDate AND endDate >= :currentDate";
		Query query = session.createQuery(sql);
		query.setDate("currentDate", new Date());
		List<RuleRepository> ruleRepositories = (List<RuleRepository>) query.list();
		return ruleRepositories;
	}
	
	public void saveRuleRepository(RuleRepository ruleConditionRepository) {
		if(ruleConditionRepository.getDateCreate() == null){
			ruleConditionRepository.setDateCreate(new Date());
		}
		ruleConditionRepository.setDateUpdate(new Date());
		em.persist(ruleConditionRepository);
	}

	public void deleteRuleRepository(RuleRepository ruleConditionRepository) {
		em.remove(ruleConditionRepository);
	}
	
}
