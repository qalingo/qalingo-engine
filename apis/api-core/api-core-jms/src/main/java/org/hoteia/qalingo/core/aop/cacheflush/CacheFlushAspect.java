/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.aop.cacheflush;

import java.net.InetAddress;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.hoteia.qalingo.core.jms.cacheflush.producer.CacheFlushMessageJms;
import org.hoteia.qalingo.core.jms.cacheflush.producer.CacheFlushMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "cacheFlushAspect")
public class CacheFlushAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected CacheFlushMessageProducer cacheFlushMessageProducer;
    
    @Value("${env.name}")  
    protected String environmentName;
    
    @Value("${env.id}")  
    protected String environmentId;
    
    @Value("${app.name}")  
    protected String applicationName;
    
    public void before(final JoinPoint joinPoint) {
        if(logger.isDebugEnabled()){
            logger.debug("CacheFlushAspect, before");
        }
    }

    public void afterReturning(final StaticPart staticPart, final Object result) {
        if(logger.isDebugEnabled()){
            logger.debug("CacheFlushAspect, afterReturning");
        }
        try {
            final CacheFlushMessageJms cacheFlushMessageJms = new CacheFlushMessageJms();
            cacheFlushMessageJms.setEnvironmentName(environmentName);
            cacheFlushMessageJms.setEnvironmentId(environmentId);
            cacheFlushMessageJms.setApplicationName(applicationName);
            cacheFlushMessageJms.setServerName(InetAddress.getLocalHost().getHostName());
            cacheFlushMessageJms.setServerIp(InetAddress.getLocalHost().getHostAddress());
            cacheFlushMessageProducer.generateAndSendMessages(cacheFlushMessageJms);
            
        } catch (Exception e) {
            logger.error("CacheFlushAspect Target Object error: " + e);
        }
    }
    
}