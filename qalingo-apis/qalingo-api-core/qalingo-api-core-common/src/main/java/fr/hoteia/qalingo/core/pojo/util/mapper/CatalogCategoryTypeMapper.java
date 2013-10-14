package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.CatalogCategoryType;
import fr.hoteia.qalingo.core.domain.CatalogCategoryTypeAttribute;
import fr.hoteia.qalingo.core.pojo.CatalogCategoryTypeAttributePojo;
import fr.hoteia.qalingo.core.pojo.CatalogCategoryTypePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component("catalogCategoryTypeMapper")
public class CatalogCategoryTypeMapper extends AbstractPojoMapper<CatalogCategoryType, CatalogCategoryTypePojo> {

    private static final String[] IGNORED_PROPERTIES = {"catalogCategoryTypeAttributes"};

    @Autowired @Qualifier("catalogCategoryTypeAttributeMapper") private PojoMapper<CatalogCategoryTypeAttribute, CatalogCategoryTypeAttributePojo> categoryTypeAttributeMapper;

    @Override
    public Class<CatalogCategoryType> getObjectType() {
        return CatalogCategoryType.class;
    }

    @Override
    public Class<CatalogCategoryTypePojo> getPojoType() {
        return CatalogCategoryTypePojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(CatalogCategoryType object, CatalogCategoryTypePojo jsonPojo) {
        Collection<CatalogCategoryTypeAttributePojo> catalogCategoryTypeAttributes = categoryTypeAttributeMapper.toPojo(object.getCatalogCategoryTypeAttributes());
        jsonPojo.setCatalogCategoryTypeAttributes(catalogCategoryTypeAttributes);
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(CatalogCategoryTypePojo jsonPojo, CatalogCategoryType object) {
        Collection<CatalogCategoryTypeAttribute> catalogCategoryTypeAttributes = categoryTypeAttributeMapper.fromPojo(jsonPojo.getCatalogCategoryTypeAttributes());
        object.setCatalogCategoryTypeAttributes(new HashSet<CatalogCategoryTypeAttribute>(catalogCategoryTypeAttributes));
    }
}
