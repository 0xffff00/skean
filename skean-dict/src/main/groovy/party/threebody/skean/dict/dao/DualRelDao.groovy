package party.threebody.skean.dict.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.skean.dict.domain.DualRel
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.testapp.mvc.dao.TriplePKCrudDAO

@Repository
class DualRelDao extends TriplePKCrudDAO<DualRel,String,String,Integer> {

	@Autowired ChainedJdbcTemplate cjt

	@Override
	protected String getTable() {
		'dct_rel_sp_dual'
	}

	@Override
	protected Class<DualRel> getBeanClass() {
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
