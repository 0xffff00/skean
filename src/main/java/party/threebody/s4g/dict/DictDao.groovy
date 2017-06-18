package party.threebody.s4g.dict

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import party.threebody.skean.jdbc.ChainedJdbcTemplate

@Repository
class DictDao {

	@Autowired ChainedJdbcTemplate q;
	
	public List<Noun> listNouns(){
		q.from("dct_noun").list();
	}
	
	public Noun getNoun(String word){
		q.from('dct_noun').by('word').val(word).first()
	}
	
	
	
}
