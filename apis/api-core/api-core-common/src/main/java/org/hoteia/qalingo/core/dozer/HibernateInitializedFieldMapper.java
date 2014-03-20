package org.hoteia.qalingo.core.dozer;

import org.dozer.CustomFieldMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;
import org.springframework.stereotype.Component;

@Component("hibernateInitializedFieldMapper")
public class HibernateInitializedFieldMapper implements CustomFieldMapper {
    
    public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping) {
        return !org.hibernate.Hibernate.isInitialized(sourceFieldValue); //  || sourceFieldValue == null
    }
    
}
