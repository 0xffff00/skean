package party.threebody.skean.jdbc.phrase;

import org.springframework.jdbc.core.RowMapper;
import party.threebody.skean.core.query.BasicCriterion;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.jdbc.DualColsBean;

import java.util.List;
import java.util.Map;

public class SelectPhrase implements Phrase {

    private FromPhrase root;

    SelectPhrase(FromPhrase root) {
        this.root = root;
    }

    // ------ filtering --------
    public PagePhrase suite(QueryParamsSuite qps) {
        return root.suite(qps);
    }

    public ByPhrase by(String... cols) {
        return root.by(cols);
    }

    public ValPhrase by(Map<String, Object> colsNameValMap) {
        return root.by(colsNameValMap);
    }

    public WherePhrase where(String... whereClauses) {
        return root.where(whereClauses);
    }

    public CriteriaPhrase criteria(BasicCriterion[] criteria) {
        return root.criteria(criteria);
    }

    // ------ value filling --------


    // ------ sorting --------
    public OrderByPhrase orderBy(String... cols) {
        return root.orderBy(cols);
    }

    // ------ paging --------
    public PagePhrase page(int page, int size) {
        return root.page(page, size);
    }

    public PagePhrase limit(int limit) {
        return root.limit(limit);
    }

    public PagePhrase offset(int offset) {
        return root.offset(offset);
    }

    // ------ fetching --------
    public List<Map<String, Object>> list() {
        return root.list();
    }

    public <T> List<T> list(Class<T> elementType) {
        return root.list(elementType);
    }

    public <T> List<T> list(RowMapper<T> rowMapper) {
        return root.list(rowMapper);
    }

    public <T> List<T> listOfSingleCol(Class<T> columnType) {
        return root.listOfSingleCol(columnType);
    }

    public <F0, F1> List<DualColsBean<F0, F1>> listOfDualCols(Class<F0> col0Clazz, Class<F1> col1Clazz) {
        return root.listOfDualCols(col0Clazz, col1Clazz);
    }

    public Map<String, Object> first() {
        return root.first();
    }

    public <T> T first(Class<T> elementType) {
        return root.first(elementType);
    }

    public <T> T first(RowMapper<T> rowMapper) {
        return root.first(rowMapper);
    }

    public Object firstCell() {
        return root.firstCell();
    }

    public <T> T single(Class<T> elementType) {
        return root.single(elementType);
    }

    public int count() {
        return root.count();
    }

}
