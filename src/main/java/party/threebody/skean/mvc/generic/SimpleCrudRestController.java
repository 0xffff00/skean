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
public abstract class SimpleCrudRestController<T, PK> {
	static final String HEADER_NAME_TOTAL_COUNT = "X-Total-Count";
	
	private SimpleCrudRestConfig<T, PK> config;
	
	
	public SimpleCrudRestConfig<T, PK> getConfig() {
		return config;
	}

	public void setConfig(SimpleCrudRestConfig<T, PK> config) {
		this.config = config;
	}


	@GetMapping("/{pk}")
	public ResponseEntity<T> readOne(@PathVariable PK pk) {
		if (config.getOneReader() == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		T entity = config.getOneReader().apply(pk);
		return ResponseEntity.ok().body(entity);
	}

	@GetMapping("")
	public ResponseEntity<List<T>> readList(@RequestParam Map<String, String> reqestParamMap) {
		if (config.getListReader() == null || config.getCountReader() == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		QueryParamsSuite qps = QueryParamsBuildUtils.buildQueryParamsSuite(reqestParamMap);
		List<T> entities = config.getListReader().apply(qps);
		int totalCount = 0;
		if (qps.isPaginationEnabled()) {
			totalCount = config.getCountReader().apply(qps);
		} else {
			totalCount = entities.size();
		}
		return ResponseEntity.ok().header(HEADER_NAME_TOTAL_COUNT, String.valueOf(totalCount)).body(entities);
	}

	@PostMapping("/")
	public ResponseEntity<T> create(@RequestBody T entity) {
		if (config.getCreator() == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		T created = config.getCreator().apply(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);

	}

	@DeleteMapping("/{pk}")
	public ResponseEntity<Object> delete(@PathVariable PK pk) {
		if (config.getDeleter() == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return respondForUD(config.getDeleter().apply(pk));
	}

	@PutMapping("/{pk}")
	public ResponseEntity<Object> update(@PathVariable PK pk, @RequestBody T entity) {
		if (config.getUpdater() == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return respondForUD(config.getUpdater().apply(entity, pk));
	}

	private static ResponseEntity<Object> respondForUD(int rowNumAffected) {
		if (rowNumAffected == 0) {
			return new ResponseEntity<Object>(HttpStatus.GONE);
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}


	
}
