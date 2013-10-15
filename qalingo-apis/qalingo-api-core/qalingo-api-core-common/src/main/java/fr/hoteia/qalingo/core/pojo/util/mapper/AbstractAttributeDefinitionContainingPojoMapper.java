package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.pojo.AttributeDefinitionPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractAttributeDefinitionContainingPojoMapper<O, T> extends AbstractPojoMapper<O, T> {

    private static final String[] IGNORED_PROPERTIES = new String[] { "attributeDefinition" };

    @Autowired @Qualifier("attributeDefinitionMapper") private PojoMapper<AttributeDefinition, AttributeDefinitionPojo> attributeDefinitionMapper;

    public PojoMapper<AttributeDefinition, AttributeDefinitionPojo> getAttributeDefinitionMapper() {
        return attributeDefinitionMapper;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }
}
