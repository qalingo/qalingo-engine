package fr.hoteia.qalingo.core.rest.util.impl;

import java.util.ArrayList;
import java.util.List;

import org.objenesis.ObjenesisStd;
import org.springframework.beans.BeanUtils;

import fr.hoteia.qalingo.core.json.pojo.AbstractJsonPojo;
import fr.hoteia.qalingo.core.rest.util.JsonBuilder;

public abstract class AbstractJsonBuilder<O, T extends AbstractJsonPojo> implements JsonBuilder<O, T> {

    public abstract Class<O> getObjectType();

    public abstract Class<T> getPojoType();

    @Override
    public O fromPojo(final T jsonPojo) {
        O mappedObject = instantiate(getObjectType());
        BeanUtils.copyProperties(jsonPojo, mappedObject, getIgnoredProperties());
        mapAdditionalPropertiesFromPojo(jsonPojo, mappedObject);
        return mappedObject;
    }

    @SuppressWarnings("unchecked")
    protected <N> N instantiate(final Class<N> newType) {
        return (N) new ObjenesisStd().newInstance(newType);
    }

    protected String[] getIgnoredProperties() {
        return null;
    }

    protected void mapAdditionalPropertiesFromPojo(final T jsonPojo, final O object) {}

    @Override
    public List<O> fromPojo(final List<T> pojosToMap) {
        final List<O> mappedObjects = new ArrayList<O>();
        for (T objectToMap : pojosToMap) {
            mappedObjects.add(fromPojo(objectToMap));
        }
        return mappedObjects;
    }

    @Override
    public List<T> toPojo(final List<O> objectsToMap) {
        final List<T> jsonPojos = new ArrayList<T>();
        for (O objectToMap : objectsToMap) {
            jsonPojos.add(toPojo(objectToMap));
        }
        return jsonPojos;
    }

}
