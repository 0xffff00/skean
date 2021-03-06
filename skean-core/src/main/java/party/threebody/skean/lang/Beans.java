package party.threebody.skean.lang;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.ListUtils;
import party.threebody.skean.misc.SkeanReflectionException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.function.Function;

/**
 * Bean Reflection Utilities
 *
 * @since 0.3
 */
public class Beans {
    final static Class<?>[] SIMPLE_CLASSES = {char.class, boolean.class, byte.class,
            int.class, long.class, short.class, float.class, double.class,
            Number.class, String.class, Character.class, Date.class, Temporal.class, Boolean.class};

    public static Object getProperty(Object bean, String propertyName) {
        try {
            return PropertyUtils.getSimpleProperty(bean, propertyName);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new SkeanReflectionException(
                    "fail to get property[" + propertyName + "] in bean[" + bean + "]", e);
        }
    }

    public static Object[] getProperties(Object bean, String[] propertyNames) {
        if (propertyNames == null) {
            return null;
        }
        int n = propertyNames.length;
        Object[] vals = new Object[n];
        for (int i = 0; i < n; i++) {
            vals[i] = getProperty(bean, propertyNames[i]);
        }
        return vals;
    }

    public static List<Object> getProperties(Object bean, List<String> propertyNames) {
        if (propertyNames == null) {
            return null;
        }
        List<Object> res = new ArrayList<>(propertyNames.size());
        for (String propertyName : propertyNames) {
            res.add(getProperty(bean, propertyName));
        }
        return res;
    }

    /**
     * get all declared fields, including inherited fields from all super classes
     *
     * @param clazz
     * @return
     */
    public static List<Field> getAllDeclaredFields(Class<?> clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }
        return ListUtils.union(Arrays.asList(clazz.getDeclaredFields()),
                getAllDeclaredFields(clazz.getSuperclass()));
    }

    /**
     * like PropertyUtils.describe()
     *
     * @param bean
     * @return
     */
    public static Map<String, Object> convertBeanToMap(Object bean) {
        if (bean == null) {
            return null;
        }
        Map<String, Object> objectAsMap = new HashMap<>();
        BeanInfo info = null;
        try {
            info = Introspector.getBeanInfo(bean.getClass());

            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                if (reader != null) {
                    objectAsMap.put(pd.getName(), reader.invoke(bean));
                }
            }
            return objectAsMap;
        } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
            throw new SkeanReflectionException("convertBeanToMap failed.", e);
        }
    }

    public static Map<String, Object> convertBeanToSimpleMap(Object bean) {
        return convertBeanToSimpleMap(bean, Function.identity());
    }

    /**
     * convert all Getters of simple type in a bean to a 'propName->propVal' Map
     *
     * @param bean
     * @return
     */
    public static Map<String, Object> convertBeanToSimpleMap(Object bean, Function<String, String> propNameToMapKey) {
        if (bean == null) {
            return null;
        }
        Map<String, Object> objectAsMap = new HashMap<>();
        BeanInfo info = null;
        try {
            info = Introspector.getBeanInfo(bean.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                if (!isSimpleType(pd.getPropertyType())) {
                    continue;
                }
                Method reader = pd.getReadMethod();
                if (reader != null) {
                    String key = propNameToMapKey.apply(pd.getName());
                    objectAsMap.put(key, reader.invoke(bean));
                }
            }
            return objectAsMap;
        } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
            throw new SkeanReflectionException("convertBeanToMap failed.", e);
        }
    }

    public static boolean isSimpleType(Class<?> clazz) {
        for (Class<?> simpleClass : SIMPLE_CLASSES) {
            if (simpleClass.isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }

    public static boolean instanceOfSimpleType(Object v) {
        return (v instanceof Number) || (v instanceof String) || (v instanceof Character) ||
                (v instanceof Date) || (v instanceof Temporal) || (v instanceof Boolean);

    }

    /**
     * clone an object, using Apache Commons Beanutils but without checked exceptions
     *
     * @since 2.3
     */
    public static <T> T clone(T source) {
        try {
            return (T) BeanUtils.cloneBean(source);
        } catch (ReflectiveOperationException e) {
            throw new SkeanReflectionException("clone failed.", e);
        }
    }
}
