package fr.hoteia.qalingo.core.pojo.util;

import java.util.Collection;

/**
 * 
 * @author Fingy
 * 
 * @param <O>
 *            Object type that POJO maps to
 * @param <T>
 *            Type of the POJO to map the object into
 */
public interface PojoMapper<O, T> {

    O fromPojo(final T jsonPojo);

    Collection<O> fromPojo(final Collection<T> jsonPojo);

    T toPojo(final O objectToMap);

    Collection<T> toPojo(final Collection<O> objectsToMap);

}