package party.threebody.skean.dict.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.skean.dict.domain.DualRel
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao0.TriplePKCrudDAO

@Repository
class DualRelDao extends TriplePKCrudDAO<DualRel,String,String,Integer> {

	@Autowired ChainedJdbcTemplate cjt

	@Override
	protected String getTable() {
		'dct_rel_sp_dual'
	}

	@Override
	protected Class<DualRel> getEntityClass() {
		DualRel.class
	}

	@Override
	protected List<String> getPrimaryKeyColumns() {
		['key', 'attr', 'vno']
	}

	@Override
	protected List<String> getAffectedColumns() {
		null
	}

}
