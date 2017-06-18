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

	public int create(String table, Map<String, Object> entity) {
		return genericMapCrudDao.insert(table, entity);
	}

	public int create(String table, String[] afCols, Map<String, Object> what) {
		return genericMapCrudDao.insert(table, afCols, what);
	}

	public Map<String, Object> createAndGet(String table, Map<String, Object> what, String[] byCols) {
		genericMapCrudDao.insert(table, what);
		return genericMapCrudDao.get(table, byCols, what);
	}

	public Map<String, Object> createAndGet(String table, String[] afCols, Map<String, Object> what,
			String[] byCols) {
		genericMapCrudDao.insert(table, afCols, what);
		return genericMapCrudDao.get(table, byCols, what);
	}

	/**
	 * count only by criteria in QueryParamsSuite
	 * 
	 * @param table
	 * @param qps
	 * @return
	 */
	public int count(String table, QueryParamsSuite qps) {
		return genericMapCrudDao.count(table, qps);
	}

	public List<Map<String, Object>> list(String table, QueryParamsSuite qps) {
		return genericMapCrudDao.list(table, qps);
	}

	public Map<String, Object> get(String table, Map<String, Object> byWhat) {
		return genericMapCrudDao.get(table, byWhat);
	}

	public Map<String, Object> get(String table, String[] byCols, Object[] byWhat) {
		return genericMapCrudDao.get(table, byCols, byWhat);
	}

	public int update(String table, Map<String, Object> afWhat, Map<String, Object> byWhat) {
		return genericMapCrudDao.update(table, afWhat, byWhat);
	}

	public int update(String table, String[] afCols, Map<String, Object> afWhat, String[] byCols,
			Map<String, Object> byWhat) {
		return genericMapCrudDao.update(table, afCols, afWhat, byCols, byWhat);
	}

	public int delete(String table, Map<String, Object> byWhat) {
		return genericMapCrudDao.delete(table, byWhat);
	}

	public int delete(String table, String[] byCols, Map<String, Object> byWhat) {
		return genericMapCrudDao.delete(table, byCols, byWhat);
	}
}
