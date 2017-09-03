package party.threebody.herd.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.herd.domain.ImageInfo
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.mvc.dao.SinglePKCrudDAO

@Repository
class ImageInfoDao extends SinglePKCrudDAO<ImageInfo, String> {

    @Autowired
    ChainedJdbcTemplate cjt

    @Override
    protected String getTable() {
        'hd_repo_image'
    }

    @Override
    protected Class<ImageInfo> getBeanClass() {
        ImageInfo.class
    }

    @Override
    protected String getPrimaryKeyColumn() {
        'hash'
    }

    @Override
    protected List<String> getAffectedColumns() {
        null
    }

    int deleteAll(){
        cjt.sql('DELETE FROM hd_repo_image').execute()
    }
}
