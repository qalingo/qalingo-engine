/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.controller.admin;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.runtime.resource.ResourceCache;
import org.apache.velocity.runtime.resource.ResourceManager;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.enumtype.CommonUrls;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractQalingoController;
import org.hoteia.qalingo.core.web.servlet.VelocityLayoutViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 */
@Controller("cacheIhmManagerController")
@RequestMapping(value = CommonUrls.VELOCITY_CACHE_URL)
public class CacheIhmManagerController extends AbstractQalingoController {

	@Resource(name="viewResolver")
	protected VelocityLayoutViewResolver viewResolver;
	
    @Resource(name = "resourceCache")
    protected ResourceCache resourceCache;
	   
    @Resource(name = "resourceManager")
    protected ResourceManager resourceManager;
    
	@Autowired
	protected CoreMessageSource coreMessageSource;
	
    @RequestMapping(method = RequestMethod.GET)
    public String displayCacheIhmManager(final HttpServletRequest request, final Model model,
                @RequestParam(value = "flush", required = false) String flush) throws Exception {

		processFlush(flush);

        model.addAttribute("title", Constants.QALINGO + " IHM Cache Manager");
        model.addAttribute("flushName", flush);
        model.addAttribute("hostname", getHostname());
        
        for (Iterator<Object> iterator = resourceCache.enumerateKeys(); iterator.hasNext();) {
            Object key = (Object) iterator.next();
            resourceCache.remove(key);
        };
        
		return CommonUrls.VELOCITY_CACHE.getVelocityPage();
	}
    
    private void processFlush(String flush) {
        if ("all".equals(flush)) {
            viewResolver.clearCache();
            coreMessageSource.clearCache();
            
        } else if ("view".equals(flush)) {
            viewResolver.clearCache();
            
        } else if ("message".equals(flush)) {
            coreMessageSource.clearCache();
            
        }
    }
	
    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknowned";
        }
    }
    
}