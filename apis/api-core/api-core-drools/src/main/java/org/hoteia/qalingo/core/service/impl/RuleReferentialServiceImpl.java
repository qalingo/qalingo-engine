/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.RuleReferentialDao;
import org.hoteia.qalingo.core.domain.AbstractRuleReferential;
import org.hoteia.qalingo.core.service.RuleReferentialService;

@Service("ruleReferentialService")
@Transactional
public class RuleReferentialServiceImpl implements RuleReferentialService {

	@Autowired
	private RuleReferentialDao ruleReferentialDao;

	public AbstractRuleReferential getRuleReferentialByRuleType(String ruleType) {
		return ruleReferentialDao.getRuleReferentialByRuleType(ruleType);
	}
	
	public AbstractRuleReferential getRuleReferentialByCode(String code) {
		return ruleReferentialDao.getRuleReferentialByCode(code);
	}

	public List<AbstractRuleReferential> findRuleReferentials() {
		return ruleReferentialDao.findRuleReferentials();
	}
	
	public void saveRuleReferential(AbstractRuleReferential ruleReferential) {
		ruleReferentialDao.saveRuleReferential(ruleReferential);
	}
	
	public void deleteRuleReferential(AbstractRuleReferential ruleReferential) {
		ruleReferentialDao.deleteRuleReferential(ruleReferential);
	}

}
