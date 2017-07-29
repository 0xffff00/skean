package party.threebody.skean.mvc.generic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import party.threebody.skean.core.query.QueryParamsSuite;

/**
 * a middleware between controller layer and service layer
 * 
 * 
 * @author hzk
 * @since 2017-07-27
 */
@Component
public class GenericBeanCrudRestControllerTemplate {

	@Autowired
	GenericMapCrudService genericMapCrudService;
	static final String HEADER_NAME_TOTAL_COUNT = "X-Total-Count";

	public Map<String, Object> get(String table, Map<String, Object> byWhat) {
		return genericMapCrudService.get(table, byWhat);

	}

	public Map<String, Object> get(String table, String[] byCols, Object[] byVals) {
		return genericMapCrudService.get(table, byCols, byVals);
	}

	public ResponseEntity<List<Map<String, Object>>> list(String table, Map<String, String> reqestParamMap) {
		QueryParamsSuite qps = QueryParamsBuildUtils.buildQueryParamsSuite(reqestParamMap);
		List<Map<String, Object>> items = genericMapCrudService.list(table, qps);
		int totalCount = 0;
		if (qps.isPaginationEnabled()) {
			totalCount = genericMapCrudService.count(table, qps);
		} else {
			totalCount = items.size();
		}
		return ResponseEntity.ok().header(HEADER_NAME_TOTAL_COUNT, String.valueOf(totalCount)).body(items);

	}

	public ResponseEntity<Map<String, Object>> createAndGet(String table, Map<String, Object> entity, String[] byCols) {
		Map<String, Object> map = genericMapCrudService.createAndGet(table, entity, byCols);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
	}

	public ResponseEntity<Map<String, Object>> create(String table, Map<String, Object> entity) {
		genericMapCrudService.create(table, entity);
		return new ResponseEntity<Map<String, Object>>(HttpStatus.CREATED);
	}

	public ResponseEntity<Map<String, Object>> createAndGet(String table, String[] afCols, Map<String, Object> entity,
			String[] byCols) {
		Map<String, Object> map = genericMapCrudService.createAndGet(table, afCols, entity, byCols);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
	}

	public ResponseEntity<Map<String, Object>> create(String table, String[] afCols, Map<String, Object> entity,
			String[] byCols) {
		genericMapCrudService.create(table, afCols, entity);
		return new ResponseEntity<Map<String, Object>>(HttpStatus.CREATED);
	}

	public ResponseEntity<Map<String, Object>> update(String table, Map<String, Object> changes,
			Map<String, Object> byWhat) {
		return respondUD(genericMapCrudService.update(table, changes, byWhat));
	}
	
	public ResponseEntity<Map<String, Object>> update(String table, String[] afCols, Map<String, Object> afWhat,
			String[] byCols, Object[] byVals) {
		return respondUD(genericMapCrudService.update(table, afCols, afWhat, byCols, byVals));
	}
	public ResponseEntity<Map<String, Object>> update(String table, String[] afCols, Map<String, Object> afWhat,
			String[] byCols, Map<String, Object> byWhat) {
		return respondUD(genericMapCrudService.update(table, afCols, afWhat, byCols, byWhat));
	}

	public ResponseEntity<Map<String, Object>> delete(String table, Map<String, Object> byWhat) {
		return respondUD(genericMapCrudService.delete(table, byWhat));
	}

	public ResponseEntity<Map<String, Object>> delete(String table, String[] byCols, String[] byVals) {
		return respondUD(genericMapCrudService.delete(table, byCols, byVals));
	}

	public ResponseEntity<Map<String, Object>> delete(String table, String[] byCols, Map<String, Object> byWhat) {
		return respondUD(genericMapCrudService.delete(table, byCols, byWhat));
	}

	private ResponseEntity<Map<String, Object>> respondUD(int rowNumAffected) {
		if (rowNumAffected == 0) {
			return new ResponseEntity<Map<String, Object>>(HttpStatus.GONE);
		}
		return new ResponseEntity<Map<String, Object>>(HttpStatus.NO_CONTENT);
	}

	public int count(String table, Map<String, String> reqestParamMap) {
		QueryParamsSuite qps = QueryParamsBuildUtils.buildQueryParamsSuite(reqestParamMap);
		return genericMapCrudService.count(table, qps);
	}

}