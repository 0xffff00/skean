package party.threebody.skean.dict.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.skean.dict.domain.Ge1Rel
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao.TriplePKsJpaCrudDAO

@Repository
class Ge1RelDao extends TriplePKsJpaCrudDAO<Ge1Rel, String, String, Integer> {

    @Autowired
    ChainedJdbcTemplate cjt

    @Override
    ChainedJdbcTemplate getChainedJdbcTemplate() {
        return cjt
    }

//    int delete(String key, String attr, Integer vno) {
//        cjt.from(getTable()).by('key', 'attr', 'vno').val(key, attr, vno).delete()
//    }

    int delete(String key, String attr) {
        cjt.from(getTable()).by('key', 'attr').val(key, attr).delete()
    }

    int maxVno(String key, String attr) {
        def sql='SELECT max(vno) FROM dct_rel_ge_dat1 WHERE `key`=? AND attr=?'
        (int) cjt.sql(sql).arg(key, attr).firstCell() ?: 0
    }
}
