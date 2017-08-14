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
class Ge2RelDao extends AbstractCrudDAO<Ge2Rel,String[]> {

	@Autowired ChainedJdbcTemplate cjt

	@Override
	protected String getTable() {
		'dct_rel_ge_dat2'
	}

	@Override
	protected Class<Ge2Rel> getBeanClass() {
		Ge2Rel.class
	}

	@Override
	protected List<String> getPrimaryKeyColumns() {
		['key', 'attr', 'vno']
	}

	@Override
	protected List<String> getAffectedColumns() {
		null
	}
	int delete( String key, String attr, Integer vno){
		cjt.from(getTable()).by('key','attr','vno').val(key,attr,vno).delete()
	}
}
