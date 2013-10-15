package fr.hoteia.qalingo.core.pojo.util.mapper.store;

import java.util.Collection;

import fr.hoteia.qalingo.core.domain.Store;
import fr.hoteia.qalingo.core.domain.StoreAttribute;
import fr.hoteia.qalingo.core.pojo.store.StoreAttributePojo;
import fr.hoteia.qalingo.core.pojo.store.StorePojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
import fr.hoteia.qalingo.core.pojo.util.mapper.PojoMapper;
import fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("storeMapper")
public class StoreMapper extends AbstractPojoMapper<Store, StorePojo> {

    private static final String[] IGNORED_PROPERTIES = {"storeAttributes"};

    @Autowired @Qualifier("storeAttributeMapper") private PojoMapper<StoreAttribute, StoreAttributePojo> storeAttributeMapper;

    @Override
    public Class<Store> getObjectType() {
        return Store.class;
    }

    @Override
    public Class<StorePojo> getPojoType() {
        return StorePojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final StorePojo jsonPojo, final Store object) {
        Collection<StoreAttribute> storeAttributes = storeAttributeMapper.fromPojo(jsonPojo.getStoreAttributes());
        object.setStoreAttributes(PojoUtil.asSet(storeAttributes));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final Store object, final StorePojo jsonPojo) {
        Collection<StoreAttributePojo> storeAttributes = storeAttributeMapper.toPojo(object.getStoreAttributes());
        jsonPojo.setStoreAttributes(storeAttributes);
    }
}
