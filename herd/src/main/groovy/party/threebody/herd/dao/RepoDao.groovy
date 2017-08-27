package party.threebody.herd.dao

import org.springframework.stereotype.Repository
import party.threebody.herd.domain.Repo
import party.threebody.skean.mvc.generic.AbstractCrudDAO

@Repository
class RepoDao extends AbstractCrudDAO<Repo,String> {

    @Override
    protected String getTable() {
        'hd_repo'
    }

    @Override
    protected Class<Repo> getBeanClass() {
        Repo.class
    }

    @Override
    protected List<String> getPrimaryKeyColumns() {
        'name'
    }

    @Override
    protected List<String> getAffectedColumns() {
        return null
    }
}
