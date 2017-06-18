package tmp.experiment;

import static org.junit.Assert.*

import org.junit.BeforeClass
import org.junit.Test

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper


class LearnJackson2 {

	static ObjectMapper objMapper;

	@BeforeClass
	public static void beforeClass() {
		objMapper = new ObjectMapper();
	}
	@Test
	public void learn1() {
		def s='''{"x1":3}'''
		A a=objMapper.readerFor(A.class).readValue(s)
		println a.x
		println 
		objMapper.writer().writeValueAsString(a)
	}
}
class A{
	int x;
	int getX2(){
		x*x
	}
	void setX1(int x1){
		x=x1;
	}
}

