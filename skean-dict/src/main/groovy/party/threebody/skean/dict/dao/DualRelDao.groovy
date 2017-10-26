package party.threebody.skean.dict.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.skean.dict.domain.DualRel
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao.TriplePKsJpaCrudDAO

@Repository
class DualRelDao extends TriplePKsJpaCrudDAO<DualRel, String, String, Integer> {

    @Autowired
    ChainedJdbcTemplate cjt

    @Override
    ChainedJdbcTemplate getChainedJdbcTemplate() {
        return cjt
    }
}
