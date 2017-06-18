package party.threebody.s4g.dict

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DictService {

	@Autowired DictDao dictDao

	public List<Noun> listNouns(){
		dictDao.listNouns()
	}

	public Noun getNoun(String word){
		dictDao.getNoun(word)
	}
	
	
}
