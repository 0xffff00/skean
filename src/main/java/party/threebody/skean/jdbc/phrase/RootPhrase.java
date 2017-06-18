package party.threebody.skean.jdbc.phrase;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public interface RootPhrase extends Phrase {
	List<Map<String, Object>> list();

	<T> List<T> list(Class<T> elementType);

	<T> List<T> list(RowMapper<T> rowMapper);

	Map<String, Object> first();

	<T> T first(Class<T> elementType);

	<T> T first(RowMapper<T> rowMapper);

	Object firstCell();

	int count();
}
