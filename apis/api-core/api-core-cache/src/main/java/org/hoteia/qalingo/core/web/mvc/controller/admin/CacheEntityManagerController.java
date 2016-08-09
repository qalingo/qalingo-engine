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
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.ehcache.xml.model.CacheType;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.enumtype.CommonUrls;
import org.hoteia.qalingo.core.web.cache.util.CacheService;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractQalingoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = CommonUrls.ENTITY_CACHE_URL)
public class CacheEntityManagerController extends AbstractQalingoController {

    @Autowired
    private CacheService cacheService;
    
    @PostConstruct
    public void init() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public String displayCacheEntityManager(final HttpServletRequest request, final Model model,
                @RequestParam(value = "flush", required = false) String flush) throws Exception {
        
        List<CacheType> caches = cacheService.getCaches();
        List<String> cacheNames = new ArrayList<String>();
        for (CacheType cacheType : caches) {
            cacheNames.add(cacheType.getAlias());
        }
        
        if("all".equals(flush)){
            cacheService.flushCaches(caches);
        }
        
        model.addAttribute("title", Constants.QALINGO + " Cache Entity Manager");
        model.addAttribute("flushName", flush);
        model.addAttribute("caches", caches);
        model.addAttribute("hostname", getHostname());

        return CommonUrls.ENTITY_CACHE.getVelocityPage();
    }

    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknowned";
        }
    }

}