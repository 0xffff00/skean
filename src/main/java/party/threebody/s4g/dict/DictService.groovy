package party.threebody.s4g.dict

import org.apache.commons.collections4.MultiValuedMap
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.SingleColumnRowMapper
import org.springframework.stereotype.Service

import party.threebody.skean.jdbc.ChainedJdbcTemplate

@Service
class DictService {

	@Autowired ChainedJdbcTemplate q
	
	List<Noun> listNouns(){
		q.from("dct_noun").list();
	}
	
	Noun getNoun(String word0){
		q.from('dct_noun').by('word','qual').val(word0,'').single()
	}
	
	Noun getNoun(String word0,String qual){
		q.from('dct_noun').by('word','qual').val(word0,qual).single()
	}
	
	List<String> listFirstNodeOfSubTreeByPreOrder(String rootNoun){
		
	}
	
	List<Noun> listInstances(String noun){
		
	}
	
	List<String> listAliases(String word){
		q.from('dct_noun_rel').by('sbj','obj').val(word,'ALIAS').list()
		.collect{it['obj']};
	}
	
	String getPrimaryAlias(String word){
		String a=q.sql('SELECT obj FROM dct_rel WHERE sbj=? AND obj=?').arg(word,'ALIAS').firstCell();
		a?a:word
	}
	List<Map> listRelatedAliasObject(String word){
		listRelated(word,'ALIAS')
	}
	
	List<Map> listRelatedInstanceObject(String word){
		listRelated(word,'ONE')
	}
	
	List<Map> listRelatedSubject(String word){
		def word0=getPrimaryAlias(word)
		q.sql('SELECT * FROM dct_rel WHERE oba=? AND obj!=?').arg(word0,'ALIAS').list()
		.collect{
			[word:it.sbj,relType:it.obj,seq:it.seq,adv:it.adv,timeFrom:it.ti1,timeTo:it.ti2]
		}
	}
	
	List<String> listAllInRel(){
		q.sql('''
      (SELECT DISTINCT sbj w FROM dct_rel) 
UNION (SELECT DISTINCT oba w FROM dct_rel) 
UNION (SELECT DISTINCT obj w FROM dct_rel)
	''').list(new SingleColumnRowMapper<String>())
	}
	
	TNode treeParts(String rootWord){
		def rels=q.sql('SELECT * FROM dct_rel WHERE obj=? ORDER BY oba,sbj,seq').arg('PART').list(Rel.class)
		def nounsInRel=listAllInRel()
		TTreeMaker treeMaker=new TTreeMaker(rels:rels,nounsInRel:nounsInRel)
		treeMaker.make()
	}
	
	List<Map> listRelated(String word,String relationType){
		def alias0=getPrimaryAlias(word)
		q.sql('SELECT sbj,adv FROM dct_rel WHERE oba=? AND obj=?').arg(alias0,relationType).list()
		.collect{
			[word:it.sbj,seq:it.seq,adv:it.adv,timeFrom:it.ti1,timeTo:it.ti2]
		}
	}
	Map<String,Integer> getRelated(String word){
		def word0=getPrimaryAlias(word)
		
	}
	Map<String,Integer> getRelatedNested(String word){
		def result=[:]
		def reached=[] as Set
		_getRelated(result,reached,word,0)
		result
		
	}
	private void _getRelated(Map result,Set reached,String word,int h){
		if (reached.contains(word)){
			return;
		}
		//println word+','+h
		reached.add(word)
		def list=q.sql('''
SELECT oba ro,obj,adv FROM dct_rel WHERE sbj=?
UNION 
SELECT sbj ro,obj,adv FROM dct_rel WHERE oba=?
''')
		.arg(word,word).list()
		for (Map row:list){
			int w=1;
			if (row['obj']=='ALIAS') w=0;
			Integer x=result.get(row['ro'])
			if (x==null || x>h+w){
				result.put(row['ro'],h+w);
			}
			
		}
		for (Map row:list){
			_getRelated(result,reached,row['ro'],h+1)
		}
	}
	 
}
class NounNode{
	String word
	List<String> dads
	List<String> sons
	
	NounNode(String word){
		this.word=word
		dads=new ArrayList<>()
		sons=new ArrayList<>()
	}
}
class TTreeMaker{
	List<Rel> rels
	List<String> nounsInRel
	
	MultiValuedMap<String,String> dadsMap=new ArrayListValuedHashMap<>()
	MultiValuedMap<String,String> sonsMap=new ArrayListValuedHashMap<>()
	
	TNode make(){
		
		for (Rel rel:rels){
			dadsMap.put(rel.sbj, rel.oba)
			sonsMap.put(rel.oba, rel.sbj)
		}
		def ancestors=nounsInRel.findAll{!dadsMap.containsKey(it)}
		def ancNodes=[] 
		for (String anc:ancestors){
			ancNodes.add(_buildTNode(anc))
		}
		new TNode(word:null,sons:ancNodes)
	}
	
	TNode _buildTNode(String w){
		TNode n=new TNode()
		n.word=w
		def sons=sonsMap.get(w)
		n.sons=new ArrayList(sons.size())
		for (String son:sons){
			n.sons.add(_buildTNode(son))
		}
		n
	}
}
class TNode{
	String word
	List<TNode> sons
	
}