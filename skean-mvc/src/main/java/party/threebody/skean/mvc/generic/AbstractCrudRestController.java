package party.threebody.skean.mvc.generic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import party.threebody.skean.core.query.QueryParamsSuite;

import java.util.List;
import java.util.Map;

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
		return ControllerUtils.respondListAndCount(reqestParamMap, this::readList, this::readCount);
	}

	@PostMapping("/")
	public ResponseEntity<T> createViaHttp(@RequestBody T entity) {
		T created = create(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);

	}

	@DeleteMapping("/{pk}")
	public ResponseEntity<Object> deleteViaHttp(@PathVariable PK pk) {
		return ControllerUtils.respondRowNumAffected(delete(pk));
	}

	@PutMapping("/{pk}")
	public ResponseEntity<Object> updateViaHttp(@PathVariable PK pk, @RequestBody T entity) {
		return ControllerUtils.respondRowNumAffected(update(entity, pk));
	}

}
