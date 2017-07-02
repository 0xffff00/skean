package party.threebody.s4g.dict

import java.util.List

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import party.threebody.skean.jdbc.ChainedJdbcTemplate

@Service
class DictService {

	@Autowired ChainedJdbcTemplate q
	
	public List<Noun> listNouns(){
		q.from("dct_noun").list();
	}
	
	public Noun getNoun(String word){
		q.from('dct_noun').by('word','qual').val(word,'').single()
	}
	
	public Noun getNoun(String word,String qual){
		q.from('dct_noun').by('word','qual').val(word,qual).single()
	}
	
	
}
