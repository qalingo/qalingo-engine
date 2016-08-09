/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.dao.RuleRepositoryDao;
import org.hoteia.qalingo.core.domain.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("ruleRepositoryService")
public class RuleRepositoryService {

	@Autowired
	private RuleRepositoryDao ruleRepositoryDao;

	// RULE CONDITION
	
	public RuleRepository getRuleRepositoryByCode(String ruleRepositoryCode) {
		return ruleRepositoryDao.getRuleRepositoryByCode(ruleRepositoryCode);
	}

	public List<RuleRepository> findRuleRepositories() {
		return ruleRepositoryDao.findRuleRepositories();
	}

	public List<RuleRepository> findActiveRuleRepositories() {
		return ruleRepositoryDao.findActiveRuleRepositories();
	}
	
	public void saveRuleRepository(RuleRepository ruleRepository) {
		ruleRepositoryDao.saveRuleRepository(ruleRepository);
	}
	
	public void deleteRuleRepository(RuleRepository ruleRepository) {
		ruleRepositoryDao.deleteRuleRepository(ruleRepository);
	}

}