package party.threebody.skean.mvc.generic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;

@Service
public class GenericMapCrudService {

	@Autowired
	ChainedJdbcTemplate cjt;

	public int create(String table, Map<String, Object> entity) {
		String[] allCols = entity.keySet().toArray(new String[0]);
		return cjt.from(table).affect(allCols).valMap(entity).insert();
	}

	public int create(String table, String[] afCols, Map<String, Object> entity) {
		return cjt.from(table).affect(afCols).valMap(entity).insert();
	}

	public Map<String, Object> createAndGet(String table, Map<String, Object> what, String[] byCols) {
		create(table, what);
		return get(table, byCols, what);
	}

	public Map<String, Object> createAndGet(String table, String[] afCols, Map<String, Object> what, String[] byCols) {
		create(table, afCols, what);
		return get(table, byCols, what);
	}


	public int count(String table, QueryParamsSuite qps) {
		return cjt.from(table).criteria(qps.getCriteria()).count();
	}

	public List<Map<String, Object>> list(String table, QueryParamsSuite qps) {
		return cjt.from(table).suite(qps).list();
	}

	public Map<String, Object> get(String table, Map<String, Object> byWhat) {
		return cjt.from(table).by(byWhat).single();
	}

	public Map<String, Object> get(String table, String[] byCols, Object[] byVals) {
		return cjt.from(table).by(byCols).valArr(byVals).single();
	}

	public Map<String, Object> get(String table, String[] byCols, Map<String, Object> byWhat) {
		return cjt.from(table).by(byCols).valMap(byWhat).single();
	}

	public int update(String table, Map<String, Object> changes, Map<String, Object> byWhat) {
		return cjt.from(table).affect(changes).by(byWhat).update();

	}

	public int update(String table, String[] afCols, Map<String, Object> changes, String[] byCols,
			Map<String, Object> byWhat) {
		return cjt.from(table).affect(afCols).val(changes).by(byCols).valMap(byWhat).update();

	}

	public int delete(String table, Map<String, Object> byWhat) {
		return cjt.from(table).by(byWhat).delete();
	}

	public int delete(String table, String[] byCols, String[] byVals) {
		return cjt.from(table).by(byCols).valArr(byVals).delete();
	}

	public int delete(String table, String[] byCols, Map<String, Object> byWhat) {
		return cjt.from(table).by(byCols).valMap(byWhat).delete();
	}
	

}
