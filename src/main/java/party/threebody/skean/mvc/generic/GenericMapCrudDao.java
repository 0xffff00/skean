package party.threebody.skean.mvc.generic;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import party.threebody.skean.core.query.QueryParamsSuite;

@Repository
public interface GenericMapCrudDao {

	int count(String table, QueryParamsSuite qps);

	List<Map<String, Object>> list(String table, QueryParamsSuite qps);

	Map<String, Object> get(String table, Map<String, Object> byWhat);

	Map<String, Object> get(String table, String[] byCols, Object[] byVals);

	Map<String, Object> get(String table, String[] byCols, Map<String, Object> byWhat);

	int insert(String table, Map<String, Object> what);

	int insert(String table, String[] afCols, Map<String, Object> what);

	int update(String table, Map<String, Object> what, Map<String, Object> byWhat);

	int update(String table, String[] afCols, Map<String, Object> what, String[] byCols, Map<String, Object> byWhat);

	int delete(String table, Map<String, Object> byWhat);

	int delete(String table, String[] byCols, Map<String, Object> byWhat);

	int delete(String table, String[] byCols, String[] byVals);
}