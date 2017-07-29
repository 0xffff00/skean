package tmp.experiment;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class LearnBeanReflect {

	public static void main(String[] args) throws Exception {
		Stu stu = new Stu("Mary", 19, null, null, null);

		BeanInfo bi = Introspector.getBeanInfo(stu.getClass());
		PropertyDescriptor[] pds = bi.getPropertyDescriptors();
		
		for (int i = 0; i < pds.length; i++) {
			// Get property name
			
			String propName = pds[i].getName();
			System.out.println(pds[i]);
		}

		// class, prop1
	}

}
