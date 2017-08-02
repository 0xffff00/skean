package tmp.experiment;

import static org.junit.Assert.*

import java.util.function.Function

import org.apache.commons.beanutils.BeanUtils

import com.fasterxml.jackson.databind.ObjectMapper

import party.threebody.skean.util.ObjectMappers


class PerfTest_Obj2Map {

	static ObjectMapper objMapper;
	
	Stu buildStu(){
		cnt=0;
		build(0);
	}
	Random random = new Random();
	 int cnt;
	 Stu buildTree(int h){
		if (h<0) return null;
		int x=random.nextInt();
		Stu s1=buildTree(h-1);
		Stu s2=buildTree(h-1);
		return new Stu("stu"+(cnt++),x,["a","b","c"] as String[],[a:4,b:5],[s1,s2] as Set)
	}
	
	Stu[] buildFlat(int len){
		Stu stu0=new Stu("stu00",444,["kkk"] as String[],[a:4,b:5],[] as Set)
		Stu[] res=new Stu[len];
		for (int i=0;i<len;i++){
			int x=random.nextInt();
			res[i]=new Stu("stu"+(i++),x,["a","b","c"] as String[],[a:4,b:5],[stu0] as Set)
		}
		res
	}
	
	Stu[] buildTrees(int len,int h){
		Stu[] res=new Stu[len];
		for (int i=0;i<len;i++){
			res[i]=buildTree(h);
		}
		res
	}
	
	public static void main(String[] args) {
		def z=new PerfTest_Obj2Map();
		Stu stu1=z.buildTree(8);
		Stu[] stus=z.buildTrees(5000,0);
		z.runPerf1(stus);
		z.runPerf2(stus);
		
		Stu s1=z.buildTree(0)
		Map m1=z.convert_flat_jackson(s1)
		println m1
		println z.convert_flat_beanutils(s1)
	}
	
	public void runPerf(Stu[] stus,Function<Stu,Map> func){
		long t1=System.currentTimeMillis();
		for (int i=0;i<stus.length;i++){
			func.apply(stus[i])
		}
		long t2=System.currentTimeMillis();
		println func.getMetaClass(),t2-t1
	}
	
	public void runPerf1(Stu[] stus){
		long t1=System.currentTimeMillis();
		for (int i=0;i<stus.length;i++){
			convert_flat_jackson(stus[i])
		}
		long t2=System.currentTimeMillis();
		println "jack"+(t2-t1)
	}
	public void runPerf2(Stu[] stus){
		long t1=System.currentTimeMillis();
		for (int i=0;i<stus.length;i++){
			convert_flat_beanutils(stus[i])
		}
		long t2=System.currentTimeMillis();
		println t2-t1
	}
	
	Map convert_tree_jackson(Stu stu){
		ObjectMappers.OM_SNAKE_CASE.convertValue(stu, Map.class)
	}
	
	Map convert_tree_beanutils(Stu stu){
		ObjectMappers.OM_SNAKE_CASE.convertValue(stu, Map.class)
	}
	
	Map convert_flat_jackson(Stu stu){
		ObjectMappers.OM_SNAKE_CASE.convertValue(stu, Map.class)
	}
	Map convert_flat_beanutils(Stu stu){
		BeanUtils.describe(stu)
	}
	
}
