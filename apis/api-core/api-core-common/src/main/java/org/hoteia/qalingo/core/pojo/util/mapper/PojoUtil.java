/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.util.mapper;

import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PojoUtil {
    
    public static <T> List<T> asList(Collection<T> collection) {
        if(collection != null){
            return new ArrayList<T>(collection);
        }
        return null;
    }

    public static <T> List<T> mapAll(Mapper mapper, Collection<?> sources, Class<T> outputType) {
        List<T> result = new ArrayList<T>(sources.size());
        for(Object source: sources) {
            result.add(mapper.map(source, outputType));
        }
        return result;
    }

}