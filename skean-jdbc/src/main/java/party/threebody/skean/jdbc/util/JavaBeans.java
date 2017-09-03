package party.threebody.skean.jdbc.util;

import org.apache.commons.beanutils.PropertyUtils;
import party.threebody.skean.SkeanException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JavaBeans {

    private JavaBeans() {
    }

    public static Object[] getProperties(Object bean, String[] propertyNames) {
        if (propertyNames == null) {
            return null;
        }
        int n = propertyNames.length;
        Object[] vals = new Object[n];
        for (int i = 0; i < n; i++) {
            try {
                vals[i] = PropertyUtils.getSimpleProperty(bean, propertyNames[i]);

            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new SkeanReflectionException(
                        "fail to get property[" + propertyNames[i] + "] in bean[" + bean + "]", e);
            }
        }
        return vals;
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

    static Class<?>[] SIMPLE_CLASSES = {char.class, boolean.class, byte.class,
            int.class, long.class, short.class, float.class, double.class,
            Number.class, String.class, Character.class, Date.class, Temporal.class, Boolean.class};

    public static boolean isSimpleType(Class<?> clazz) {
        for (Class<?> simpleClass : SIMPLE_CLASSES) {
            if (simpleClass.isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSimpleTypeOf(Object v) {
        return (v instanceof Number) || (v instanceof String) || (v instanceof Character) ||
                (v instanceof Date) || (v instanceof Temporal) || (v instanceof Boolean);

    }
}

@SuppressWarnings("serial")
class SkeanReflectionException extends SkeanException {

    public SkeanReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

}