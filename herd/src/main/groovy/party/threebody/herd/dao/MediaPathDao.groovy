package party.threebody.herd.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import party.threebody.herd.domain.MediaPath
import party.threebody.skean.core.query.BasicCriterion
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.jdbc.util.CriteriaUtils
import party.threebody.skean.mvc.dao.DualPKCrudDAO

import java.time.LocalDateTime

@Repository
class MediaPathDao extends DualPKCrudDAO<MediaPath, String, String> {

    @Autowired
    ChainedJdbcTemplate cjt


    @Override
    protected String getTable() {
        'hd_media_path'
    }

    @Override
    protected Class<MediaPath> getBeanClass() {
        MediaPath.class
    }

    @Override
    protected List<String> getPrimaryKeyColumns() {
        ['hash','path']
    }

    @Override
    protected List<String> getAffectedColumns() {
        return null
    }

    List<String> listPathsByHash(String hash){
        cjt.sql("SELECT path FROM hd_media_path WHERE hash=?").arg(hash).listOfSingleColumn(String.class)
    }

    int createOne(String hash,String path,String type,String repoName){
        cjt.sql("INSERT INTO hd_media_path(hash,path,type,repo_name) VALUES(?,?,?,?)").arg(hash,path,type,repoName).execute()
    }

    List<MediaPath> listByRepoNames(List<String> repoNames){
        fromTable().by('repo_name').val([repoNames]).list(MediaPath.class)
    }


    List<MediaPath> listBySyncTime(LocalDateTime syncTime){
        fromTable().by('sync_time').val(syncTime).list(MediaPath.class)
    }

    int deleteByRepoNames(List<String> repoNames){
        def repoName_IN=CriteriaUtils.buildClauseOfInStrs('repo_name',repoNames)
        def sql="DELETE FROM hd_media_path WHERE $repoName_IN"
        cjt.sql(sql).execute()
    }


}
