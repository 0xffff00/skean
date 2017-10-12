package party.threebody.skean.jdbc.phrase;

import java.util.Map;

import party.threebody.skean.data.query.BasicCriterion;

public class AffectPhrase implements Phrase {

	private FromPhrase root;

	AffectPhrase(FromPhrase root) {
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

	// ------ value(affect) filling --------

	public AffectValPhrase valArr(Object[] vals) {
		return root.afValArr(vals);
	}

	public AffectValPhrase valMap(Map<String, Object> vals) {
		return root.afValMap(vals);
	}

	public AffectValPhrase valObj(Object vals) {
		return root.afValObj(vals);
	}

	public AffectValPhrase val(Object... vals) {
		return root.afVal(vals);
	}

	
}
