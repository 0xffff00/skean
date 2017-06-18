package party.threebody.skean.mvc.generic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;

public abstract class GenericTypeCrudDaoJdbcImpl<T> {

	@Autowired
	ChainedJdbcTemplate jdbc;

	BeanPropertyRowMapper<T> beanPropertyRowMapper;

	public GenericTypeCrudDaoJdbcImpl() {
		beanPropertyRowMapper = new BeanPropertyRowMapper<T>();
	}

	public int insert(Map<String, Object> entity) {
		String[] allCols = entity.keySet().toArray(new String[0]);
		return jdbc.from(getTableName()).affect(allCols).valMap(entity).insert();

	}

	public List<T> list(QueryParamsSuite qps) {
		return jdbc.from(getTableName()).suite(qps).list(beanPropertyRowMapper);
	}

	public T get(String[] pkVals) {
		// TODO check single result
		return jdbc.from(getTableName()).by(getPrimaryKeys()).valArr(pkVals).first(beanPropertyRowMapper);
	}

	public int update(Map<String, Object> changes, String[] pkVals) {
		String[] changesCols = changes.keySet().toArray(new String[0]);
		return jdbc.from(getTableName()).affect(changesCols).valObj(changes).by(getPrimaryKeys()).valArr(pkVals)
				.update();

	}

	public int delete(String[] pkVals) {
		return jdbc.from(getTableName()).by(getPrimaryKeys()).valArr(pkVals).delete();
	}

	protected abstract String getTableName();

	protected abstract String[] getPrimaryKeys();

}
