package party.threebody.skean.mvc.generic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import party.threebody.skean.core.query.QueryParamsSuite;

@Service
public class GenericMapCrudService {

	@Autowired
	GenericMapCrudDao genericMapCrudDao;
	
	public int insert(String tableName, Map<String, Object> entity, String[] byColsName) {
		return genericMapCrudDao.insert(tableName, entity);
	}
	public Map<String, Object> insertAndGetInserted(String tableName, Map<String, Object> entity, String[] byColsName) {
		genericMapCrudDao.insert(tableName, entity);
		return genericMapCrudDao.get(tableName, byColsName, entity);
	}

	/**
	 * count only by criteria in QueryParamsSuite
	 * @param tableName
	 * @param qps
	 * @return
	 */
	public int count(String tableName, QueryParamsSuite qps) {
		return genericMapCrudDao.count(tableName, qps);
	}

	public List<Map<String, Object>> list(String tableName, QueryParamsSuite qps) {
		return genericMapCrudDao.list(tableName, qps);
	}

	public Map<String, Object> get(String tableName, Map<String, Object> byColsNameValMap) {
		return genericMapCrudDao.get(tableName, byColsNameValMap);
	}

	public Map<String, Object> get(String tableName, String[] byColsName, Object[] byColsyVal){
		return genericMapCrudDao.get(tableName, byColsName, byColsyVal);
	}

	public int update(String tableName, Map<String, Object> changes, Map<String, Object> byWhat){
		return genericMapCrudDao.update(tableName, changes, byWhat);
	}
	
	public int delete(String tableName, Map<String, Object> byWhat){
		return genericMapCrudDao.delete(tableName, byWhat);
	}
}
