package tmp.experiment;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

public class LangLearn2 {

	@Test
	public void learn1() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Car c1 = new Car(2, 4, "vc", "tr");
		c1.setArr(new String[]{"x","y","z"});
		c1.setDates(Arrays.asList(new Date(),new Date()));
		Map m1=new HashMap();
		m1.put("k1", "v1");
		m1.put("k3", "dfdsf");
		c1.setM1(m1);
		Object[] ps = getPropVals(c1, new String[] {"arr","dates","value","x3", "s1","s2333"});
		
		System.out.println(Arrays.toString(ps));
		Object obb=PropertyUtils.getProperty(c1, "m1");
		Object obj1=PropertyUtils.getProperty(c1, "arr");
		Object ddd1=PropertyUtils.getProperty(c1, "dates");
		
	}

	Object[] getPropVals(Object bean, String[] props)
			throws IllegalAccessException, InvocationTargetException {
		int n = props.length;
		Object[] vals = new Object[n];
		for (int i = 0; i < n; i++) {
			try {
				vals[i] = PropertyUtils.getProperty(bean, props[i]);
			} catch (NoSuchMethodException e) {
				//ignore
			}
		}
		return vals;
	}
	

}