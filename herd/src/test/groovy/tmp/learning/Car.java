package tmp.learning;

import java.util.Date;
import java.util.List;
import java.util.Map;

class Car {
	private int x;
	private Integer x1;
	String s1;
	public String s2;

	static String s3 = "Abc";
	String[] arr;
	List<Date> dates;
	Map<String,String> m1;
	public Car(int x, Integer x1, String s1, String s2) {
		super();
		this.x = x;
		this.x1 = x1;
		this.s1 = s1;
		this.s2 = s2;
	}

	
	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	Integer getX1() {
		return x1;
	}

	public void setX1(Integer x1) {
		this.x1 = x1;
	}

	public String getS1() {
		return s1;
	}

	public void setS1(String s1) {
		this.s1 = s1;
	}

	public static String getS3() {
		return s3;
	}

	public static void setS3(String s3) {
		Car.s3 = s3;
	}

	public int getX3(){
		return x*188;
	}


	public String getS2() {
		return s2;
	}


	public void setS2(String s2) {
		this.s2 = s2;
	}


	public String[] getArr() {
		return arr;
	}


	public void setArr(String[] arr) {
		this.arr = arr;
	}


	public List<Date> getDates() {
		return dates;
	}
	
	


	public Map<String, String> getM1() {
		return m1;
	}


	public void setM1(Map<String, String> m1) {
		this.m1 = m1;
	}


	public void setDates(List<Date> dates) {
		this.dates = dates;
	}
	
	
}
