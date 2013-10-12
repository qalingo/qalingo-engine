package fr.hoteia.qalingo.core.rest.util;

import java.util.List;

import fr.hoteia.qalingo.core.json.pojo.AbstractJsonPojo;

/**
 * 
 * @author Fingy
 * 
 * @param <O>
 *            POJO type that Json POJO maps to
 * @param <T>
 *            Json POJO type
 */
public interface JsonBuilder<O, T extends AbstractJsonPojo> {

    O fromPojo(final T jsonPojo);

    List<O> fromPojo(final List<T> toMap);

    T toPojo(final O toMap);

    List<T> toPojo(final List<O> toMap);

}