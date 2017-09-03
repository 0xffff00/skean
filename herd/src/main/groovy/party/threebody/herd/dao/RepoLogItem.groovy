package party.threebody.herd.dao

import org.springframework.stereotype.Repository
import party.threebody.herd.domain.RepoLogItem
import party.threebody.skean.mvc.dao.DualPKCrudDAO

import java.time.LocalDateTime

@Repository
class RepoLogItemDao extends DualPKCrudDAO<RepoLogItem, LocalDateTime, String> {

    @Override
    protected String getTable() {
        'hd_repo_log'
    }

    @Override
    protected Class<RepoLogItem> getBeanClass() {
        RepoLogItem.class
    }

    @Override
    protected List<String> getPrimaryKeyColumns() {
        ['action_time', 'file_abs_path']
    }

    @Override
    protected List<String> getAffectedColumns() {
        null
    }
}
