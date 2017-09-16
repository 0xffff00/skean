package party.threebody.skean.jdbc.phrase;

import org.springframework.jdbc.core.RowMapper;
import party.threebody.skean.core.query.SortingField;
import party.threebody.skean.jdbc.DualColsBean;

import java.util.List;
import java.util.Map;

/**
 * neither 'where().valObj()' nor 'where().valMap()' impl yet
 *
 * @author hzk
 * @since 2017-06-18
 */
public class WherePhrase implements Phrase {

    private FromPhrase root;

    WherePhrase(FromPhrase root) {
        this.root = root;
    }

    // ------ sorting --------
    public OrderByPhrase orderBy(String... cols) {
        return root.orderBy(cols);
    }

    public OrderByPhrase orderBy(SortingField[] cols) {
        return root.orderBy(cols);
    }

    // ------ value filling --------
    public ValPhrase valArr(Object[] vals) {
        return root.valArr(vals);
    }

    /**
     * valMap,valObj is disallowed.
     */
    public ValPhrase val(Object... vals) {
        return root.valArr(vals);
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

    // ------ modifying --------
    public int insert() {
        return root.insert();
    }

    public int update() {
        return root.update();
    }

    public int delete() {
        return root.delete();
    }

}
