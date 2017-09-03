package party.threebody.skean.jdbc.phrase;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

public class ArgPhrase implements Phrase {

    private SqlPhrase root;

    ArgPhrase(SqlPhrase root) {
        this.root = root;
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

    public <T> List<T> listOfSingleColumn(Class<T> columnType) {
        return root.listOfSingleColumn(columnType);
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

    public int execute() {
        return root.execute();
    }

}
