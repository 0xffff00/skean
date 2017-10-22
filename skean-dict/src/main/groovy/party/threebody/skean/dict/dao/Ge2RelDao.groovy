package party.threebody.skean.dict.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.skean.dict.domain.Ge2Rel
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao0.TriplePKCrudDAO

@Repository
class Ge2RelDao extends TriplePKCrudDAO<Ge2Rel,String,String,Integer> {

	@Autowired ChainedJdbcTemplate cjt

	@Override
	protected String getTable() {
		'dct_rel_ge_dat2'
	}

	@Override
	protected Class<Ge2Rel> getEntityClass() {
		Ge2Rel.class
	}

	@Override
	protected List<String> getPrimaryKeyColumns() {
		['key', 'attr', 'vno']
	}

	@Override
	protected List<String> getAffectedColumns() {
		['key','attr','vno','adv','attrx','pred','time1','time2','valstr','valnum','valmu']
	}
	int delete(String key, String attr, Integer vno){
		cjt.from(getTable()).by('key','attr','vno').val(key,attr,vno).delete()
	}
	int delete(String key, String attr){
		cjt.from(getTable()).by('key','attr').val(key,attr).delete()
	}
}
