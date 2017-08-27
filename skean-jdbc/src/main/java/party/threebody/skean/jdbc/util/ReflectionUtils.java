package party.threebody.skean.jdbc.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import party.threebody.skean.SkeanException;

public class ReflectionUtils {

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
}

@SuppressWarnings("serial")
class SkeanReflectionException extends SkeanException {

	public SkeanReflectionException(String message, Throwable cause) {
		super(message, cause);
	}

}