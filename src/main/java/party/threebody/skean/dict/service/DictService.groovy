package party.threebody.skean.dict.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import party.threebody.skean.dict.dao.DictDao
import party.threebody.skean.dict.model.DualRelation
import party.threebody.skean.dict.model.DualType
import party.threebody.skean.dict.model.GenericRelation
import party.threebody.skean.dict.model.Word

@Service
class DictService {
	@Autowired DictDao dictDao

	String getFormal(String alias){
		dictDao.getAliasRoot(alias) ?: alias
	}

	Word getWord(String text){
		Word w=new Word()
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
