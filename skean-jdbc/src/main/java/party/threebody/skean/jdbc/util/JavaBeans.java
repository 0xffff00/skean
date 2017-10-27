/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package party.threebody.skean.jdbc.util;

import org.apache.commons.beanutils.PropertyUtils;
import party.threebody.skean.misc.SkeanReflectionException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.function.Function;

public class JavaBeans {

    final static Class<?>[] SIMPLE_CLASSES = {char.class, boolean.class, byte.class,
            int.class, long.class, short.class, float.class, double.class,
            Number.class, String.class, Character.class, Date.class, Temporal.class, Boolean.class};

    private JavaBeans() {
    }

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
        List<Object> res=new ArrayList<>(propertyNames.size());
        for (String propertyName:propertyNames){
            res.add(getProperty(bean, propertyName));
        }
        return res;
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
}
