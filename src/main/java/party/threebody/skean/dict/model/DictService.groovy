package party.threebody.skean.dict.model

import org.apache.commons.collections4.MultiValuedMap
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.SingleColumnRowMapper
import org.springframework.stereotype.Service

import groovy.transform.ToString
import party.threebody.skean.jdbc.ChainedJdbcTemplate

@Service
class DictService {
	@Autowired DictDao dictDao

	String getFormal(String alias){
		dictDao.getAliasRoot(alias) ?: alias
	}

	WordDO getWord(String text){
		WordDO w=new WordDO()
		w.setText(text)
		w.setInstanceRelations(listInstanceRelations(text))
		w.setDefinitionRelations(listDefinitionRelations(text))
		w.setSubsetRelations(listSubsetRelations(text))
		w.setSupersetRelations(listSupersetRelations(text))
		w.setChildRelations(listChildren(text))
		w.setParentRelations(listParentRelations(text))
		w.setAttributeRelations(listAttributeRelations(text))
		w.setReferenceRelations(listReferenceRelations(text))
		w
	}

	void t2(String text){
		Word w=new Word()
		w.setText(text)
		List<String> subsets=listSubsets(text)
		TreeNode<String> subsetTree=new TreeNode(text,subsets)
	}
	void dfs(TreeNode node,String text){
		node=new TreeNode(text)
		List<String> subsets=listSubsets(text)
		
		
	}
	
	List<String> listSubsets(String text){
		listSubsetRelations(text).collect{it.key}
	}
	List<DualRelation> listSupersets(String text){
		listSupersetRelations(text).collect{it.val}
	}
	
	
	List<DualRelation> listInstanceRelations(String text){
		dictDao.listDualRelationsByKeyAndAttr(text, DualType.INST.toString())
	}
	List<DualRelation> listDefinitionRelations(String text){
		dictDao.listDualRelationsByValAndAttr(text, DualType.INST.toString())
	}
	List<DualRelation> listSubsetRelations(String text){
		dictDao.listDualRelationsByKeyAndAttr(text, DualType.SUBS.toString())
	}
	List<DualRelation> listSupersetRelations(String text){
		dictDao.listDualRelationsByValAndAttr(text, DualType.SUBS.toString())
	}
	List<DualRelation> listChildren(String text){
		dictDao.listDualRelationsByKeyAndAttr(text, DualType.GECH.toString())
	}
	List<DualRelation> listParentRelations(String text){
		dictDao.listDualRelationsByValAndAttr(text, DualType.GECH.toString())
	}

	List<GenericRelation> listAttributeRelations(String text){
		dictDao.listGenericRelationsByKey(text)
	}
	List<GenericRelation> listReferenceRelations(String text){
		dictDao.listGenericRelationsByVal(text)
	}
}
