package party.threebody.skean.jdbc.phrase;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public class PagePhrase implements Phrase {

	private FromPhrase root;

	PagePhrase(FromPhrase root) {
		this.root = root;
	}

	// ------ fetching --------
	public List<Map<String,Object>> list() {
		return root.list();
	}
	public <T> List<T> list(Class<T> elementType) {
		return root.list(elementType);
	}
	public <T> List<T> list(RowMapper<T> rowMapper) {
		return root.list(rowMapper);
	}
	
	public Map<String, Object> first() {
		return root.first();
	}

	public <T> T first(Class<T> elementType) {
		return root.first(elementType);
	}

	public <T> T first(RowMapper<T> rowMapper) {
		return root.first(rowMapper);
	}

	public Object firstCell() {
		return root.firstCell();
	}

	public <T> T single(Class<T> elementType) {
		return root.single(elementType);
	}

	public int count() {
		return root.count();
	}

}
