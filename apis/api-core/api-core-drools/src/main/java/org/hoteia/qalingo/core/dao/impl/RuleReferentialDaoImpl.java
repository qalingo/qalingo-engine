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

import org.hoteia.qalingo.core.dao.RuleReferentialDao;
import org.hoteia.qalingo.core.domain.AbstractRuleReferential;

@Transactional
@Repository("ruleReferentialDao")
public class RuleReferentialDaoImpl extends AbstractGenericDaoImpl implements RuleReferentialDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public AbstractRuleReferential getRuleReferentialByRuleType(String ruleType) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM AbstractRuleReferential WHERE upper(ruleType) = upper(:ruleType)";
		Query query = session.createQuery(sql);
		query.setString("ruleType", ruleType);
		AbstractRuleReferential ruleReferential = (AbstractRuleReferential) query.uniqueResult();
		return ruleReferential;
	}
	
	public AbstractRuleReferential getRuleReferentialByCode(String code) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM AbstractRuleReferential WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", code);
		AbstractRuleReferential ruleReferential = (AbstractRuleReferential) query.uniqueResult();
		return ruleReferential;
	}
	
	public List<AbstractRuleReferential> findRuleReferentials() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM AbstractRuleReferential";
		Query query = session.createQuery(sql);
		List<AbstractRuleReferential> ruleReferentials = (List<AbstractRuleReferential>) query.list();
		return ruleReferentials;
	}
	
	public void saveRuleReferential(AbstractRuleReferential ruleReferential) {
		if(ruleReferential.getDateCreate() == null){
			ruleReferential.setDateCreate(new Date());
		}
		ruleReferential.setDateUpdate(new Date());
		em.persist(ruleReferential);
	}

	public void deleteRuleReferential(AbstractRuleReferential ruleReferential) {
		em.remove(ruleReferential);
	}
	
}
