package org.hoteia.qalingo.core.cache;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class CachingSetupApplication  {

    @Autowired
    protected ApplicationContext applicationContext;
    
    @Value("${cache.configuration.location}")
    protected String ehcacheConfiguration;
    
    @Bean(name = "cacheManager")
    public CacheManager cacheManager() {
        CacheManager cacheManager = null;
        try {
            Resource resource = applicationContext.getResource(ehcacheConfiguration);
            XmlConfiguration xmlConfiguration = new XmlConfiguration(resource.getURL());
            cacheManager = CacheManagerBuilder.newCacheManager(xmlConfiguration);
            cacheManager.init();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheManager;
    }
}
