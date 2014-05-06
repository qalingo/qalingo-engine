/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Statistics;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin/cachemanager.html")
public class CacheManagerController extends AbstractQalingoController {

    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private enum CacheAction {
        STATISTICS {
            public void doAction(Cache cache, String value) {
                cache.setStatisticsAccuracy(Statistics.STATISTICS_ACCURACY_GUARANTEED);
                cache.setStatisticsEnabled(Boolean.valueOf(value));
                cache.getCacheConfiguration().statistics(Boolean.valueOf(value));
            }
        },
        MAXELEMENTSINMEMORY {
            public void doAction(Cache cache, String value) {
                cache.getCacheConfiguration().setMaxElementsInMemory(Integer.valueOf(value));
            }
        },
        MAXELEMENTSONDISK {
            public void doAction(Cache cache, String value) {
                cache.getCacheConfiguration().setMaxElementsOnDisk(Integer.valueOf(value));
            }
        },
        TIMETOIDLESECONDS {
            public void doAction(Cache cache, String value) {
                cache.getCacheConfiguration().setTimeToIdleSeconds(Integer.valueOf(value));
            }
        },
        TIMETOLIVESECONDS {
            public void doAction(Cache cache, String value) {
                cache.getCacheConfiguration().setTimeToLiveSeconds(Integer.valueOf(value));
            }
        },
        ACTIVE {
            public void doAction(Cache cache, String value) {
                cache.setDisabled(!Boolean.valueOf(value));
            }
        };

        public abstract void doAction(Cache cache, String value);
    }

//    private StoreCache storeCache;

    @PostConstruct
    public void init() {
//        storeCache = OpenJPAPersistence.cast(entityManagerFactory).getStoreCache();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String display(final HttpServletRequest request, final Model model,
                @RequestParam(value = "flush", required = false) String flush,
                @RequestParam(value = "resetStats", required = false) Boolean resetStats,
                @RequestParam(value = "calculateMemorySize", required = false) Boolean calculateMemorySize,
                @RequestParam(value = "evictExpiredElements", required = false) Boolean evictExpiredElements,
                @RequestParam(value = "disable", required = false) String disable,
                @RequestParam(value = "enable", required = false) String enable,
                @RequestParam(value = "actions", required = false) String[] actions) throws Exception {
        List<Cache> caches = getCaches();

        if (actions != null)
            processActions(actions, caches);
        if (flush != null)
            processFlush(flush, caches);
        if (resetStats == Boolean.TRUE)
            processResetStats(caches);
        if (evictExpiredElements == Boolean.TRUE)
            processEvictElements(caches);
        if (disable != null)
            processCachesStatus(disable, caches, true);
        if (enable != null)
            processCachesStatus(enable, caches, false);

        model.addAttribute("title", Constants.QALINGO + " Cache Manager");
        model.addAttribute("flushName", flush);
        model.addAttribute("caches", caches);
        model.addAttribute("hostname", getHostname());
        model.addAttribute("hasMemorySize", calculateMemorySize);

        return "/velocity/admin/cache-manager";
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

    private void processActions(String[] actions, List<Cache> caches) {
        for (String actionStr : actions) {
            String[] action = actionStr.split("_");
            String actionName = action[0].toUpperCase();
            String cacheName = action[1];
            String value = action[2];
            if (StringUtils.equals(cacheName, "all")) {
                for (Cache cache : caches) {
                    CacheAction.valueOf(actionName).doAction(cache, value);
                }
            } else {
                Cache cache = getCacheManager().getCache(cacheName);
                if (cache == null)
                    cache = CacheManager.getInstance().getCache(cacheName);
                if (cache != null)
                    CacheAction.valueOf(actionName).doAction(cache, value);
            }
        }

    }

    private void processFlush(String flush, List<Cache> caches) {
        // if ("openjpa".equals(flush) || "awoj".equals(flush) ||
        // "allwithopenjpa".equals(flush)) storeCache.evictAll();
        if ("all".equals(flush) || "awoj".equals(flush) || "allwithopenjpa".equals(flush)) {
            flushCaches(caches);
        }
        if (!("all".equals(flush) || "openjpa".equals(flush) || "awoj".equals(flush) || "allwithopenjpa".equals(flush))) {
            flushCache(getCacheManager().getCache(flush));
        }
    }

    public CacheManager getCacheManager() {
        return ehCacheCacheManager.getCacheManager();
    }

    private void flushCaches(List<Cache> caches) {
        for (Cache cache : caches) {
            flushCache(cache);
        }
    }

    private void flushCache(Cache cache) {
        cache.removeAll();
        cache.clearStatistics();
    }

    private void processResetStats(List<Cache> caches) {
        for (Cache cache : caches) {
            cache.clearStatistics();
        }
    }

    private void processEvictElements(List<Cache> caches) {
        for (Cache cache : caches) {
            cache.evictExpiredElements();
        }
    }

    private void updateCachesStatus(List<Cache> caches, boolean status) {
        for (Cache cache : caches) {
            cache.setDisabled(status);
        }
    }

    private void processCachesStatus(String flushScope, List<Cache> caches, boolean status) {
        if ("all".equals(flushScope)) {
            updateCachesStatus(caches, status);
        } else {
            getCacheManager().getCache(flushScope).setDisabled(status);
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