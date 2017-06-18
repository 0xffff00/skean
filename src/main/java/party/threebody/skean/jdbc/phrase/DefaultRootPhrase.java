package party.threebody.skean.jdbc.phrase;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;

public abstract class DefaultRootPhrase implements RootPhrase {

	boolean enableCount;
	ChainedJdbcTemplateContext context;

	protected static Object firstMapElemVal(List<Map<String, Object>> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0).entrySet().iterator().next().getValue();
	}

	protected static <T> T firstElementOrNull(List<T> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public Map<String, Object> first() {
		return firstElementOrNull(list());
	}

	@Override
	public <T> T first(Class<T> elementType) {
		return firstElementOrNull(list(elementType));
	}

	@Override
	public <T> T first(RowMapper<T> rowMapper) {
		return firstElementOrNull(list(rowMapper));
	}

	@Override
	public Object firstCell() {
		return firstMapElemVal(list());
	}

	@Override
	public int count() {
		enableCount = true;
		Object obj = firstCell();
		if (obj == null) {
			return COUNT_NOTHING;
		}
		if (obj instanceof Number) {
			return ((Number) obj).intValue();
		} else {
			return COUNT_UNAVAILABLE;
		}

	}

}
