package party.threebody.skean.dict.model

import java.util.List

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import party.threebody.skean.jdbc.ChainedJdbcTemplate

@Repository
class DictDao {

	@Autowired ChainedJdbcTemplate cjt

	String getAliasRoot(String alias){
		cjt.from("dct_rel_sp_alias").select('key').by("val").val(alias).limit(1).firstCell()
	}



	List<AliasRelation> listDualRelationsByKey(String text){
		cjt.from("dct_rel_sp_dual").by("key").val(text).list(DualRelation.class)
	}
	List<AliasRelation> listDualRelationsByKeyAndAttr(String text,String attr){
		cjt.from("dct_rel_sp_dual").by("key","attr").val(text,attr).list(DualRelation.class)
	}
	
	List<AliasRelation> listDualRelationsByVal(String text){
		cjt.from("dct_rel_sp_dual").by("val").val(text).list(DualRelation.class)
	}
	List<AliasRelation> listDualRelationsByValAndAttr(String text,String attr){
		cjt.from("dct_rel_sp_dual").by("val","attr").val(text,attr).list(DualRelation.class)
	}

	List<AliasRelation> listGenericRelationsByKey(String text){
		cjt.sql("SELECT * FROM dct_rel_ge_dat1 WHERE `key`=?").arg(text).list(GenericRelation.class)
	}
	

	List<AliasRelation> listGenericRelationsByVal(String text){
		cjt.sql("SELECT * FROM dct_rel_ge_dat1 WHERE `val`=?").arg(text).list(GenericRelation.class)
	}
	
	List<AliasRelation> listGenericRelationsByKeyAndAttr2(String text,String attr,String attrx){
		cjt.sql("SELECT * FROM dct_rel_ge_dat1 WHERE `key`=? AND `attr`=? AND attrx=?")
		.arg(text,attr,attrx).list(GenericRelation.class)
	}
}
