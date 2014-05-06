/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.cache;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.tools.view.context.ChainedContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;
import org.springframework.web.util.NestedServletException;

/**
 * 
 * 
 */
public class CustomVelocityLayoutView extends VelocityLayoutView {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void mergeTemplate(Template template, Context context, HttpServletResponse response) throws Exception {
        try {
            EhCacheCacheManager ehCacheCacheManager = (EhCacheCacheManager) getAttributesMap().get("ehCacheCacheManager");
            CacheManager cacheManager = ehCacheCacheManager.getCacheManager();
            String cacheName = (String) getAttributesMap().get("cacheName");
            Cache cache = cacheManager != null && StringUtils.isNotEmpty(cacheName) ? cacheManager.getCache(cacheName) : null;

            HttpServletRequest request = ((ChainedContext) context).getRequest();
            boolean isGet = StringUtils.equals("GET", request.getMethod());

            Object contextUseCache = context.get("useCache");
            Boolean useCache = Boolean.TRUE;
            if(contextUseCache instanceof Boolean){
                useCache = (Boolean) contextUseCache;
            } else if(contextUseCache instanceof String){
                useCache = BooleanUtils.toBoolean((String)contextUseCache);
            }
            boolean cacheable = useCache != null ? useCache : !isAjax(request) && isGet;

            if (!cacheable || cache == null) {
                template.merge(context, response.getWriter());
            } else {
                StringWriter outputWriter = new StringWriter();
                template.merge(context, outputWriter);
                outputWriter.close();
                String output = outputWriter.toString();

                cache.put(new Element(getRequestKey(request), output));

                response.getWriter().write(output);
            }

        } catch (MethodInvocationException ex) {
            logger.error("Fail to merge template.", ex);
            Throwable cause = ex.getWrappedThrowable();
            throw new NestedServletException( "Method invocation failed during rendering of Velocity view with name '" + getBeanName() + "': " + ex.getMessage() 
                                               + "; reference [" + ex.getReferenceName() + "], method '" + ex.getMethodName() + "'", cause == null ? ex : cause);
        }
    }
    
    public static final int getRequestKey(HttpServletRequest request) {
        return (request.getRequestURL().toString() + request.getQueryString()).hashCode();
    }

    private static boolean isAjax(HttpServletRequest request) {
        return StringUtils.equals("XMLHttpRequest", request.getHeader("X-Requested-With"));
    }
    
}