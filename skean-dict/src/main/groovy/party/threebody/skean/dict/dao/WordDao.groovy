package party.threebody.skean.dict.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.skean.dict.domain.AliasRel
import party.threebody.skean.dict.domain.Word
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao.SinglePKCrudDAO

@Repository
class WordDao extends SinglePKCrudDAO<Word,String> {

	@Autowired ChainedJdbcTemplate cjt

	List<String> listTemporaryTexts(){
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
		cjt.sql(sql).listOfSingleCol(String.class)
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

	@Override
	protected String getTable() {
		'dct_word'
	}

	@Override
	protected Class<Word> getEntityClass() {
		Word.class
	}

	@Override
	protected String getPrimaryKeyColumn() {
		'text'
	}

	@Override
	protected List<String> getAffectedColumns() {
		['text','desc']
	}
	
	
}
