package party.threebody.skean.dict.dao

import java.util.List

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import party.threebody.skean.dict.model.AliasRel
import party.threebody.skean.dict.model.DualRel
import party.threebody.skean.dict.model.Ge1Rel
import party.threebody.skean.dict.model.Ge2Rel
import party.threebody.skean.dict.model.Word
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.mvc.generic.AbstractCrudDAO

@Repository
class WordDao extends AbstractCrudDAO<Word,String> {

	@Autowired ChainedJdbcTemplate cjt

	List<String> listTempWords(){
		def sql=
		'''		
SELECT a.w FROM (
		        SELECT `key` w FROM dct_rel_ge_dat1
		UNION	SELECT `val` w FROM dct_rel_ge_dat1
		UNION	SELECT `key` w FROM dct_rel_ge_dat2
		UNION	SELECT `key` w FROM dct_rel_sp_dual
		UNION	SELECT `val` w FROM dct_rel_sp_dual
		UNION	SELECT `key` w FROM dct_rel_sp_alias
		UNION	SELECT `val` w FROM dct_rel_sp_alias
		
) a WHERE a.w NOT IN (SELECT `text`  FROM dct_word)
		'''
		cjt.sql(sql).listOfSingleColumn(String.class)
	}

	List<AliasRel> listAliasRels(){
		cjt.from("dct_rel_sp_alias").list(AliasRel.class)
	}

	int createAliasRel(AliasRel rel){
		cjt.from("dct_rel_sp_alias").affect('key','attr','lang','vno','val','adv').val(rel).insert()
	}

	int updateAliasRelByKV(AliasRel rel){
		cjt.from("dct_rel_sp_alias")
				.affect('key','attr','lang','vno','val','adv').by('key','val').val(rel).update()
	}

	int deleteAliasRelsByKV(String key,String val){
		cjt.from("dct_rel_sp_alias").by('key','val').val(key,val).delete()
	}


	List<DualRel> listDualRels(){
		cjt.from("dct_rel_sp_dual").list(DualRel.class)
	}


	List<Ge1Rel> listGe1Rels(){
		cjt.from("dct_rel_ge_dat1").list(Ge1Rel.class)
	}

	List<Ge2Rel> listGe2Rels(){
		cjt.from("dct_rel_ge_dat2").list(Ge2Rel.class)
	}

	@Override
	protected String getTable() {
		'dct_word'
	}

	@Override
	protected Class<Word> getBeanClass() {
		Word.class
	}

	@Override
	protected List<String> getPrimaryKeyColumns() {
		['text']
	}

	@Override
	protected List<String> getAffectedColumns() {
		['text','desc']
	}
	
	
}
