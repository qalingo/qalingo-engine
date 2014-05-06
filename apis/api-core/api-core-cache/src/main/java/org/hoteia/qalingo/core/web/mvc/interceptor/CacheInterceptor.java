/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.cache.CustomVelocityLayoutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 
 */
public class CacheInterceptor implements HandlerInterceptor {
    
    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    @Value("${cache.web.html.name}")
    private String cacheName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Cache cache = getCacheManager() != null && StringUtils.isNotEmpty(cacheName) ? getCacheManager().getCache(cacheName) : null;
        boolean isGet = "GET".equals(request.getMethod());
        boolean isHttp = "http".equals(request.getScheme().toLowerCase());
        if (cache != null && isHttp && isGet) {
            int key = CustomVelocityLayoutView.getRequestKey(request);
            if (cache.isKeyInCache(key)) {
                Element element = cache.get(key);
                if (element != null && !element.isExpired()) {
                    response.getWriter().write((String) element.getObjectValue());
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception exception) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
                           Object handler, ModelAndView modelAndView) throws Exception {
    }
    
    public CacheManager getCacheManager() {
        return ehCacheCacheManager.getCacheManager();
    }
    
}