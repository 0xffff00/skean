package party.threebody.herd.dao

import org.springframework.stereotype.Repository
import party.threebody.herd.domain.Repo
import party.threebody.skean.mvc.dao.SinglePKCrudDAO

@Repository
class RepoDao extends SinglePKCrudDAO<Repo, String> {

    @Override
    protected String getTable() {
        'hd_repo'
    }

    @Override
    protected Class<Repo> getBeanClass() {
        Repo.class
    }

    @Override
    protected String getPrimaryKeyColumn() {
        'name'
    }

    @Override
    protected List<String> getAffectedColumns() {
        return null
    }

    List<Repo> listByState(String state) {
        fromTable().by('state').val(state).list(Repo.class)
    }
}
