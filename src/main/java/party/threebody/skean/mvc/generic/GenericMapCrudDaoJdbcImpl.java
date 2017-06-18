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
	 * @see party.threebody.skean.mvc.generic.GenericMapCrudDao#count(java.lang.
	 * String, party.threebody.skean.core.query.QueryParamsSuite)
	 */
	@Override
	public int count(String table, QueryParamsSuite qps) {
		return jdbc.from(table).criteria(qps.getCriteria()).count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see party.threebody.skean.mvc.generic.GenericMapCrudDao#list(java.lang.
	 * String, party.threebody.skean.core.query.QueryParamsSuite)
	 */
	@Override
	public List<Map<String, Object>> list(String table, QueryParamsSuite qps) {
		return jdbc.from(table).suite(qps).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * party.threebody.skean.mvc.generic.GenericMapCrudDao#get(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public Map<String, Object> get(String table, Map<String, Object> byWhat) {
		return jdbc.from(table).by(byWhat).single();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * party.threebody.skean.mvc.generic.GenericMapCrudDao#get(java.lang.String,
	 * java.lang.String[], java.lang.Object[])
	 */
	@Override
	public Map<String, Object> get(String table, String[] byCols, Object[] byVals) {
		return jdbc.from(table).by(byCols).valArr(byVals).single();
	}

	@Override
	public Map<String, Object> get(String table, String[] byCols, Map<String, Object> byWhat) {
		return jdbc.from(table).by(byCols).valMap(byWhat).single();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * party.threebody.skean.mvc.generic.GenericMapCrudDao#update(java.lang.
	 * String, java.util.Map, java.util.Map)
	 */
	@Override
	public int update(String table, Map<String, Object> changes, Map<String, Object> byWhat) {
		return jdbc.from(table).affect(changes).by(byWhat).update();

	}
	
	@Override
	public int update(String table,String[] afCols, Map<String, Object> changes,String[] byCols, Map<String, Object> byWhat) {
		return jdbc.from(table).affect(afCols).val(changes).by(byCols).valMap(byWhat).update();

	}
	@Override
	public int delete(String table, Map<String, Object> byWhat) {
		return jdbc.from(table).by(byWhat).delete();
	}
	@Override
	public int delete(String table, String[] byCols, String[] byVals) {
		return jdbc.from(table).by(byCols).valArr(byVals).delete();
	}
	@Override
	public int delete(String table, String[] byCols, Map<String, Object> byWhat) {
		return jdbc.from(table).by(byCols).valMap(byWhat).delete();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * party.threebody.skean.mvc.generic.GenericMapCrudDao#insert(java.lang.
	 * String, java.util.Map)
	 */
	@Override
	public int insert(String table, Map<String, Object> entity) {
		String[] allCols = entity.keySet().toArray(new String[0]);
		return jdbc.from(table).affect(allCols).valMap(entity).insert();
	
	}

	@Override
	public int insert(String table, String[] afCols, Map<String, Object> entity) {
		return jdbc.from(table).affect(afCols).valMap(entity).insert();
	}

	

}
