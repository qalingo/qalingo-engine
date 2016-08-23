package org.hoteia.qalingo.core.web.cache.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.reflect.MethodSignature;
import org.dom4j.tree.AbstractEntity;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.xml.XmlConfiguration;
import org.ehcache.xml.model.BaseCacheType;
import org.ehcache.xml.model.CacheType;
import org.ehcache.xml.model.ConfigType;
import org.hoteia.qalingo.core.annotation.CacheEntityInformation;
import org.hoteia.qalingo.core.domain.impl.DomainEntity;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

@Service("cacheService")
public class CacheService {

    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private CacheManager cacheManager;
    
    @Autowired
    protected ApplicationContext applicationContext;
    
    @Value("${cache.configuration.location}")
    protected String ehcacheConfiguration;
    
    public List<CacheType> getCaches() {
        List<CacheType> caches = new ArrayList<CacheType>();
        try {
            Resource resource = applicationContext.getResource(ehcacheConfiguration);
            
            Class<ConfigType> configTypeClass = ConfigType.class;
            JAXBContext jaxbContext = JAXBContext.newInstance(ConfigType.class.getPackage().getName(), configTypeClass.getClassLoader());

            Collection<Source> schemaSources = new ArrayList<Source>();
            schemaSources.add(new StreamSource(XmlConfiguration.class.getResource("/ehcache-core.xsd").openStream()));
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaSources.toArray(new Source[schemaSources.size()])));

            DocumentBuilder domBuilder = factory.newDocumentBuilder();
            Element dom = domBuilder.parse(resource.getFile()).getDocumentElement();

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ConfigType config = unmarshaller.unmarshal(dom, configTypeClass).getValue();

            List<BaseCacheType> baseCacheTypes = config.getCacheOrCacheTemplate();
            for (BaseCacheType baseCacheType : baseCacheTypes) {
                caches.add((CacheType)baseCacheType);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return caches;
    }

    public void flushCaches(List<CacheType> caches) throws ClassNotFoundException {
        for (CacheType cache : caches) {
            flushCache(cache);
        }
    }

    public void flushCache(CacheType cache) throws ClassNotFoundException {
        if(cache != null){
            Cache cacheRuntime = getCache(cache.getAlias(), Class.forName(cache.getKeyType().getValue()), Class.forName(cache.getValueType().getValue()));
            cacheRuntime.clear();
        }
    }
    
    public Cache getCache(String cacheAlias, Class key, Class type) throws ClassNotFoundException {
        if(StringUtils.isNotEmpty(cacheAlias)){
            return cacheManager.getCache(cacheAlias, key, type);
        }
        return null;
    }
    
    public void flushCacheEntity(String cacheAlias) throws ClassNotFoundException {
        if(StringUtils.isNotEmpty(cacheAlias)){
            Cache cache = getCache(cacheAlias, String.class, DomainEntity.class);
            cache.clear();
        }
    }
    
    public void flushCacheEntity(String entityClass, String id) {
        if(StringUtils.isNotEmpty(entityClass) && StringUtils.isNotEmpty(id)){
            try {
                Class classObject = Class.forName(entityClass);
                
                CacheEntityInformation cacheEntityInformation = (CacheEntityInformation) classObject.getAnnotation(CacheEntityInformation.class);
                String cacheName = cacheEntityInformation.cacheName();
                logger.debug("CacheEntityInformation from annotation : cacheName= '" + cacheName + "'");

                String key = buildEntityKey(classObject, id);
                Cache cache = cacheManager.getCache(cacheName, String.class, DomainEntity.class);
                cache.remove(key);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String buildCommonKey(MethodSignature signature, Object[] args){
        StringBuffer key = new StringBuffer();
        key.append(signature.toShortString());
        if(args != null && args.length > 0){
            for (Object arg : args) {
                if (arg instanceof DomainEntity) {
                    AbstractEntity argEntity = (AbstractEntity) arg;
                    Method[] methods = argEntity.getClass().getMethods();
                    for (Method methodIt : methods) {
                        if (methodIt.getName().equals("getId")) {
                            Long id;
                            try {
                                id = (Long) methodIt.invoke(argEntity);
                                key.append("_");
                                key.append(id);
                            } catch (Exception e) {
                                // NOTHING
                            }
                        }
                    }
                } else {
                    if (arg != null && !(arg instanceof Object[]) && !(arg instanceof DomainEntity)&& !(arg instanceof FetchPlan)) {
                        key.append("_");
                        key.append(arg.toString());
                    }
                }
            }
        }
        return key.toString();
    }
    
    public String buildEntityKey(Class classReturnType, String id){
        StringBuffer key = new StringBuffer();
        key.append(classReturnType.getName());
        key.append("_");
        key.append(id);
        return key.toString();
    }
    
    public String buildCodeIdKey(MethodSignature signature, String code){
        StringBuffer key = new StringBuffer();
        Class classReturnType = signature.getReturnType();
        key.append(classReturnType.getName());
        key.append("_");
        key.append(code);
        return key.toString();
    }
    
//    public String buildCodeIdKey(MethodSignature signature, Object[] args){
//        String code = null;
//        if(args != null && args.length > 0){
//            for (Object arg : args) {
//                if (arg != null && !(arg instanceof Object[]) && !(arg instanceof AbstractEntity)&& !(arg instanceof FetchPlan)) {
//                    code = arg.toString();
//                }
//            }
//        }
//        return buildCodeIdKey(signature, code);
//    }
    
}
