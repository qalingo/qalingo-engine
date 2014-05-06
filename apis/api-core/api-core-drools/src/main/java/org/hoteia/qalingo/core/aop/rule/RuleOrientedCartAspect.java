/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.aop.rule;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.hoteia.qalingo.core.rule.util.RuleUtil;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "ruleOrientedCartAspect")
public class RuleOrientedCartAspect {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected RequestUtil requestUtil;

    @Autowired
    protected RuleUtil ruleUtil;

    public void before(final JoinPoint joinPoint) {
        if (logger.isDebugEnabled()) {
            logger.debug("RuleEcoAspect, before");
        }
    }

    public void afterReturning(final StaticPart staticPart, final Object result) {
        if (logger.isDebugEnabled()) {
            logger.debug("RuleEcoAspect, afterReturning");
        }
        try {
            List<Object> objects = new ArrayList<Object>();
            try {
                EngineEcoSession engineEcoSession = (EngineEcoSession) result;
                objects.add(engineEcoSession);
            } catch (Exception e) {
                logger.error("Failed to load EngineEcoSession from Request", e);
            }
            ruleUtil.handleRuleSession(objects);

        } catch (Exception e) {
            logger.error("RuleEcoAspect Target Object error: " + e);
        }
    }

}