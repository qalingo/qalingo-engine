/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.jms.cachemanagement.listener;

import java.beans.ExceptionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hoteia.qalingo.core.jms.cachemanagement.producer.CacheManagementMessageJms;
import org.hoteia.qalingo.core.mapper.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

@Component(value = "cacheManagementQueueListener")
public class CacheManagementQueueListener implements MessageListener, ExceptionListener {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;
    
    @Autowired
    protected XmlMapper xmlMapper;
    
    /**
     * Implementation of <code>MessageListener</code>.
     */
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                String valueJMSMessage = tm.getText();
                
                if(StringUtils.isNotEmpty(valueJMSMessage)){
                    final CacheManagementMessageJms documentMessageJms = xmlMapper.getXmlMapper().readValue(valueJMSMessage, CacheManagementMessageJms.class);

                    List<Cache> caches = getCaches();
                    for (Cache cache : caches) {
                        flushCache(cache);
                    }    
                    
                    if (logger.isDebugEnabled()) {
                        logger.debug("Processed message, value: " + valueJMSMessage);
                    }
                } else {
                    logger.warn("Cache management: Jms Message is empty");
                }
            }
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
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

    @Override
    public void exceptionThrown(Exception e) {
        logger.debug("Exception on queue listener: " + e.getCause() + ":" + e.getLocalizedMessage());
    }

}