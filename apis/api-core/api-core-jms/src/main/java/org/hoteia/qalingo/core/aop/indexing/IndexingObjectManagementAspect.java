/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.aop.indexing;

import java.net.InetAddress;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.jms.indexing.producer.IndexingObjectMessageJms;
import org.hoteia.qalingo.core.jms.indexing.producer.IndexingObjectMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "indexingObjectManagementAspect")
public class IndexingObjectManagementAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IndexingObjectMessageProducer indexingObjectMessageProducer;
    
    @Value("${env.name}")  
    protected String environmentName;
    
    @Value("${env.id}")  
    protected String environmentId;
    
    @Value("${app.name}")  
    protected String applicationName;
    
    public void before(final JoinPoint joinPoint) {
        if(logger.isDebugEnabled()){
            logger.debug("IndexingObjectManagementAspect, before");
        }
    }

    public void afterReturning(final StaticPart staticPart, final Object result) {
        if(logger.isDebugEnabled()){
            logger.debug("IndexingObjectManagementAspect, afterReturning");
        }
        try {
            final IndexingObjectMessageJms indexingObjectMessageJms = new IndexingObjectMessageJms();
            indexingObjectMessageJms.setEnvironmentName(environmentName);
            indexingObjectMessageJms.setEnvironmentId(environmentId);
            indexingObjectMessageJms.setApplicationName(applicationName);
            indexingObjectMessageJms.setServerName(InetAddress.getLocalHost().getHostName());
            indexingObjectMessageJms.setServerIp(InetAddress.getLocalHost().getHostAddress());
            if(result instanceof ProductMarketing){
                final ProductMarketing productMarketing = (ProductMarketing) result;
                if(productMarketing != null
                        && productMarketing.getId() != null){
                    indexingObjectMessageJms.setObjectId(productMarketing.getId());
                    indexingObjectMessageJms.setObjectType("ProductMarketing");
                }
            } else if(result instanceof ProductSku){
                final ProductSku productSku = (ProductSku) result;
                if(productSku != null
                        && productSku.getId() != null){
                    indexingObjectMessageJms.setObjectId(productSku.getId());
                    indexingObjectMessageJms.setObjectType("ProductSku");
                }
            } else if(result instanceof Store){
                final Store store = (Store) result;
                if(store != null
                        && store.getId() != null){
                    indexingObjectMessageJms.setObjectId(store.getId());
                    indexingObjectMessageJms.setObjectType("Store");
                }
            }
            if(indexingObjectMessageJms.getObjectId() != null){
                // Generate and send the JMS message
                indexingObjectMessageProducer.generateMessages(indexingObjectMessageJms);
            }
            
        } catch (Exception e) {
            logger.error("IndexingObjectManagementAspect Target Object error: " + e);
        }
    }
    
}