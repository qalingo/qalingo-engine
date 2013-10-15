package fr.hoteia.qalingo.core.pojo.util.mapper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

public class PojoUtil {

    private PojoUtil() {
    }

    public static <T> List<T> asList(Collection<T> collection) {
        return new ArrayList<T>(collection);
    }

    public static <T> Set<T> asSet(Collection<T> collection) {
        return new HashSet<T>(collection);
    }


    public static Map<String, String> recursiveDescribe(Object object) {
        Set cache = new HashSet();
        return recursiveDescribe(object, null, cache);
    }

    private static Map<String, String> recursiveDescribe(Object object, String prefix, Set cache) {
        if (object == null || cache.contains(object)) return Collections.EMPTY_MAP;
        cache.add(object);
        prefix = (prefix != null) ? prefix + "." : "";

        Map<String, String> beanMap = new TreeMap<String, String>();

        Map<String, Object> properties = getProperties(object);
        for (String property : properties.keySet()) {
            Object value = properties.get(property);
            try {
                if (value == null) {
                    //ignore nulls
                } else if (Collection.class.isAssignableFrom(value.getClass())) {
                    beanMap.putAll(convertAll((Collection) value, prefix + property, cache));
                } else if (value.getClass().isArray()) {
                    beanMap.putAll(convertAll(Arrays.asList((Object[]) value), prefix + property, cache));
                } else if (Map.class.isAssignableFrom(value.getClass())) {
                    beanMap.putAll(convertMap((Map) value, prefix + property, cache));
                } else {
                    beanMap.putAll(convertObject(value, prefix + property, cache));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return beanMap;
    }

    private static Map<String, Object> getProperties(Object object) {
        Map<String, Object> propertyMap = getFieldsWithValue(object);
        //getters take precedence in case of any name collisions
        propertyMap.putAll(getGetterMethods(object));
        return propertyMap;
    }

    private static Map<String, Object> getGetterMethods(Object object) {
        Map<String, Object> result = new HashMap<String, Object>();
        BeanInfo info;
        try {
            info = Introspector.getBeanInfo(object.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                if (reader != null) {
                    String name = pd.getName();
                    if (!"class".equals(name)) {
                        try {
                            Object value = reader.invoke(object);
                            result.put(name, value);
                        } catch (Exception e) {
                            //you can choose to do something here
                        }
                    }
                }
            }
        } catch (IntrospectionException e) {
            //you can choose to do something here
        } finally {
            return result;
        }

    }

    private static Map<String, Object> getFieldsWithValue(Object object) {
        return getFieldsWithValue(object, object.getClass());
    }

    private static Map<String, Object> getFieldsWithValue(Object object, Class<?> classType) {
        Map<String, Object> result = new HashMap<String, Object>();
        getFieldsWithValueFromSuperclass(object, classType, result);
        populateFieldsWithValue(object, classType, result);
        return result;
    }

    private static void populateFieldsWithValue(Object object, Class<?> classType, Map<String, Object> result) {
        Field[] fields = classType.getFields();
        for (Field field : fields) {
            try {
                result.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                //you can choose to do something here
            }
        }
    }

    private static void getFieldsWithValueFromSuperclass(Object object, Class<?> classType, Map<String, Object> result) {
        Class superClass = classType.getSuperclass();
        if (superClass != null) result.putAll(getFieldsWithValue(object, superClass));
    }

    private static Map<String, String> convertAll(Collection<Object> values, String key, Set cache) {
        Map<String, String> valuesMap = new HashMap<String, String>();
        Object[] valArray = values.toArray();
        for (int i = 0; i < valArray.length; i++) {
            Object value = valArray[i];
            if (value != null) valuesMap.putAll(convertObject(value, key + "[" + i + "]", cache));
        }
        return valuesMap;
    }

    private static Map<String, String> convertMap(Map<Object, Object> values, String key, Set cache) {
        Map<String, String> valuesMap = new HashMap<String, String>();
        for (Object thisKey : values.keySet()) {
            Object value = values.get(thisKey);
            if (value != null) valuesMap.putAll(convertObject(value, key + "[" + thisKey + "]", cache));
        }
        return valuesMap;
    }

    private static Map<String, String> convertObject(Object value, String key, Set cache) {
        if (hasRegisteredConverter(value)) {
            return ArrayUtils.toMap(new Object[] {key, convertValueToString(value)});
        } else {
            return recursiveDescribe(value, key, cache);
        }
    }

    private static String convertValueToString(Object value) {
        ConvertUtilsBean converter = BeanUtilsBean.getInstance().getConvertUtils();
        return converter.convert(value);
    }

    private static boolean hasRegisteredConverter(Object value) {
        ConvertUtilsBean converter = BeanUtilsBean.getInstance().getConvertUtils();
        return converter.lookup(value.getClass()) != null;
    }
}
