package party.threebody.skean.dict.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import party.threebody.skean.dict.model.DualRelation
import party.threebody.skean.dict.model.GenericNonRefRelation
import party.threebody.skean.dict.model.GenericRelation
import party.threebody.skean.jdbc.ChainedJdbcTemplate

@Repository
class DictDao {

	@Autowired ChainedJdbcTemplate cjt

	String getAliasRoot(String alias){
		cjt.from("dct_rel_sp_alias").select('key').by("val").val(alias).limit(1).firstCell()
	}



	List<DualRelation> listDualRelationsByKey(String text){
		cjt.from("dct_rel_sp_dual").by("key").val(text).list(DualRelation.class)
	}
	List<DualRelation> listDualRelationsByKeyAndAttr(String text,String attr){
		cjt.from("dct_rel_sp_dual").by("key","attr").val(text,attr).list(DualRelation.class)
	}
	
	List<DualRelation> listDualRelationsByVal(String text){
		cjt.from("dct_rel_sp_dual").by("val").val(text).list(DualRelation.class)
	}
	List<DualRelation> listDualRelationsByValAndAttr(String text,String attr){
		cjt.from("dct_rel_sp_dual").by("val","attr").val(text,attr).list(DualRelation.class)
	}

		
	List<GenericRelation> listGenericRelationsByKey(String text){
		cjt.sql("SELECT * FROM dct_rel_ge_dat1 WHERE `key`=?").arg(text).list(GenericRelation.class)
	}
	
	List<GenericNonRefRelation> listGenericNonRefRelationsByKey(String text){
		cjt.sql("SELECT * FROM dct_rel_ge_dat2 WHERE `key`=?").arg(text).list(GenericNonRefRelation.class)
	}

	List<GenericRelation> listGenericRelationsByVal(String text){
		cjt.sql("SELECT * FROM dct_rel_ge_dat1 WHERE `val`=?").arg(text).list(GenericRelation.class)
	}
	
	List<GenericRelation> listGenericRelationsByKeyAndAttr2(String text,String attr,String attrx){
		cjt.sql("SELECT * FROM dct_rel_ge_dat1 WHERE `key`=? AND `attr`=? AND attrx=?")
		.arg(text,attr,attrx).list(GenericRelation.class)
	}
}
