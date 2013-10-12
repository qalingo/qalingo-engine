package fr.hoteia.qalingo.core.rest.util.impl;

import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.domain.Store;
import fr.hoteia.qalingo.core.rest.pojo.StoreJsonPojo;

@Component("storeMapper")
public class StoreMapper extends AbstractPojoMapper<Store, StoreJsonPojo> {

    @Override
    public Class<Store> getObjectType() {
        return Store.class;
    }

    @Override
    public Class<StoreJsonPojo> getPojoType() {
        return StoreJsonPojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return new String[] { "storeAttributes" };
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final StoreJsonPojo jsonPojo, final Store object) {
        super.mapAdditionalPropertiesFromPojo(jsonPojo, object);
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final Store object, final StoreJsonPojo jsonPojo) {
        super.mapAdditionalPropertiesToPojo(object, jsonPojo);
    }

}
