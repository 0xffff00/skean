package party.threebody.skean.mvc.generic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import party.threebody.skean.core.query.QueryParamsBuildUtils;
import party.threebody.skean.core.query.QueryParamsSuite;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ControllerUtils {

    static final String HEADER_NAME_TOTAL_COUNT = "X-Total-Count";
    static final String HEADER_NAME_TOTAL_AFFECTED = "X-Total-Affected";

    private ControllerUtils() {

    }

    /**
     * respond for a controller method by PLOx
     * @param reqestParamMap  reqestParams Map from a controller method
     * @param listReader a function to fetch entities list by QPS
     * @param countReader a function to fetch count without QPS
     * @param <E> the type of entity
     * @return what a controller method should return
     */
    public static <E> ResponseEntity<List<E>> respondListAndCountByPLOx(
            Map<String, String> reqestParamMap,
            Function<QueryParamsSuite, List<E>> listReader,
            Function<QueryParamsSuite, Integer> countReader) {
        QueryParamsSuite qps = QueryParamsBuildUtils.buildQueryParamsSuiteByPLOx(reqestParamMap);
        return respondListAndCount(qps, listReader, countReader);
    }

    public static <E> ResponseEntity<List<E>> respondListAndCountByPLOC(
            Map<String, String> reqestParamMap,
            Function<QueryParamsSuite, List<E>> listReader,
            Function<QueryParamsSuite, Integer> countReader) {

        QueryParamsSuite qps = QueryParamsBuildUtils.buildQueryParamsSuiteByPLOC(reqestParamMap);
        return respondListAndCount(qps, listReader, countReader);
    }

    public static <E> ResponseEntity<List<E>> respondListAndCount(
            QueryParamsSuite queryParamsSuite,
            Function<QueryParamsSuite, List<E>> listReader,
            Function<QueryParamsSuite, Integer> countReader) {

        List<E> entities = listReader.apply(queryParamsSuite);
        int totalCount = 0;
        if (queryParamsSuite.getPagingInfo().isPagingEnabled()) {
            totalCount = countReader.apply(queryParamsSuite);
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
