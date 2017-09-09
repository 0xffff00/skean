package party.threebody.herd.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import party.threebody.herd.domain.ImageMedia
import party.threebody.herd.domain.Media
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.mvc.dao.SinglePKCrudDAO

@Repository
class ImageMediaDao extends SinglePKCrudDAO<ImageMedia, String> {

    @Autowired
    ChainedJdbcTemplate cjt
    @Autowired
    NamedParameterJdbcTemplate njt
    @Override
    protected String getTable() {
        'hd_media_image'
    }

    @Override
    protected Class<ImageMedia> getBeanClass() {
        ImageMedia.class
    }

    @Override
    protected String getPrimaryKeyColumn() {
        'hash'
    }

    @Override
    protected List<String> getAffectedColumns() {
        null
    }

    List<ImageMedia> listByHashs(Collection<String> hashs){
        fromTable().by('hash').val([hashs]).list(ImageMedia.class)
    }


}
