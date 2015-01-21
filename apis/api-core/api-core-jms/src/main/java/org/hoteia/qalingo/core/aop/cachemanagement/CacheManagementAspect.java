/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.aop.cachemanagement;

import java.net.InetAddress;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.hoteia.qalingo.core.jms.cachemanagement.producer.CacheManagementMessageJms;
import org.hoteia.qalingo.core.jms.cachemanagement.producer.CacheManagementMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "cacheManagementAspect")
public class CacheManagementAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CacheManagementMessageProducer cacheManagementMessageProducer;
    
    @Value("${env.name}")  
    protected String environmentName;
    
    @Value("${env.id}")  
    protected String environmentId;
    
    @Value("${app.name}")  
    protected String applicationName;
    
    public void before(final JoinPoint joinPoint) {
        if(logger.isDebugEnabled()){
            logger.debug("CacheManagementAspect, before");
        }
    }

    public void afterReturning(final StaticPart staticPart, final Object result) {
        if(logger.isDebugEnabled()){
            logger.debug("CacheManagementAspect, afterReturning");
        }
        try {
            final CacheManagementMessageJms cacheManagementMessageJms = new CacheManagementMessageJms();
            cacheManagementMessageJms.setEnvironmentName(environmentName);
            cacheManagementMessageJms.setEnvironmentId(environmentId);
            cacheManagementMessageJms.setApplicationName(applicationName);
            cacheManagementMessageJms.setServerName(InetAddress.getLocalHost().getHostName());
            cacheManagementMessageJms.setServerIp(InetAddress.getLocalHost().getHostAddress());
            cacheManagementMessageProducer.generateAndSendMessages(cacheManagementMessageJms);
            
        } catch (Exception e) {
            logger.error("CacheManagementAspect Target Object error: " + e);
        }
    }
    
}