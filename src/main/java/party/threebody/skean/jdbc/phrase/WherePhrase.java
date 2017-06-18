package party.threebody.skean.jdbc.phrase;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

/**
 * neither 'where().valObj()' nor 'where().valMap()' impl yet
 * 
 * @author hzk
 * @since 2017-06-18
 */
public class WherePhrase implements Phrase {

	private FromPhrase root;

	WherePhrase(FromPhrase root) {
		this.root = root;
	}

	// ------ value filling --------
	public ValPhrase valArr(Object[] vals) {
		return root.valArr(vals);
	}

	public ValPhrase val(Object... vals) {
		return root.val(vals);
	}

	// ------ fetching --------

	public List<Map<String, Object>> list() {
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

	// ------ modifying --------
	public int insert() {
		return root.insert();
	}

	public int update() {
		return root.update();
	}

	public int delete() {
		return root.delete();
	}

}
