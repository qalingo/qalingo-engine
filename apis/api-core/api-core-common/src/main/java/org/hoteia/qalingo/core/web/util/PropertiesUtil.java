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

import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil extends org.springframework.beans.BeanUtils { 
    
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    
    public static String getWebappContextKey(final String contextName) {
        String webappContextKey = null;
        try {
            if(StringUtils.isNotEmpty(contextName)){
                String keySuffix = contextName.replace("_", ".").toLowerCase();
                webappContextKey = ResourceBundle.getBundle("engine-setting-webapp-context-qalingo").getString("engine.setting.webapp.context." + keySuffix);
            }
            
        } catch (Exception e) {
            logger.error("This contextName key doesn't exist", e);
        }
        return webappContextKey;
    }
}