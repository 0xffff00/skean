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
    static final String HEADER_NAME_TOTAL_COUNT="X-Total-Count";
	public Map<String, Object> get(String tableName, Map<String, Object> byColsNameValMap) {
		return genericMapCrudService.get(tableName, byColsNameValMap);
	}

	public Map<String, Object> get(String tableName, String[] byColsName, Object[] byColsyVal) {
		return genericMapCrudService.get(tableName, byColsName, byColsyVal);
	}

	public ResponseEntity<List<Map<String, Object>>> list( String tableName,Map<String, String> reqestParamMap) {
		QueryParamsSuite qps = QueryParamsBuildUtils.buildQueryParamsSuite(reqestParamMap);
		 List<Map<String, Object>> items=genericMapCrudService.list(tableName, qps);
		 int totalCount=0;
		if (qps.isPaginationEnabled()){
			totalCount=genericMapCrudService.count(tableName, qps);
		}else{
			totalCount=items.size();
		}
		return ResponseEntity.ok().header(HEADER_NAME_TOTAL_COUNT, String.valueOf(totalCount)).body(items);
		
	}

	public ResponseEntity<Map<String, Object>> createAndGet(String tableName, Map<String, Object> entity, String[] byCols) {
		Map<String, Object> map = genericMapCrudService.insertAndGetInserted(tableName, entity, byCols);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.CREATED);
	}
	public ResponseEntity<Map<String, Object>> create(String tableName, Map<String, Object> entity, String[] byCols) {
		genericMapCrudService.insert(tableName, entity, byCols);
		return new ResponseEntity<Map<String, Object>>( HttpStatus.CREATED);
	}

	public ResponseEntity<Map<String, Object>> update(String tableName, Map<String, Object> changes,
			Map<String, Object> byWhat) {
		int rna = genericMapCrudService.update(tableName, changes, byWhat);
		if (rna == 0) {
			return new ResponseEntity<Map<String, Object>>(HttpStatus.GONE);
		}
		return new ResponseEntity<Map<String, Object>>(HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<Map<String, Object>> delete(String tableName, Map<String, Object> byWhat) {
		int rna = genericMapCrudService.delete(tableName, byWhat);
		if (rna == 0) {
			return new ResponseEntity<Map<String, Object>>(HttpStatus.GONE);

		}
		return new ResponseEntity<Map<String, Object>>(HttpStatus.NO_CONTENT);
	}

	public int count(String tableName, Map<String, String> reqestParamMap) {
		QueryParamsSuite qps = QueryParamsBuildUtils.buildQueryParamsSuite(reqestParamMap);
		return genericMapCrudService.count(tableName, qps);
	}

}