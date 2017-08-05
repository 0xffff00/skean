package tmp.experiment;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

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
		System.out.println(Arrays.toString(Stu.class.getFields()));
		System.out.println(Arrays.toString(Stu.class.getDeclaredFields()));
		System.out.println(Arrays.toString(Stu1.class.getFields()));
		System.out.println(Arrays.toString(Stu1.class.getDeclaredFields()));
		
	}
	
	class Stu1 extends Stu{
		
		public int x;
		int x1;
		protected int x2;
		private int x3;
		private int x4;
		public Stu1(String name, int age, String[] arr, Map m1, Set<Stu> friends) {
			super(name, age, arr, m1, friends);
			// TODO Auto-generated constructor stub
		}
		public int getX4() {
			return x4;
		}
		public void setX4(int x4) {
			this.x4 = x4;
		}

	
	}

}
