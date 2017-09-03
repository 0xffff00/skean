package tmp.learning;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Set;

public class LearnBeanUtils {

	public static void main(String[] args) throws Exception {
		Set<Stu> fri=new HashSet<Stu>();
		fri.add(new Stu("Mary",19,null,null,null));
		fri.add(new Stu("Lily",20,null,null,null));
		Stu stu=new Stu("Tom",22,new String[]{"a","b"},null,fri);
		System.out.println("==============BeanUtils.getProperty===========");
		System.out.println(BeanUtils.getProperty(stu, "name"));
		System.out.println(BeanUtils.getProperty(stu, "age"));
		System.out.println(BeanUtils.getProperty(stu, "arr"));
		System.out.println(BeanUtils.getProperty(stu, "m1"));
		System.out.println("==============getProperty===========");
		System.out.println(PropertyUtils.getProperty(stu, "age"));
		System.out.println(PropertyUtils.getProperty(stu, "age").getClass());
		System.out.println(PropertyUtils.getProperty(stu, "arr"));
		System.out.println(PropertyUtils.getProperty(stu, "arr").getClass());
		System.out.println(PropertyUtils.getProperty(stu, "friends"));
		System.out.println(PropertyUtils.getProperty(stu, "friends").getClass());
		
		System.out.println("==============getSimpleProperty===========");
		System.out.println(PropertyUtils.getSimpleProperty(stu, "age"));
		System.out.println(PropertyUtils.getSimpleProperty(stu, "age").getClass());
		System.out.println(PropertyUtils.getSimpleProperty(stu, "arr"));
		System.out.println(PropertyUtils.getSimpleProperty(stu, "arr").getClass());
		System.out.println(PropertyUtils.getSimpleProperty(stu, "friends"));
		System.out.println(PropertyUtils.getSimpleProperty(stu, "friends").getClass());
		
		System.out.println("==============reflectionToString===========");
		System.out.println(ToStringBuilder.reflectionToString(stu));
		System.out.println(BeanUtils.describe(stu));
	}

}
