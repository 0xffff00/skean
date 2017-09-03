package party.threebody.herd.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.herd.domain.RepoFile
import party.threebody.herd.domain.RepoFilePath
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.mvc.dao.DualPKCrudDAO
import party.threebody.skean.mvc.dao.SinglePKCrudDAO

@Repository
class RepoFilePathDao extends DualPKCrudDAO<RepoFilePath, String,String> {

    @Autowired
    ChainedJdbcTemplate cjt

    @Override
    protected String getTable() {
        'hd_repo_file_path'
    }

    @Override
    protected Class<RepoFilePath> getBeanClass() {
        RepoFilePath.class
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
        cjt.sql("SELECT path FROM hd_repo_file_path WHERE hash=?").arg(hash).listOfSingleColumn(String.class)
    }

    int createOne(String hash,String path,String type,String repoName){
        cjt.sql("INSERT INTO hd_repo_file_path(hash,path,type,repo_name) VALUES(?,?,?,?)").arg(hash,path,type,repoName).execute()
    }

    int deleteAll(){
        cjt.sql("DELETE FROM hd_repo_file_path").execute()
    }

    List<RepoFilePathGroupByHash> groupByHash(){
        def sql="SELECT `hash`,COUNT(path) cnt,MIN(path) path0 FROM hd_repo_file_path GROUP BY `hash`"
        cjt.sql(sql).list(RepoFilePathGroupByHash.class)
    }

    static class RepoFilePathGroupByHash{
        String hash
        int count
        String path0
    }
}

