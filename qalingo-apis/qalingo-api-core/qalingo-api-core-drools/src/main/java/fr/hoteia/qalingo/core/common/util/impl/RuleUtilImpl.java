/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.util.impl;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.service.RuleRepositoryService;
import fr.hoteia.qalingo.core.common.util.RuleUtil;
import fr.hoteia.qalingo.core.domain.EngineEcoSession;
import fr.hoteia.qalingo.core.domain.RuleRepository;
import fr.hoteia.qalingo.core.web.util.RequestUtil;

/**
 * <p>
 * <a href="RuleUtilImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
@Service("ruleUtil")
@Transactional
public class RuleUtilImpl implements RuleUtil {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected RuleRepositoryService ruleRepositoryService;

	@Autowired
	protected RequestUtil requestUtil;
	
	@Autowired
	protected StatefulKnowledgeSession ksession;

	public void handleRuleSession(final HttpServletRequest request){
    	try {
//    		StatefulKnowledgeSession ksession = (StatefulKnowledgeSession) ctx.getBean("ksessionQalingo");

    		KnowledgeBase kbase = ksession.getKnowledgeBase();
    		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

    		List<RuleRepository> activeRules = ruleRepositoryService.findActiveRuleRepositories();

    		for (Iterator<RuleRepository> iterator = activeRules.iterator(); iterator.hasNext();) {
				RuleRepository ruleRepository = (RuleRepository) iterator.next();
				String ruleDRL = ruleRepository.getRuleString().toString();
				Resource ruleResource = ResourceFactory.newReaderResource((Reader) new StringReader(ruleDRL)); 
				kbuilder.add(ruleResource, ResourceType.DRL); 
			}

			if (kbuilder.hasErrors()) {
				KnowledgeBuilderErrors errors = kbuilder.getErrors();
				if(errors != null){
					for (Iterator<KnowledgeBuilderError> iterator = errors.iterator(); iterator.hasNext();) {
						KnowledgeBuilderError knowledgeBuilderError = (KnowledgeBuilderError) iterator.next();
						LOG.error(knowledgeBuilderError.getMessage());
					}
				}
	            throw new RuntimeException("Unable to compile drl"); 
	        } 
			
    		Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
    		kbase.addKnowledgePackages(pkgs);
    		EngineEcoSession engineEcoSession = requestUtil.getCurrentEcoSession(request);
    		ksession.insert(engineEcoSession);
    		ksession.fireAllRules();
        		
		} catch (Exception e) {
			LOG.error("UrlParams Filter & HandleUrlParameters failed, e");
		}
	}
}
