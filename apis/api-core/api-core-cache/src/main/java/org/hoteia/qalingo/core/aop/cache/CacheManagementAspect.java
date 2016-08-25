/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 */
package org.hoteia.qalingo.core.aop.cache;

import java.lang.reflect.Method;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.ehcache.Cache;
import org.hoteia.qalingo.core.annotation.CacheEntityInformation;
import org.hoteia.qalingo.core.annotation.CacheMethodInformation;
import org.hoteia.qalingo.core.annotation.CacheType;
import org.hoteia.qalingo.core.domain.AbstractEntity;
import org.hoteia.qalingo.core.domain.impl.DomainEntity;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.pojo.AbstractPojo;
import org.hoteia.qalingo.core.web.cache.util.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "cacheManagementAspect")
public class CacheManagementAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CacheService cacheService;

    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnObject = null;
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Class classReturnType = signature.getReturnType();
            Object[] args = joinPoint.getArgs();
            String cacheType = null;
            String cacheName = null;
            Cache cache = null;
            String key = null;
            
            logger.debug("Start Cache AOP. Call from : '" + joinPoint.getSignature().toShortString() + "'");

            // DEFINE THE CACHE TYPE
            try {
                CacheMethodInformation cacheMethodInformation = signature.getMethod().getAnnotation(CacheMethodInformation.class);
                cacheType = cacheMethodInformation.cacheType();
                logger.debug("CacheMethodInformation from annotation : cacheType= '" + cacheType + "'");
                cacheName = cacheMethodInformation.cacheName();
                logger.debug("CacheEntityInformation from annotation : cacheName= '" + cacheName + "'");
                
            } catch (Exception e) {
                // TODO: handle exception
            }
            if(DomainEntity.class.isAssignableFrom(classReturnType)){
                cacheType = CacheType.CACHE_ENTITY;
                if (joinPoint.getSignature().toShortString().contains("ByCode")) {
                    // FIRST ARG IS A STRING FOR THE GET METHOD : SO THIS A GET BY CODE
                    cacheType = CacheType.CACHE_LINK_CODE_ID;
                }
            } else if(AbstractPojo.class.isAssignableFrom(classReturnType)){
                cacheType = CacheType.CACHE_POJO;
                
            }
            logger.debug("Cache Type : '" + cacheType + "'");

            // TARGETED FETCH PLAN
            FetchPlan askedFetchPlan = null;
            FetchPlan loadedFetchPlan = null;
            for (Object arg : args) {
                if (arg instanceof Object[]) {
                    Object[] objects = (Object[]) arg;
                    for (Object object : objects) {
                        if (object instanceof FetchPlan) {
                            FetchPlan fetchPlan = (FetchPlan) object;
                            if (fetchPlan != null && !fetchPlan.getFetchModes().isEmpty()) {
                                askedFetchPlan = fetchPlan;
                                break;
                            }
                        }
                    }
                }
            }
            
            if(StringUtils.isNotEmpty(cacheType)){
                try {
                    CacheEntityInformation cacheEntityInformation = (CacheEntityInformation) classReturnType.getAnnotation(CacheEntityInformation.class);
                    cacheName = cacheEntityInformation.cacheName();
                    // CHECK THE NAME OF THE CACHE
                    if (cacheType.equals(CacheType.CACHE_LINK_CODE_ID)) {
                        cacheName += "_link_code_id";
                    }
                    logger.debug("CacheEntityInformation from annotation : cacheName= '" + cacheName + "'");

                } catch (Exception e) {
                    // TODO: handle exception
                }
                
                if(StringUtils.isNotEmpty(cacheName)){
                    // LOAD THE CACHE
                    if (cacheType.equals(CacheType.CACHE_ENTITY)) {
                        String id = null;
                        if(args != null && args.length > 0){
                            if(args[0] instanceof Long){
                                id = ((Long) args[0]).toString();
                            }
                        }
                        key = cacheService.buildEntityKey(signature.getReturnType(), id);
                        if(id == null){
                            // OVERRIDE THE KEY BY THE METHOD
                            key = signature.toShortString();
                        }
                        cache = cacheService.getCache(cacheName, String.class, AbstractEntity.class);

                    } else if (cacheType.equals(CacheType.CACHE_LINK_CODE_ID)) {
                        String code = null;
                        if(args != null && args.length > 0){
                            if(args[0] instanceof String){
                                code = ((String) args[0]).toString();
                            }
                        }
                        key = cacheService.buildCodeIdKey(signature, code);
                        if(code == null){
                            // OVERRIDE THE KEY BY THE METHOD
                            key = signature.toShortString();
                        }
                        cache = cacheService.getCache(cacheName, String.class, Long.class);
                        
                    } else if (cacheType.equals(CacheType.CACHE_POJO)) {
                        key = cacheService.buildCommonKey(signature, args);
                        cache = cacheService.getCache(cacheName, String.class, AbstractPojo.class);
                        
                    } else if (cacheType.equals(CacheType.CACHE_STRING)) {
                        key = cacheService.buildCommonKey(signature, args);
                        cache = cacheService.getCache(cacheName, String.class, String.class);
                    }
                    
                    // TEST IF THE VALUE IS IN CACHE
                    if (cache != null) {
                        logger.debug("Searching object in cache with : key= '" + key + "'");
                        if (cache.containsKey(key)) {
                            logger.debug("Object exist in cache with : key= '" + key + "'");
                            Object element = cache.get(key);
                            if (element != null) {
                                // WE TEST IF THE FETCH PLAN ARE EQUALS
                                returnObject = element;
                                
                                if (cacheType.equals(CacheType.CACHE_ENTITY)) {
                                    loadedFetchPlan = checkFetchPlan(returnObject, askedFetchPlan, loadedFetchPlan);
                                    
                                } else if (cacheType.equals(CacheType.CACHE_LINK_CODE_ID)) {
                                    String cacheNameEntity = cacheName.replace("_link_code_id", "");
                                    Long id = (Long) returnObject;
                                    returnObject = null;
                                    Cache cacheEntity = cacheService.getCache(cacheNameEntity, String.class, AbstractEntity.class);
                                    String keyEntity = cacheService.buildEntityKey(signature.getReturnType(), id.toString());
                                    if (cacheEntity.containsKey(keyEntity)) {
                                        Object entity = cacheEntity.get(keyEntity);
                                        returnObject = entity;
                                        loadedFetchPlan = checkFetchPlan(returnObject, askedFetchPlan, loadedFetchPlan);
                                    }
                                }
                            }
                        } else {
                            logger.debug("Object doesn't exist in cache with : key= '" + key + "'");
                        }
                    }
                }
            }
            
            // NOTHING IN CACHE - CALL THE TARGET METHOD
            if (returnObject == null) {
                if (loadedFetchPlan != null) {
                    args = ArrayUtils.add(args, loadedFetchPlan);
                    returnObject = joinPoint.proceed(args);
                } else {
                    returnObject = joinPoint.proceed();
                }
                
                if (returnObject != null && cache != null) {
                    // PUT IN CACHE
                    if (cacheType.equals(CacheType.CACHE_ENTITY)) {
                        logger.debug("Put in cache '" + cacheName + "'. key : '" + key + "'. value: '" + returnObject + "'");
                        cache.put(key, returnObject);

                        // PUT THE CODE/ID
                        String cacheNameCodeId = cacheName + "_link_code_id";
                        Cache cacheCodeId = cacheService.getCache(cacheNameCodeId, String.class, Long.class);
                        Long id = (Long) handleClassMethodGetValue(joinPoint, returnObject, classReturnType, "getId");
                        String code = (String) handleClassMethodGetValue(joinPoint, returnObject, classReturnType, "getCode");
                        String newKey = key = cacheService.buildCodeIdKey(signature, code);
                        logger.debug("Put in cache '" + cacheNameCodeId + "'. key : '" + newKey + "'. value: '" + id + "'");
                        cacheCodeId.put(newKey, id);
                        
                    } else if (cacheType.equals(CacheType.CACHE_LINK_CODE_ID)) {
                        Long id = (Long) handleClassMethodGetValue(joinPoint, returnObject, classReturnType, "getId");
                        logger.debug("Put in cache '" + cacheName + "'. key : '" + key + "'. value: '" + id + "'");
                        cache.put(key, id);
                        
                        // PUT THE ENTITY
                        String cacheNameEntity = cacheName.replace("_link_code_id", "");
                        Cache cacheEntity = cacheService.getCache(cacheNameEntity, String.class, AbstractEntity.class);
                        String newKey = cacheService.buildEntityKey(signature.getReturnType(), id.toString());
                        logger.debug("Put in cache '" + cacheName + "'. key : '" + newKey + "'. value: '" + returnObject + "'");
                        cacheEntity.put(newKey, returnObject);
                        
                    } else if (cacheType.equals(CacheType.CACHE_POJO)) {
                        logger.debug("Put in cache '" + cacheName + "'. key : '" + key + "'. value: '" + returnObject + "'");
                        cache.put(key, returnObject);
                        
                    }  else if (cacheType.equals(CacheType.CACHE_STRING)) {
                        logger.debug("Put in cache '" + cacheName + "'. key : '" + key + "'. value: '" + returnObject + "'");
                        cache.put(key, returnObject);
                    } 
                }
            }

        } catch (Exception e) {
            logger.error("Failed to load datas with Cache AOP! Call from : '" + joinPoint.getSignature().toShortString() + "'", e);
        }
        
        logger.debug("End Cache AOP. Call from : '" + joinPoint.getSignature().toShortString() + "'");
        logger.debug("---------------------------------------------------------------------------------");

        return returnObject;
    }
    
    protected Object handleClassMethodGetValue(ProceedingJoinPoint joinPoint, Object returnObject, Class classReturnType, String methode) {
        try {
            Method[] methods = classReturnType.getMethods();
            for (Method methodIt : methods) {
                if (methodIt.getName().equals(methode)) {
                    return methodIt.invoke(returnObject);
                }
            }
        } catch (Exception e) {
            logger.debug("Exception code. Call from : '" + joinPoint.getSignature().toShortString() + "'", e);
        }
        return null;
    }
    
    protected FetchPlan checkFetchPlan(Object returnObject, FetchPlan askedFetchPlan, FetchPlan loadedFetchPlan){
        if (returnObject instanceof DomainEntity) {
            AbstractEntity entity = (AbstractEntity) returnObject;
            if (entity.getFetchPlan() != null) {
                loadedFetchPlan = entity.getFetchPlan();
            }
        }
        
        if (askedFetchPlan != null) {
            if (loadedFetchPlan != null && !loadedFetchPlan.containAllTargetFetchPlans(askedFetchPlan)) {
                // ENTITY IS LOAD WITHOUT FETCHPLAN - WE RESET THE returnObject TO TRIGGER THE RELOAD WITH THE FETCHPLAN
                // WE WILL ADD LOADED FETCH PLAN AND ASKED FETCH PLAN TO THE INVOCATED METHOD
                returnObject = null;
            }
        }
        return loadedFetchPlan;
    }
    
}