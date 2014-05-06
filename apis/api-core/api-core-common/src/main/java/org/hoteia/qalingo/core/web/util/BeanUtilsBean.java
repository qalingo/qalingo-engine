/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class BeanUtilsBean extends org.springframework.beans.BeanUtils { 
    
    public static void copyNotNullProperties(final Object source, final Object target, final Iterable<String> properties){
            final BeanWrapper src = new BeanWrapperImpl(source);
            final BeanWrapper trg = new BeanWrapperImpl(target);
            for(final String propertyName : properties){
                trg.setPropertyValue(propertyName, src.getPropertyValue(propertyName)
                );
            }
        }
    
//    public void copyNotNullProperties(Object source, Object target) throws BeansException {
//        String[] ignoreEcoEngineSessionProperties = new String[] {"environmentStagingModeEnabled","environmentType","currentCustomer","currentMarketPlace","currentMarket","currentMarketArea","currentMarketAreaLocalization","currentMarketAreaRetailer","currentMarketAreaCurrency","currentUser","lastOrders","theme","device"};
//        String[] ignoreCartProperties = new String[] {"taxes","deliveryMethods"};
//        String[] ignoreCartItemProperties = new String[] {"taxes","deliveryMethod","productSku","cartItemPrice"};
//        List<String> allIgnoreProperties = new ArrayList<String>();
//        allIgnoreProperties.addAll(Arrays.asList(ignoreEcoEngineSessionProperties));
//        allIgnoreProperties.addAll(Arrays.asList(ignoreCartProperties));
//        allIgnoreProperties.addAll(Arrays.asList(ignoreCartItemProperties));
//        String[] ignoreProperties = new String[allIgnoreProperties.size()];
//        copyProperties(source, target, allIgnoreProperties.toArray(ignoreProperties));
//    }
    
//    extends org.apache.commons.beanutils.BeanUtilsBean {
//}
//
//    public void copyNotNullProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {
//        copyProperties(dest, orig);
//    }
//    
//    @Override
//    public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
//        if (value == null) {
//            return;
//        } else if (value instanceof Set && ((Set)value).isEmpty()) {
//            return;
//        }
//        super.copyProperty(bean, name, value);
//    }
//    
}