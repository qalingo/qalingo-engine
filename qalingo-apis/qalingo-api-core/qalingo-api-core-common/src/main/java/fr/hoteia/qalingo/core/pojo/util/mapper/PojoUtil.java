package fr.hoteia.qalingo.core.pojo.util.mapper;

import java.util.*;

public class PojoUtil {

    private PojoUtil() {
    }

    public static <T> List<T> asList(Collection<T> collection) {
        return new ArrayList<T>(collection);
    }

    public static  <T> Set<T> asSet(Collection<T> collection) {
        return new HashSet<T>(collection);
    }
}
