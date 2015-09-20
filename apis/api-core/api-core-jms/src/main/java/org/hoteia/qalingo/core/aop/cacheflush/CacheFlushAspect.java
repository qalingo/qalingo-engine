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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.hoteia.qalingo.core.jms.cacheflush.producer.CacheFlushMessageJms;
import org.hoteia.qalingo.core.jms.cacheflush.producer.CacheFlushMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

@Component(value = "cacheFlushAspect")
public class CacheFlushAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected CacheFlushMessageProducer cacheFlushMessageProducer;
    
    @Autowired
    protected EhCacheCacheManager ehCacheCacheManager;
    
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
            List<Cache> caches = getCaches();
            for (Cache cache : caches) {
                flushCache(cache);
            } 
            
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
    
    private List<Cache> getCaches() {
        List<Cache> caches = new ArrayList<Cache>();
        for (String cacheName : getCacheManager().getCacheNames()) {
            caches.add(getCacheManager().getCache(cacheName));
        }
        Collections.sort(caches, new Comparator<Cache>() {
            @Override
            public int compare(Cache o1, Cache o2) {
                if (o1 != null && o2 != null) {
                    String order1 = o1.getName();
                    String order2 = o2.getName();
                    return order1.compareTo(order2);
                }
                return 0;
            }
        });
        return caches;
    }
    
    private void flushCache(Cache cache) {
        if(cache != null){
            cache.removeAll();
            cache.clearStatistics();
        }
    }
    
    public CacheManager getCacheManager() {
        return ehCacheCacheManager.getCacheManager();
    }
    
}