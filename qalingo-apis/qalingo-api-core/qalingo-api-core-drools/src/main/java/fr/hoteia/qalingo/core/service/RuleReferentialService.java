/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service;

import java.util.List;

import fr.hoteia.qalingo.core.domain.AbstractRuleReferential;

public interface RuleReferentialService {

	AbstractRuleReferential getRuleReferentialByRuleType(String ruleType);

	AbstractRuleReferential getRuleReferentialByCode(String code);

	List<AbstractRuleReferential> findRuleReferentials();
	
	void saveRuleReferential(AbstractRuleReferential ruleReferential);
	
	void deleteRuleReferential(AbstractRuleReferential ruleReferential);

}
