package party.threebody.skean.jdbc.phrase;

import java.util.Map;

import party.threebody.skean.core.query.BasicCriterion;

/**
 * a 'val()' phrase after 'affect()' phrase.
 * 
 * @author hzk
 * @since 2017-06-18
 */
public class AffectValPhrase implements Phrase {

	private FromPhrase root;

	AffectValPhrase(FromPhrase root) {
		this.root = root;
	}

	// ------ filtering --------
	public ByPhrase by(String... cols) {
		return root.by(cols);
	}

	public ValPhrase by(Map<String, Object> colsNameValMap) {
		return root.by(colsNameValMap);
	}

	public WherePhrase where(String... whereClauses) {
		return root.where(whereClauses);
	}

	public CriteriaPhrase criteria(BasicCriterion[] criteria) {
		return root.criteria(criteria);
	}

	// ------ modifying --------
	public int insert() {
		return root.insert();
	}

	public int delete() {
		return root.delete();
	}

}
