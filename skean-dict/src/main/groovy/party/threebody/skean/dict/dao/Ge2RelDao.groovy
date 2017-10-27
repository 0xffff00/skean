package party.threebody.skean.dict.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.skean.dict.domain.Ge2Rel
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao.JpaCrudDAO

@Repository
class Ge2RelDao implements JpaCrudDAO<Ge2Rel> {
    @Autowired ChainedJdbcTemplate cjt

    @Override
    ChainedJdbcTemplate getChainedJdbcTemplate() {
        return cjt
    }

//    int delete(String key, String attr, Integer vno) {
//        cjt.from(getTable()).by('key', 'attr', 'vno').val(key, attr, vno).delete()
//    }

    int delete(String key, String attr) {get
        cjt.from(getTable()).by('key', 'attr').val(key, attr).delete()
    }

}
