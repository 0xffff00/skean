package party.threebody.herd.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.herd.domain.RepoFile
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.mvc.generic.AbstractCrudDAO

@Repository
class RepoFileDao extends AbstractCrudDAO<RepoFile, String> {

    @Autowired
    ChainedJdbcTemplate cjt

    @Override
    protected String getTable() {
        'hd_repo_file'
    }

    @Override
    protected Class<RepoFile> getBeanClass() {
        RepoFile.class
    }

    @Override
    protected List<String> getPrimaryKeyColumns() {
        ['hash']
    }

    @Override
    protected List<String> getAffectedColumns() {
        ['hash', 'repo_name', 'abs_path', 'type', 'subtype', 'desc', 'size', 'sync_time']
    }

    @Override
    protected Map<String, Object> convertBeanToMap(RepoFile bean) {
        return ['hash'    : bean.hash, 'repo_name': bean.repoName,
                'abs_path': bean.absPath, 'type': bean.type, 'subtype': bean.type,
                'desc'    : bean.desc, 'size': bean.size, 'sync_time': bean.syncTime]

    }
}
