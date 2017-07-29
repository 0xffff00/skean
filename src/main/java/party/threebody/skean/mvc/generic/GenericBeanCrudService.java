package party.threebody.skean.mvc.generic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;

@Service
public class GenericBeanCrudService<T> {

	@Autowired
	ChainedJdbcTemplate cjt;

	public GenericBeanCrudService(){
		
	}
	public GenericBeanCrudService(String table, Class<T> clazz, String[] afCols, String[] byCols) {
	
	}

	public int create(String table, T entity) {
		
		//String[] allCols = entity.keySet().toArray(new String[0]);
		String[] allCols=null;
		return cjt.from(table).affect(allCols).valObj(entity).insert();
	}

	public int create(String table, String[] afCols, T entity) {
		return cjt.from(table).affect(afCols).valObj(entity).insert();
	}


	public int count(String table, QueryParamsSuite qps) {
		return cjt.from(table).criteria(qps.getCriteria()).count();
	}

	public List<T> list(String table, QueryParamsSuite qps,Class<T> clazz) {
		return cjt.from(table).suite(qps).list(clazz);
	}

	public  T get(String table, Map<String, Object> byWhat,Class<T> clazz) {
		return cjt.from(table).by(byWhat).single(clazz);
	}

	public T get(String table, String[] byCols, Object[] byVals,Class<T> clazz) {
		return cjt.from(table).by(byCols).valArr(byVals).single(clazz);
	}

	public T get(String table, String[] byCols, Map<String, Object> byWhat,Class<T> clazz) {
		return cjt.from(table).by(byCols).valMap(byWhat).single(clazz);
	}

	public int update(String table, Map<String, Object> afWhat, Map<String, Object> byWhat) {
		return cjt.from(table).affect(afWhat).by(byWhat).update();

	}

	public int update(String table, String[] afCols, Map<String, Object> afWhat, String[] byCols,
			Map<String, Object> byWhat) {
		return cjt.from(table).affect(afCols).val(afWhat).by(byCols).valMap(byWhat).update();
	}
	
	public int update(String table, String[] afCols, Map<String, Object> afWhat, String[] byCols,
			 Object[] byVals) {
		return cjt.from(table).affect(afCols).val(afWhat).by(byCols).valArr(byVals).update();
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
