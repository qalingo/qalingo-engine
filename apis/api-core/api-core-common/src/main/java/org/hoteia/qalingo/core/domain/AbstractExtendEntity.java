/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Transient;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;

public abstract class AbstractExtendEntity<E> implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -171223653896353957L;

    @Transient
    private FetchPlan fetchPlan;

    public FetchPlan getFetchPlan() {
        return fetchPlan;
    }
    
    public void setFetchPlan(FetchPlan fetchPlan) {
        this.fetchPlan = fetchPlan;
    }
    
    abstract public Set<E> getAttributes();
    
    public List<AbstractAttribute> getGlobalAttributes() {
        List<AbstractAttribute> productSkuGlobalAttributes = null;
        if (getAttributes() != null
                && Hibernate.isInitialized(getAttributes())) {
            productSkuGlobalAttributes = new ArrayList<AbstractAttribute>();
            for (Iterator<E> iterator = getAttributes().iterator(); iterator.hasNext();) {
                AbstractAttribute attribute = (AbstractAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && attributeDefinition.isGlobal()) {
                    productSkuGlobalAttributes.add(attribute);
                }
            }
        }        
        return productSkuGlobalAttributes;
    }

    public List<AbstractAttribute> getMarketAreaAttributes(Long marketAreaId) {
        List<AbstractAttribute> productSkuMarketAreaAttributes = null;
        if (getAttributes() != null
                && Hibernate.isInitialized(getAttributes())) {
            productSkuMarketAreaAttributes = new ArrayList<AbstractAttribute>();
            for (Iterator<E> iterator = getAttributes().iterator(); iterator.hasNext();) {
                AbstractAttribute attribute = (AbstractAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && !attributeDefinition.isGlobal()) {
                    productSkuMarketAreaAttributes.add(attribute);
                }
            }
        }        
        return productSkuMarketAreaAttributes;
    }
    
    public AbstractAttribute getAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
        AbstractAttribute AbstractAttributeToReturn = null;

        // 1: GET THE GLOBAL VALUE
        AbstractAttribute globalAttribute = getAttribute(getGlobalAttributes(), attributeCode, marketAreaId, localizationCode);

        // 2: GET THE MARKET AREA VALUE
        AbstractAttribute marketAreaAttribute = getAttribute(getMarketAreaAttributes(marketAreaId), attributeCode, marketAreaId, localizationCode);
        
        if(marketAreaAttribute != null){
            AbstractAttributeToReturn = marketAreaAttribute;
        } else if (globalAttribute != null){
            AbstractAttributeToReturn = globalAttribute;
        }
        
        return AbstractAttributeToReturn;
    }
    
    public AbstractAttribute getAttribute(List<AbstractAttribute> attributes, String attributeCode, Long marketAreaId, String localizationCode) {
        AbstractAttribute attributeToReturn = null;
        List<AbstractAttribute> attributesFilter = new ArrayList<AbstractAttribute>();
        if(attributes != null) {
            // GET ALL attributes FOR THIS ATTRIBUTE
            for (Iterator<AbstractAttribute> iterator = attributes.iterator(); iterator.hasNext();) {
                AbstractAttribute attribute = (AbstractAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if(attributeDefinition != null
                        && attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
                    attributesFilter.add(attribute);
                }
            }
            // REMOVE ALL attributes NOT ON THIS MARKET AREA
            if(marketAreaId != null) {
                for (Iterator<AbstractAttribute> iterator = attributesFilter.iterator(); iterator.hasNext();) {
                    AbstractAttribute attribute = (AbstractAttribute) iterator.next();
                    AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                    if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
                        if(attribute.getMarketAreaId() != null
                                && BooleanUtils.negate(attribute.getMarketAreaId().equals(marketAreaId))){
                            iterator.remove();
                        }
                    }
                }
            }
            // FINALLY RETAIN ONLY attributes FOR THIS LOCALIZATION CODE
            if(StringUtils.isNotEmpty(localizationCode)) {
                for (Iterator<AbstractAttribute> iterator = attributesFilter.iterator(); iterator.hasNext();) {
                    AbstractAttribute attribute = (AbstractAttribute) iterator.next();
                    String attributeLocalizationCode = attribute.getLocalizationCode();
                    if(StringUtils.isNotEmpty(attributeLocalizationCode)
                            && BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
                        iterator.remove();
                    }
                }
                if(attributesFilter.size() == 0){
                    // TODO : warning ?

                }
            }
        }
        
        if(attributesFilter.size() == 1){
            attributeToReturn = attributesFilter.get(0);
        } else {
            // TODO : throw error ?
        }
        
        return attributeToReturn;
    }
    
    public AbstractAttribute getAttribute(String attributeCode) {
        return getAttribute(attributeCode, null, null);
    }

    public AbstractAttribute getAttribute(String attributeCode, String localizationCode) {
        return getAttribute(attributeCode, null, localizationCode);
    }

    public AbstractAttribute getAttribute(String attributeCode, Long marketAreaId) {
        return getAttribute(attributeCode, marketAreaId, null);
    }
}
