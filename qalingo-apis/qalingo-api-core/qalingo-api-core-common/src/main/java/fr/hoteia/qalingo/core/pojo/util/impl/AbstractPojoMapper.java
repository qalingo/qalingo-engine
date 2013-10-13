package fr.hoteia.qalingo.core.pojo.util.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.objenesis.ObjenesisStd;
import org.springframework.beans.BeanUtils;

import fr.hoteia.qalingo.core.pojo.util.PojoMapper;

public abstract class AbstractPojoMapper<O, T> implements PojoMapper<O, T> {

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
    public Collection<O> fromPojo(final Collection<T> pojosToMap) {
        final List<O> mappedObjects = new ArrayList<O>();
        for (T objectToMap : pojosToMap) {
            mappedObjects.add(fromPojo(objectToMap));
        }
        return mappedObjects;
    }

    @Override
    public Collection<T> toPojo(final Collection<O> objectsToMap) {
        final List<T> jsonPojos = new ArrayList<T>();
        for (O objectToMap : objectsToMap) {
            jsonPojos.add(toPojo(objectToMap));
        }
        return jsonPojos;
    }

    @Override
    public T toPojo(final O objectToMap) {
        final T jsonPojo = instantiate(getPojoType());
        BeanUtils.copyProperties(objectToMap, jsonPojo, getIgnoredProperties());
        mapAdditionalPropertiesToPojo(objectToMap, jsonPojo);
        return jsonPojo;
    }

    protected void mapAdditionalPropertiesToPojo(final O object, final T jsonPojo) {}

}
