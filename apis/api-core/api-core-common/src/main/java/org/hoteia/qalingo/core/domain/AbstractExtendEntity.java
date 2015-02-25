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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

public abstract class AbstractExtendEntity<E, A extends AbstractAttribute<A>> extends AbstractEntity<E> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -171223653896353957L;

    abstract public Set<A> getAttributes();
    
    public List<A> getGlobalAttributes() {
        List<A> productSkuGlobalAttributes = null;
        if (getAttributes() != null
                && Hibernate.isInitialized(getAttributes())) {
            productSkuGlobalAttributes = new ArrayList<A>();
            for (Iterator<A> iterator = getAttributes().iterator(); iterator.hasNext();) {
                A attribute = (A) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && attributeDefinition.isGlobal()) {
                    productSkuGlobalAttributes.add(attribute);
                }
            }
        }        
        return productSkuGlobalAttributes;
    }

    public List<A> getMarketAreaAttributes(Long marketAreaId) {
        List<A> productSkuMarketAreaAttributes = null;
        if (getAttributes() != null
                && Hibernate.isInitialized(getAttributes())) {
            productSkuMarketAreaAttributes = new ArrayList<A>();
            for (Iterator<A> iterator = getAttributes().iterator(); iterator.hasNext();) {
                A attribute = (A) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && !attributeDefinition.isGlobal()) {
                    productSkuMarketAreaAttributes.add(attribute);
                }
            }
        }        
        return productSkuMarketAreaAttributes;
    }
    
    public A getAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
        A AbstractAttributeToReturn = null;

        // 1: GET THE GLOBAL VALUE
        A globalAttribute = getAttribute(getGlobalAttributes(), attributeCode, marketAreaId, localizationCode);

        // 2: GET THE MARKET AREA VALUE
        A marketAreaAttribute = getAttribute(getMarketAreaAttributes(marketAreaId), attributeCode, marketAreaId, localizationCode);
        
        if(marketAreaAttribute != null){
            AbstractAttributeToReturn = marketAreaAttribute;
        } else if (globalAttribute != null){
            AbstractAttributeToReturn = globalAttribute;
        }
        
        return AbstractAttributeToReturn;
    }
    
    public A getAttribute(List<A> attributes, String attributeCode, Long marketAreaId, String localizationCode) {
        A attributeToReturn = null;
        List<A> attributesFilter = new ArrayList<A>();
        if(attributes != null) {
            // GET ALL attributes FOR THIS ATTRIBUTE
            for (Iterator<A> iterator = attributes.iterator(); iterator.hasNext();) {
                A attribute = (A) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if(attributeDefinition != null
                        && attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
                    attributesFilter.add(attribute);
                }
            }
            // REMOVE ALL attributes NOT ON THIS MARKET AREA
            if(marketAreaId != null) {
                for (Iterator<A> iterator = attributesFilter.iterator(); iterator.hasNext();) {
                    A attribute = (A) iterator.next();
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
                for (Iterator<A> iterator = attributesFilter.iterator(); iterator.hasNext();) {
                    A attribute = (A) iterator.next();
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
    
    public A getAttribute(String attributeCode) {
        return getAttribute(attributeCode, null, null);
    }

    public A getAttribute(String attributeCode, String localizationCode) {
        return getAttribute(attributeCode, null, localizationCode);
    }

    public A getAttribute(String attributeCode, Long marketAreaId) {
        return getAttribute(attributeCode, marketAreaId, null);
    }
}
