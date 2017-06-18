package party.threebody.skean.mvc.generic;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import party.threebody.skean.core.query.QueryParamsSuite;

@Repository
public interface GenericMapCrudDao {

	int count(String tableName, QueryParamsSuite qps);

	List<Map<String, Object>> list(String tableName, QueryParamsSuite qps);

	Map<String, Object> get(String tableName, Map<String, Object> byWhat);

	Map<String, Object> get(String tableName, String[] byColsName, Object[] byColsVal);

	Map<String, Object> get(String tableName, String[] byColsName, Map<String, Object> byWhat);

	int insert(String tableName, Map<String, Object> entity);

	int update(String tableName, Map<String, Object> changes, Map<String, Object> byWhat);

	int delete(String tableName, Map<String, Object> byWhat);

}