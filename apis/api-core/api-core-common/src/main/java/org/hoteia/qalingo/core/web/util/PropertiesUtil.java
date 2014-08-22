/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.util;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class PropertiesUtil extends org.springframework.beans.BeanUtils { 
    
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    
    public static String getWebappContextKey(final String contextName) {
        String webappContextKey = null;
        try {
            if(StringUtils.isNotEmpty(contextName)){
                String keySuffix = contextName.replace("_", ".").toLowerCase();
                PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                Resource resources[] = resolver.getResources("classpath*:engine-setting-webapp-context*");
                for (int i = 0; i < resources.length; i++) {
                    Resource resource = resources[i];
                    String key = "engine.setting.webapp.context." + keySuffix;
                    Properties prop = new Properties();
                    prop.load(resource.getInputStream());
                    webappContextKey = prop.getProperty(key);
                }
            }
            
        } catch (Exception e) {
            logger.error("This contextName key doesn't exist", e);
        }
        return webappContextKey;
    }
}