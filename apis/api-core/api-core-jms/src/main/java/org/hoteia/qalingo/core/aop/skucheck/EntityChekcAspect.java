/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.aop.skucheck;

import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.jms.skucheck.producer.EntityCheckMessageJms;
import org.hoteia.qalingo.core.jms.skucheck.producer.EntityCheckMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "entityCheckAspect")
public class EntityChekcAspect {

    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    protected EntityCheckMessageProducer messageProducer;
    
    @Value("${env.name}")
    protected String environmentName;
    
    @Value("${env.id}")
    protected String environmentId;
    
    @Value("${app.name}")
    protected String applicationName;
    
    public void before(final JoinPoint joinPoint) {
        if(logger.isDebugEnabled()){
            logger.debug("EntityChekcAspect, before");
        }
    }

    public void afterReturning(final StaticPart staticPart, final Object result) {
        if(logger.isDebugEnabled()){
            logger.debug("EntityChekcAspect, afterReturning");
        }
        try {
            final EntityCheckMessageJms messageJms = new EntityCheckMessageJms();
            messageJms.setEnvironmentName(environmentName);
            messageJms.setEnvironmentId(environmentId);
            messageJms.setApplicationName(applicationName);
            messageJms.setServerName(InetAddress.getLocalHost().getHostName());
            messageJms.setServerIp(InetAddress.getLocalHost().getHostAddress());
            
            if(result instanceof ProductSku){
                final ProductSku productSku = (ProductSku) result;
                if(productSku != null
                        && productSku.getId() != null){
                    messageJms.setObjectId(productSku.getId());
                    messageJms.setObjectType("ProductSku");
                }
            } 
            
            // Generate and send the JMS message
            messageProducer.generateAndSendMessages(messageJms);
            
        } catch (Exception e) {
            logger.error("EntityChekcAspect Target Object error: " + e);
        }
    }

}