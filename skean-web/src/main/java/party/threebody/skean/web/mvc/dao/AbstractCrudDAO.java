package party.threebody.skean.web.mvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import party.threebody.skean.data.query.QueryParamsSuite;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.jdbc.phrase.AffectPhrase;
import party.threebody.skean.jdbc.phrase.ByPhrase;
import party.threebody.skean.jdbc.phrase.FromPhrase;
import party.threebody.skean.jdbc.util.JavaBeans;
import party.threebody.skean.lang.StringCases;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @param <T> type of the entity bean
 * @author hzk
 * @since 2017-09-02
 */
public abstract class AbstractCrudDAO<T> {
    @Autowired
    ChainedJdbcTemplate cjt;

    protected abstract String getTable();

    protected abstract Class<T> getBeanClass();

    /**
     * @return names of columns which are exact primary keys
     */
    protected abstract List<String> getPrimaryKeyColumns();

    /**
     * if return null, build actual AffectedColumns by properties of the bean
     *
     * @return names of columns which should be affected (insert or update)
     */
    protected abstract List<String> getAffectedColumns();

    protected FromPhrase fromTable() {
        return cjt.from(getTable());
    }

    protected ByPhrase fromTableByPkCols() {
        return cjt.from(getTable()).by(getPrimaryKeyColumns());
    }

    protected AffectPhrase fromTableAffectCols() {
        return cjt.from(getTable()).affect(getAffectedColumns());
    }

    protected Map<String, Object> convertBeanToMap(T bean) {
        return JavaBeans.convertBeanToSimpleMap(bean, StringCases::camelToSnake);
    }

    public int create(T entity) {
        Map<String, Object> propsMap = convertBeanToMap(entity);
        return fromTableAffectCols().val(propsMap).insert();
    }

    public T createAndGet(T entity) {
        Map<String, Object> propsMap = convertBeanToMap(entity);
        fromTableAffectCols().val(propsMap).insert();
        return fromTable().by(getPrimaryKeyColumns()).val(propsMap).limit(1).first(getBeanClass());
    }

    public List<T> readList(QueryParamsSuite qps) {
        return fromTable().suite(qps).list(getBeanClass());
    }

    public int readCount(QueryParamsSuite qps) {
        return fromTable().suite(qps).count();
    }

    protected T readOne(Object[] pk) {
        return fromTableByPkCols().valArr(pk).limit(1).first(getBeanClass());
    }

    public T readOneByExample(T example) {
        return fromTableByPkCols().valObj(example).limit(1).first(getBeanClass());
    }

    protected int update(T entity, Object[] pkArr) {
        Map<String, Object> propsMap = convertBeanToMap(entity);
        return fromTableAffectCols().val(propsMap).by(getPrimaryKeyColumns()).valArr(pkArr).update();
    }

    public int updateByExample(T entity) {
        Map<String, Object> propsMap = convertBeanToMap(entity);
        return fromTableAffectCols().val(propsMap).by(getPrimaryKeyColumns()).valObj(entity).update();
    }

    /**
     * @since skean 2.0
     */
    public int partialUpdate(T entity, Object[] pkArr, Collection<String> colsToUpdate){
        Map<String, Object> propsMap = convertBeanToMap(entity);
        String[] afCols=colsToUpdate.stream()
                .filter(col-> getAffectedColumns()==null || getAffectedColumns().contains(col))
                .toArray(String[]::new);
        return cjt.from(getTable()).affect(afCols).val(propsMap).by(getPrimaryKeyColumns()).valArr(pkArr).update();
    }

    /**
     * @since skean 2.0
     */
    public int partialUpdate(Map<String,Object> fieldsToUpdate, Object[] pkArr){
        if (fieldsToUpdate.isEmpty()){
            return 0;
        }
        return cjt.from(getTable()).affect(fieldsToUpdate).by(getPrimaryKeyColumns()).valArr(pkArr).update();
    }

    protected int delete(Object[] pkArr) {
        return fromTableByPkCols().val(pkArr).delete();
    }

    public int deleteByExample(T example) {
        return fromTableByPkCols().valObj(example).delete();
    }

    public int deleteSome(QueryParamsSuite qps) {
        return fromTable().suite(qps).delete();
    }
}