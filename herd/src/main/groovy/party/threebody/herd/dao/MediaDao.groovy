package party.threebody.herd.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import party.threebody.herd.domain.Media
import party.threebody.herd.domain.MediaPath
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.mvc.dao.SinglePKCrudDAO

import java.time.LocalDateTime

@Repository
class MediaDao extends SinglePKCrudDAO<Media, String> {

    @Autowired
    ChainedJdbcTemplate cjt
    @Autowired
    NamedParameterJdbcTemplate njt

    @Override
    protected String getTable() {
        'hd_media'
    }

    @Override
    protected Class<Media> getBeanClass() {
        Media.class
    }

    @Override
    protected String getPrimaryKeyColumn() {
        'hash'
    }

    @Override
    protected List<String> getAffectedColumns() {
        ['hash', 'type', 'subtype', 'desc', 'size', 'sync_time']
    }

    List<Media> listAll() {
        def sql='''
SELECT m.*,
       (SELECT p.path FROM hd_media_path p WHERE p.hash=m.hash LIMIT 1) path0Path
FROM hd_media m
'''
        cjt.arg(repoName).list()
    }


    List<Media> listBySyncTime(LocalDateTime syncTime){
        def sql='''
SELECT m.*,
       (SELECT p.path FROM hd_media_path p WHERE p.hash=m.hash LIMIT 1) path0Path
FROM hd_media m
WHERE sync_time=:syncTime
'''
        njt.queryForList(sql,[syncTime:syncTime])
    }

}
