package party.threebody.skean.mvc.generic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;

@Repository
public class GenericMapCrudDaoJdbcImpl implements GenericMapCrudDao {

	@Autowired
	ChainedJdbcTemplate jdbc;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * party.threebody.skean.mvc.generic.GenericMapCrudDao#insert(java.lang.
	 * String, java.util.Map)
	 */
	@Override
	public int insert(String tableName, Map<String, Object> entity) {
		String[] allCols = entity.keySet().toArray(new String[0]);
		return jdbc.from(tableName).affect(allCols).valMap(entity).insert();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see party.threebody.skean.mvc.generic.GenericMapCrudDao#count(java.lang.
	 * String, party.threebody.skean.core.query.QueryParamsSuite)
	 */
	@Override
	public int count(String tableName, QueryParamsSuite qps) {
		return jdbc.from(tableName).criteria(qps.getCriteria()).count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see party.threebody.skean.mvc.generic.GenericMapCrudDao#list(java.lang.
	 * String, party.threebody.skean.core.query.QueryParamsSuite)
	 */
	@Override
	public List<Map<String, Object>> list(String tableName, QueryParamsSuite qps) {
		return jdbc.from(tableName).suite(qps).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * party.threebody.skean.mvc.generic.GenericMapCrudDao#get(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public Map<String, Object> get(String tableName, Map<String, Object> byWhat) {
		return jdbc.from(tableName).by(byWhat).single();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * party.threebody.skean.mvc.generic.GenericMapCrudDao#get(java.lang.String,
	 * java.lang.String[], java.lang.Object[])
	 */
	@Override
	public Map<String, Object> get(String tableName, String[] byColsName, Object[] byColsyVal) {
		return jdbc.from(tableName).by(byColsName).valArr(byColsyVal).single();
	}

	@Override
	public Map<String, Object> get(String tableName, String[] byColsName, Map<String, Object> byWhat) {
		return jdbc.from(tableName).by(byColsName).valMap(byWhat).single();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * party.threebody.skean.mvc.generic.GenericMapCrudDao#update(java.lang.
	 * String, java.util.Map, java.util.Map)
	 */
	@Override
	public int update(String tableName, Map<String, Object> changes, Map<String, Object> byWhat) {
		return jdbc.from(tableName).affect(changes).by(byWhat).update();

	}

	@Override
	public int delete(String tableName, Map<String, Object> byWhat) {
		return jdbc.from(tableName).by(byWhat).delete();
	}

}
