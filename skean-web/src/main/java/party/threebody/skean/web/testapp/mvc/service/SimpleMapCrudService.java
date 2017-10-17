package party.threebody.skean.web.testapp.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.threebody.skean.data.query.QueryParamsSuite;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * not so useful
 */
@Service
public class SimpleMapCrudService {

    @Autowired
    ChainedJdbcTemplate cjt;
    String table;
    String[] afCols;
    String[] byCols;

    public int create(String table, String[] afCols, Map<String, Object> entity) {
        return cjt.from(table).affect(afCols).valMap(entity).insert();
    }

    public int readCount(String table, QueryParamsSuite qps) {
        return cjt.from(table).criteria(qps.getCriteria()).count();
    }

    public List<Map<String, Object>> readListOfMap(String table, QueryParamsSuite qps) {
        return cjt.from(table).suite(qps).list();
    }

    public Map<String, Object> readOneOfMap(String table, Map<String, Object> byWhat) {
        return cjt.from(table).by(byWhat).single();
    }

    public Map<String, Object> readOneOfMap(String table, String[] byCols, Object[] byWhat) {
        return cjt.from(table).by(byCols).valArr(byWhat).single();
    }

    public int update(Map<String, Object> afWhat, Map<String, Object> byWhat) {
        return cjt.from(table).affect(afCols).valMap(afWhat).by(byCols).valMap(byWhat).update();
    }

    public int update(String[] afWhat, String[] byWhat) {
        return cjt.from(table).affect(afCols).valArr(afWhat).by(byCols).valArr(byWhat).update();
    }

    public int delete(Map<String, Object> byWhat) {
        return cjt.from(table).by(byCols).valMap(byWhat).delete();
    }

    public int delete(String[] byWhat) {
        return cjt.from(table).by(byCols).valArr(byWhat).delete();
    }

}
