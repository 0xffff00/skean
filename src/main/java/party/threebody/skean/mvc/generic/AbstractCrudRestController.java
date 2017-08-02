package party.threebody.skean.mvc.generic;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.mvc.util.QueryParamsBuildUtils;

/**
 * a simple RestController supporting typical CRUD operations of an entity
 * bean<br>
 * ,which contains a single-column primary key.
 * 
 * @author hzk
 * @since 2017-08-01
 * @param <T>
 *            type of the entity bean
 * @param <PK>
 *            primitive type of the single-column primary key
 */
public abstract class AbstractCrudRestController<T, PK> {
	static final String HEADER_NAME_TOTAL_COUNT = "X-Total-Count";
	
	protected abstract T create(T entity);

	protected abstract List<T> readList(QueryParamsSuite qps);

	protected abstract int readCount(QueryParamsSuite qps);

	protected abstract T readOne(PK pk);

	protected abstract int update(T entity, PK pk);

	protected abstract int delete(PK pk);

	
	
	@GetMapping("/{pk}")
	public ResponseEntity<T> readOneViaHttp(@PathVariable PK pk) {
		T entity = readOne(pk);
		return ResponseEntity.ok().body(entity);
	}

	@GetMapping("")
	public ResponseEntity<List<T>> readListViaHttp(@RequestParam Map<String, String> reqestParamMap) {
		QueryParamsSuite qps = QueryParamsBuildUtils.buildQueryParamsSuite(reqestParamMap);
		List<T> entities = readList(qps);
		int totalCount = 0;
		if (qps.isPaginationEnabled()) {
			totalCount = readCount(qps);
		} else {
			totalCount = entities.size();
		}
		return ResponseEntity.ok().header(HEADER_NAME_TOTAL_COUNT, String.valueOf(totalCount)).body(entities);
	}

	@PostMapping("/")
	public ResponseEntity<T> createViaHttp(@RequestBody T entity) {
		T created = create(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);

	}

	@DeleteMapping("/{pk}")
	public ResponseEntity<Object> deleteViaHttp(@PathVariable PK pk) {
		return respondRowNumAffected(delete(pk));
	}

	@PutMapping("/{pk}")
	public ResponseEntity<Object> updateViaHttp(@PathVariable PK pk, @RequestBody T entity) {
		return respondRowNumAffected(update(entity, pk));
	}

	protected static ResponseEntity<Object> respondRowNumAffected(int rowNumAffected) {
		if (rowNumAffected == 0) {
			return new ResponseEntity<Object>(HttpStatus.GONE);
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

}
