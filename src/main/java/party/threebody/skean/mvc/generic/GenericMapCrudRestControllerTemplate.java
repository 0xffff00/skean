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
 * @author hzk
 * @since 2017-06-18
 */
@Component
public class GenericMapCrudRestControllerTemplate {

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



	public int count(String table, Map<String, String> reqestParamMap) {
		QueryParamsSuite qps = QueryParamsBuildUtils.buildQueryParamsSuite(reqestParamMap);
		return genericMapCrudService.count(table, qps);
	}

}