/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("productSkuPromotion")
public class RuleReferentialProductSkuPromotion extends AbstractRuleReferential {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -7478576526933473910L;

	public RuleReferentialProductSkuPromotion() {
	}
	
	// collection attributs

	
	@Override
	public String getCondition() {
		StringBuffer condition = new StringBuffer();
		condition.append("eval (1==1)");
		return condition.toString();
	}
	
	@Override
	public String getConsequence() {
		StringBuffer consequence = new StringBuffer();
		consequence.append("System.out.println(\"Rule productSkuPromotion Works\");");
		return consequence.toString();
	}
	
}
