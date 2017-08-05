package party.threebody.skean.jdbc.phrase;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import party.threebody.skean.core.query.SortingField;

public class ValPhrase implements Phrase {

	private FromPhrase root;

	ValPhrase(FromPhrase root) {
		this.root = root;
	}


	// ------ sorting --------
	public OrderByPhrase orderBy(String... cols) {
		return root.orderBy(cols);
	}

	public OrderByPhrase orderBy(SortingField[] cols) {
		return root.orderBy(cols);
	}

	// ------ paging --------
	public PagePhrase page(int page, int size) {
		return root.page(page, size);
	}

	public PagePhrase limit(int limit) {
		return root.limit(limit);
	}

	public PagePhrase offset(int offset) {
		return root.offset(offset);
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
	public <T> List<T> listOfSingleColumn(Class<T> columnType){
		return root.listOfSingleColumn(columnType);
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
	public Map<String,Object> single(){
		return root.single();
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
