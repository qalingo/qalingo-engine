/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.aop.cache;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.hoteia.qalingo.core.domain.AbstractEntity;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

@Component(value = "cacheManagementAspect")
public class CacheManagementAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String DEFAULT_CACHE_NAME = "web_cache_common";

    public static final String CACHE_NAME = "CACHE_NAME";
    
    public static final String CACHE_TYPE_MISC = "CACHE_TYPE_MISC";
    public static final String CACHE_BY_ID = "CACHE_BY_ID";
    public static final String CACHE_BY_CODE = "CACHE_BY_CODE";

    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnObject = null;
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Class classTarget = signature.getReturnType();
            Object[] args = joinPoint.getArgs();
            String suffix = "";
            List<SpecificFetchMode> askedFetchModes = null;
            List<SpecificFetchMode> loadedFetchModes = null;
            String cacheType = CACHE_TYPE_MISC;
            
            // TOD : Denis : blindé le code pour tester les arg differement entre une method get* et find* et autre
            
            if(joinPoint.getSignature().toShortString().contains("ById")){
                // FIRST ARG IS A LONG FOR THE GET METHOD : SO THIS A GET BY ID
                cacheType = CACHE_BY_ID;
            } else if(joinPoint.getSignature().toShortString().contains("ByCode")){
                // FIRST ARG IS A STRING FOR THE GET METHOD : SO THIS A GET BY CODE
                cacheType = CACHE_BY_CODE;
            }
            
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if(arg instanceof Object[]){
                    Object[] objects = (Object[]) arg;
                    for (int j = 0; j < objects.length; j++) {
                        Object object = (Object) objects[j];
                        if(object instanceof FetchPlan){
                            FetchPlan fetchPlan = (FetchPlan) object;
                            if(fetchPlan != null && !fetchPlan.getFetchModes().isEmpty()){
                                askedFetchModes = fetchPlan.getFetchModes();
                            }
                        }
                    }
                } 
                if(arg instanceof RequestData){
                    RequestData requestData = (RequestData) arg;
                    if(!suffix.endsWith("_")){
                        suffix = suffix + "_";
                    }
                    suffix = suffix + requestData.getMarketPlace().getCode() 
                                    + "_" + requestData.getMarket().getCode()
                                    + "_" + requestData.getMarketArea().getCode()
                                    + "_" + requestData.getMarketAreaLocalization().getCode()
                                    + "_" + requestData.getMarketAreaRetailer().getCode()
                                    + "_" + requestData.getMarketAreaCurrency().getCode();

                } else if(arg instanceof AbstractEntity){
                    AbstractEntity argEntity = (AbstractEntity) arg;
                    if(!suffix.endsWith("_")){
                        suffix = suffix + "_";
                    }
                    Method[] methods = argEntity.getClass().getMethods();
                    for (int j = 0; j < methods.length; j++) {
                        Method methodIt = methods[j];
                        if(methodIt.getName().equals("getId")){
                            Long id = (Long) methodIt.invoke(argEntity);
                            suffix = suffix + id;
                        }
                    }

                } else {
                    if(!(arg instanceof java.lang.Object[])
                            && !(arg instanceof AbstractEntity)) {
                        if(!suffix.endsWith("_")){
                            suffix = suffix + "_";
                        }
                        suffix = suffix + arg.toString();
                    }
                }
            }
            String key = null;
            String cacheName = DEFAULT_CACHE_NAME;
            if(classTarget != null){
                try {
                    Field cacheField = null;
                    Field[] fields = classTarget.getFields();
                    for (int i = 0; i < fields.length; i++) {
                        Field fieldIt = fields[i];
                        if(fieldIt.getName().equals(CACHE_NAME)){
                            cacheField = fieldIt;
                        }
                    }
                    if(cacheField != null){
                        cacheName = (String) cacheField.get(CACHE_NAME);
                    }
                } catch (IllegalAccessException e) {
                    if(logger.isDebugEnabled()){
                        logger.debug("IllegalAccessException code.", e);
                    }
                }
            }
            
            // CACHE TYPE
            if(cacheType.equals(CACHE_TYPE_MISC)){
                key = joinPoint.getSignature().toShortString() + suffix;
                if(!cacheName.contains("_misc")){
                    cacheName = cacheName + "_misc";
                }
            } else if(cacheType.equals(CACHE_BY_CODE)){
                // TODO : Denis : utiliser un cache de type cacheName_link_code_id pour avoir l'id en fonction du code
                key = classTarget.getName() + suffix;
                cacheName = cacheName + "_link_code_id";
            } else {
                key = classTarget.getName() + suffix;
            }
            
            Cache cache = getCacheManager() != null && StringUtils.isNotEmpty(cacheName) ? getCacheManager().getCache(cacheName) : null;
            if (cache != null) {
                if (cache.isKeyInCache(key)) {
                    Element element = cache.get(key);
                    if (element != null && !element.isExpired()) {
                        // WE TEST IF THE FETCH PLAN ARE EQUALS
                        returnObject = element.getObjectValue();
                        if(returnObject instanceof AbstractEntity){
                            AbstractEntity entity = (AbstractEntity) returnObject;
                            if(entity.getFetchPlan() != null){
                                loadedFetchModes = entity.getFetchPlan().getFetchModes();
                            }
                            
                            if(cacheType.equals(CACHE_BY_ID)){
                                // ENTITY : UPDATE THE CACHE LINK ID CODE
                                String cacheNameIdCodeLink = cacheName + "_link_code_id";
                                Cache cacheLinkIdCode = getCacheManager() != null && StringUtils.isNotEmpty(cacheNameIdCodeLink) ? getCacheManager().getCache(cacheNameIdCodeLink) : null;
                                if(cacheLinkIdCode != null){
                                    String newKey = null;
                                    String codeValue = null;
                                    try {
                                        Method[] methods = classTarget.getMethods();
                                        for (int i = 0; i < methods.length; i++) {
                                            Method methodIt = methods[i];
                                            if(methodIt.getName().equals("getId")){
                                                Long id = (Long) methodIt.invoke(returnObject);
                                                newKey = classTarget.getName() + "_" + id;
                                            }
                                            if(methodIt.getName().equals("getCode")){
                                                codeValue = (String) methodIt.invoke(returnObject);
                                            }
                                            if(newKey != null && codeValue != null){
                                                break;
                                            }
                                        }
                                    } catch (Exception e) {
                                        if(logger.isDebugEnabled()){
                                            logger.debug("IllegalAccessException.", e);
                                        }
                                    }
                                    if(newKey != null){
                                        cacheLinkIdCode.put(new Element(newKey, codeValue));
                                    }
                                }
                            }
                            
                            if(cacheType.equals(CACHE_BY_CODE)){
                                String cacheNameEntityById = cacheName.replace("_link_code_id", "");
                                Cache cacheEntityById = getCacheManager() != null && StringUtils.isNotEmpty(cacheNameEntityById) ? getCacheManager().getCache(cacheNameEntityById) : null;
                                
                                String newKey = null;
                                Method[] methods = classTarget.getMethods();
                                for (int i = 0; i < methods.length; i++) {
                                    Method methodIt = methods[i];
                                    if(methodIt.getName().equals("getId")){
                                        Long id = (Long) methodIt.invoke(returnObject);
                                        newKey = classTarget.getName() + "_" + id;
                                        break;
                                    }
                                }
                                
                                if (cacheEntityById != null) {
                                    if (cacheEntityById.isKeyInCache(newKey)) {
                                        Element elementEntityById = cacheEntityById.get(newKey);
                                        if (elementEntityById != null && !elementEntityById.isExpired()) {
                                            returnObject = elementEntityById.getObjectValue();
                                        }
                                    }
                                }

                            }
                        } else if(returnObject instanceof Long){
                            if(cacheType.equals(CACHE_BY_CODE)){
                                String cacheNameEntityById = cacheName.replace("_link_code_id", "");
                                Cache cacheEntityById = getCacheManager() != null && StringUtils.isNotEmpty(cacheNameEntityById) ? getCacheManager().getCache(cacheNameEntityById) : null;
                                String newKey = classTarget.getName() + "_" + returnObject;
                                if (cacheEntityById.isKeyInCache(newKey)) {
                                    Element finalElement = cacheEntityById.get(newKey);
                                    if (finalElement != null && !finalElement.isExpired()) {
                                        // WE WILL TEST IF THE FETCH PLAN ARE EQUALS
                                        returnObject = finalElement.getObjectValue();
                                    }
                                } else {
                                    // WE RESET THE returnObject WHICH HAS THE LONG VALUE - THIS WILL TRIGGER THE LOAD BY DAO
                                    returnObject = null;
                                }
                            }
                        }
                    }
                }
                if(returnObject == null){
                    if(loadedFetchModes != null){
                        args = ArrayUtils.add(args, loadedFetchModes);
                        returnObject = joinPoint.proceed(args);
                    } else {
                        returnObject = joinPoint.proceed();
                    }
                    if(cacheType.equals(CACHE_BY_CODE)){
                        // PUT IN THE RIGHT ENTITY CACHE
                        String cacheNameEntityById = cacheName.replace("_link_code_id", "");
                        Cache cacheEntityById = getCacheManager() != null && StringUtils.isNotEmpty(cacheNameEntityById) ? getCacheManager().getCache(cacheNameEntityById) : null;
                        String newKey = null;
                        Method[] methods = classTarget.getMethods();
                        Long value = null;
                        for (int i = 0; i < methods.length; i++) {
                            Method methodIt = methods[i];
                            if(methodIt.getName().equals("getId")){
                                Long id = (Long) methodIt.invoke(returnObject);
                                newKey = classTarget.getName() + "_" + id;
                                value = id;
                                break;
                            }
                        }
                        if (cacheEntityById != null) {
                            cacheEntityById.put(new Element(newKey, returnObject));
                        }
                        
                        cache.put(new Element(key, value));

                    } else {
                        cache.put(new Element(key, returnObject));
                    }
                } else {
                    if(returnObject instanceof AbstractEntity){
                        AbstractEntity entity = (AbstractEntity) returnObject;
                        if(entity.getFetchPlan() != null){
                            loadedFetchModes = entity.getFetchPlan().getFetchModes();
                        }
                        if(askedFetchModes != null){
                            for (Iterator<SpecificFetchMode> iterator = askedFetchModes.iterator(); iterator.hasNext();) {
                                SpecificFetchMode specificFetchMode = (SpecificFetchMode) iterator.next();
                                if(loadedFetchModes == null){
                                    // ENTITY IS LOAD WITHOUT FETCHPLAN - WE RESET THE returnObject TO TRIGGER THE RELOAD WITH THE FETCHPLAN
                                    returnObject = null;
                                    break;
                                } else if (!loadedFetchModes.contains(specificFetchMode)){
                                    // ENTITY IS LOAD WITH A DIFF FETCHPLAN - WE RESET THE returnObject TO TRIGGER THE RELOAD
                                    returnObject = null;
                                    break;
                                }
                            }
                            
                            if(returnObject == null){
                                if(loadedFetchModes != null){
                                    for (int i = 0; i < args.length; i++) {
                                        Object arg = args[i];
                                        if(arg instanceof Object[]){
                                            Object[] objects = (Object[]) arg;
                                            for (int j = 0; j < objects.length; j++) {
                                                Object object = (Object) objects[j];
                                                if(object instanceof FetchPlan){
                                                    // WE ARE IN THE FETCHPLAN OBJECT ARRAY
                                                    objects = ArrayUtils.add(objects, entity.getFetchPlan());
                                                    args = ArrayUtils.remove(args, i);
                                                    args = ArrayUtils.add(args, objects);
                                                    break;
                                                }
                                            }
                                        } 
                                    }
                                    
                                    returnObject = joinPoint.proceed(args);
                                } else {
                                    returnObject = joinPoint.proceed();
                                }
                                
                                if(returnObject != null){
                                    if(cacheType.equals(CACHE_BY_CODE)){
                                        // PUT IN THE RIGHT ENTITY CACHE
                                        String cacheNameEntityById = cacheName.replace("_link_code_id", "");
                                        Cache cacheEntityById = getCacheManager() != null && StringUtils.isNotEmpty(cacheNameEntityById) ? getCacheManager().getCache(cacheNameEntityById) : null;
                                        String newKey = null;
                                        Method[] methods = classTarget.getMethods();
                                        Long value = null;
                                        for (int i = 0; i < methods.length; i++) {
                                            Method methodIt = methods[i];
                                            if(methodIt.getName().equals("getId")){
                                                Long id = (Long) methodIt.invoke(returnObject);
                                                newKey = classTarget.getName() + "_" + id;
                                                value = id;
                                                break;
                                            }
                                        }
                                        if (cacheEntityById != null) {
                                            cacheEntityById.put(new Element(newKey, returnObject));
                                        }
                                        
                                        cache.put(new Element(key, value));

                                    } else {
                                        cache.put(new Element(key, returnObject));
                                    }
                                }
                            }
                        }
                    }
                }
                
            }

        } catch (Exception e) {
            logger.error("Failed to load datas with Cache AOP!", e);
        }
        return returnObject;
    }

    public CacheManager getCacheManager() {
        return ehCacheCacheManager.getCacheManager();
    }
    
}