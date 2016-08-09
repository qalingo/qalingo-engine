/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.rule.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hoteia.qalingo.core.domain.RuleRepository;
import org.hoteia.qalingo.core.service.RuleRepositoryService;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * <a href="RuleUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
@Service("ruleUtil")
@Transactional
public class RuleUtil {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected RuleRepositoryService ruleRepositoryService;

	protected KieContainer container = null;

	public KieContainer getKieContainer() throws IOException {
	    if(container == null){
	        KieServices ks = KieServices.Factory.get();
	        container =  ks.getKieClasspathContainer();
	    }
        return container;
	}
	
	public void handleRuleSession(List<Object> objects){
    	try {
    	    KieBase kbase = getKieContainer().getKieBase("kbaseQalingo");
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
						logger.error(knowledgeBuilderError.getMessage());
					}
				}
	            throw new RuntimeException("Unable to compile drl"); 
	        } 
			
    		Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
//    		kbase.addKnowledgePackages(pkgs);
    		kbase.getKiePackages().addAll(pkgs);
    		
    		KieSession kSession = container.newKieSession();
    		for (Iterator<Object> iterator = objects.iterator(); iterator.hasNext();) {
    			Object object = (Object) iterator.next();
    			kSession.insert(object);
			}
    		kSession.fireAllRules();
        		
		} catch (Exception e) {
			logger.error("UrlParams Filter & HandleUrlParameters failed, e");
		}
	}
}
