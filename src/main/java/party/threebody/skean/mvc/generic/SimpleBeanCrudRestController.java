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
public abstract class SimpleBeanCrudRestController<T, PK> {
	static final String HEADER_NAME_TOTAL_COUNT = "X-Total-Count";

	protected abstract SingleBeanCrudService<T, PK> getSingleBeanCrudService();

	@GetMapping("/{pk}")
	public ResponseEntity<T> readOne(@PathVariable PK pk) {
		T entity = getSingleBeanCrudService().readOne(pk);
		return ResponseEntity.ok().body(entity);
	}

	@GetMapping("")
	public ResponseEntity<List<T>> readList(@RequestParam Map<String, String> reqestParamMap) {
		QueryParamsSuite qps = QueryParamsBuildUtils.buildQueryParamsSuite(reqestParamMap);
		List<T> entities = getSingleBeanCrudService().readList(qps);
		int totalCount = 0;
		if (qps.isPaginationEnabled()) {
			totalCount = getSingleBeanCrudService().readCount(qps);
		} else {
			totalCount = entities.size();
		}
		return ResponseEntity.ok().header(HEADER_NAME_TOTAL_COUNT, String.valueOf(totalCount)).body(entities);
	}

	@PostMapping("")
	public ResponseEntity<T> create(@RequestBody T entity) {
		T created = getSingleBeanCrudService().create(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);

	}

	@DeleteMapping("/{pk}")
	public ResponseEntity<Object> delete(@PathVariable PK pk) {
		return respondForUD(getSingleBeanCrudService().delete(pk));
	}

	@PutMapping("/{pk}")
	public ResponseEntity<Object> update(@PathVariable PK pk, @RequestBody T entity) {
		return respondForUD(getSingleBeanCrudService().update(entity, pk));
	}

	private static ResponseEntity<Object> respondForUD(int rowNumAffected) {
		if (rowNumAffected == 0) {
			return new ResponseEntity<Object>(HttpStatus.GONE);
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

}
