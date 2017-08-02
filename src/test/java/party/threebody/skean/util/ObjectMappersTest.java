package party.threebody.skean.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ObjectMappersTest {

	public class Car {
		private int p1;
		String p2;
		private String _p3;

		Car(){
			p1=4;
			p2="h";
		}
		public String getLastName3() {
			return _p3;
		}

		public void setLastName(String p3) {
			_p3 = p3;
		}
	}

	@Test
	public void testOM_SNAKE(){
		Car car1=new Car();
		car1.setLastName("aaa");
		for (int i=0;i<30;i++){
			long t1=System.currentTimeMillis();
			Map m1=ObjectMappers.OM_SNAKE_CASE.convertValue(car1, Map.class);
			//System.out.println(System.currentTimeMillis()-t1);
		}
		
		Map m=new HashMap();
		m.put("car1", car1);
			
		
		for (int i=0;i<30;i++){
			m.put("a", i++);
			long t1=System.currentTimeMillis();
			Map m1=ObjectMappers.OM_SNAKE_CASE.convertValue(m, Map.class);
			
			
			System.out.println(Strings.repeat("*", (int)(System.currentTimeMillis()-t1))+1);
			
			
		}
	}
}
