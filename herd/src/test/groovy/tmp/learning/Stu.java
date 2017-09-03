package tmp.learning;

import java.util.Map;
import java.util.Set;

public class Stu{
	private long _md5;
	private String name;
	private int  age;
	
	private String[] arr;
	private Map m1;
	private Set<Stu> friends;
	
	
	public Stu(String name, int age, String[] arr, Map m1, Set<Stu> friends) {
		super();
		this.name = name;
		this.age = age;
		this.arr = arr;
		this.m1 = m1;
		this.friends = friends;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public int getAge2(){
		return -age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	public String[] getArr() {
		return arr;
	}
	public void setArr(String[] arr) {
		this.arr = arr;
	}
	public Map getM1() {
		return m1;
	}
	public void setM1(Map m1) {
		this.m1 = m1;
	}
	public Set<Stu> getFriends() {
		return friends;
	}
	public void setFriends(Set<Stu> friends) {
		this.friends = friends;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stu other = (Stu) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
	
	
	
	
}