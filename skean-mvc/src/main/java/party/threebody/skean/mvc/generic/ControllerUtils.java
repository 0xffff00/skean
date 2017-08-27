package party.threebody.skean.mvc.generic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.mvc.util.QueryParamsBuildUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ControllerUtils {

	static final String HEADER_NAME_TOTAL_COUNT = "X-Total-Count";
	static final String HEADER_NAME_TOTAL_AFFECTED = "X-Total-Affected";

	private ControllerUtils() {

	}

	public static <E> ResponseEntity<List<E>> respondListAndCount(Map<String, String> reqestParamMap,
			Function<QueryParamsSuite, List<E>> listReader, Function<QueryParamsSuite, Integer> countReader) {

		QueryParamsSuite qps = QueryParamsBuildUtils.buildQueryParamsSuite(reqestParamMap);
		List<E> entities = listReader.apply(qps);
		int totalCount = 0;
		if (qps.isPaginationEnabled()) {
			totalCount = countReader.apply(qps);
		} else {
			totalCount = entities.size();
		}
		return ResponseEntity.ok().header(HEADER_NAME_TOTAL_COUNT, String.valueOf(totalCount)).body(entities);
	}

	public static ResponseEntity<Object> respondRowNumAffected(int rowNumAffected) {
		if (rowNumAffected == 0) {
			return new ResponseEntity<Object>(HttpStatus.GONE);
		}
		return ResponseEntity.noContent().header(HEADER_NAME_TOTAL_AFFECTED, String.valueOf(rowNumAffected)).build();
	}
}
