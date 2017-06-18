package party.threebody.skean.jdbc;

import static org.junit.Assert.*

import org.junit.BeforeClass
import org.junit.Test

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

import party.threebody.skean.core.query.BasicCriterion
import party.threebody.skean.jdbc.phrase.ClauseAndArgs
import party.threebody.skean.jdbc.phrase.ClausesAndArgs
import party.threebody.skean.jdbc.phrase.CriteriaUtils

class TestPhrase {

	static ObjectMapper objMapper;
	def s2="""[
{"name":"a","val":3},
{"name":"a1","val":null},
{"name":"a1","opt":"!=","val":null},
{"name":"a2","opt":">","val":"111"},
{"name":"b0","opt":"in","val":null},
{"name":"bx","opt":"in","val":""},
{"name":"b1","opt":"in","val":[]},
{"name":"b2","opt":"in","val":[null]},
{"name":"b3","opt":"in","val":["h1","h2","h3",6.5]},
{"name":"a4","opt":"<","val":"22d","type":"str"},
{"name":"f_","val":"sdsd","fake":1}
]"""
	
	@BeforeClass
	public static void beforeClass(){
		objMapper=new ObjectMapper();
		objMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
	}

	
	@Test
	public void learn1() {
		def s1="""{"name":"b","opt":">","val":"111"}"""
		println objMapper.readerFor(BasicCriterion.class).readValue(s1)
		

		println objMapper.readValue(s2,Object.class)
		println objMapper.readValue(s2,List.class)
		//objMapper.readValue(s2,String[].class)
		//objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
		BasicCriterion[] x2= objMapper.readValue(s2,BasicCriterion[].class)
		println x2
//		println x2[2]['val'].class
//		println x2[2]['val'][2].class
//		println x2[2]['val'][3].class
	}
	
	@Test
	public void testCriteriaUtils(){
		BasicCriterion[] crits= objMapper.readValue(s2,BasicCriterion[].class)
		ClauseAndArgs caps=CriteriaUtils.toClauseAndArgs(crits[1]);
		println caps.clause
		println caps.args
		ClausesAndArgs csaps=CriteriaUtils.toClausesAndArgs(crits);
		println csaps.clauses
		println csaps.args
		
	}

}

