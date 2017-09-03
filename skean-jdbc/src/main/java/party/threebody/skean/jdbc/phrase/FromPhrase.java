package party.threebody.skean.jdbc.phrase;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;

import party.threebody.skean.core.query.Criterion;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.core.query.SortingField;
import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;
import party.threebody.skean.jdbc.ChainedJdbcTemplateException;
import party.threebody.skean.jdbc.util.CriteriaUtils;
import party.threebody.skean.jdbc.util.SqlAndArgs;
import party.threebody.skean.jdbc.util.ValueHolder;

public class FromPhrase extends DefaultRootPhrase {

	static Logger logger = LoggerFactory.getLogger(FromPhrase.class);

	String table;
	String tableAlias;
	String[] seCols; // column names to display, via 'select(seCols)'
	String[] afCols; // column names to affect, via 'affect(afCols)'
	String[] byCols; // column names to filter, via 'by(byCols)'

	String[] whereClauses;

	Criterion[] criteria;

	ValueHolder val; // values to affect or filter, via 'by(byCols).val(val)'
	ValueHolder afVal; // values to affect, via 'affect(afCols).val(afVal)'

	SortingField[] sortingFields;
	int limit, offset;

	public FromPhrase(ChainedJdbcTemplateContext context, String table) {
		super();
		this.context = context;
		this.table = table;
		enableCount = false;
		limit = offset = 0;
		val = new ValueHolder();
		afVal = new ValueHolder();
	}

	public SelectPhrase select(String... cols) {
		seCols = cols;
		return new SelectPhrase(this);
	}

	// ------ suite -------
	public PagePhrase suite(QueryParamsSuite qps) {
		if (qps == null) {
			return new PagePhrase(this);
		}
		return criteria(qps.getCriteria()).orderBy(qps.getSortingField()).page(qps.getPageIndexNonNull(),
				qps.getPageLengthNonNull());
	}

	// ------ filtering --------
	public ByPhrase by(String... cols) {
		byCols = cols;
		return new ByPhrase(this);
	}
	public ByPhrase by(List<String> cols){
		return by(toStringArray(cols));
	}

	public ValPhrase by(Map<String, Object> colsNameValMap) {
		String[] cols = colsNameValMap.keySet().toArray(new String[0]);
		return by(cols).valMap(colsNameValMap);
	}

	public WherePhrase where(String... whereClauses) {
		this.whereClauses = whereClauses;
		return new WherePhrase(this);
	}

	public CriteriaPhrase criteria(Criterion... criteria) {
		this.criteria = criteria;
		return new CriteriaPhrase(this);
	}

	// ------ value filling --------
	protected ValPhrase valArr(Object[] vals) {
		this.val.valArr(vals);
		return new ValPhrase(this);
	}

	protected ValPhrase valArr(Collection<Object> vals) {
		this.val.valColl(vals);
		return new ValPhrase(this);
	}

	protected ValPhrase valMap(Map<String, Object> vals) {
		this.val.valMap(vals);
		return new ValPhrase(this);
	}

	protected ValPhrase valObj(Object vals) {
		this.val.valObj(vals);
		return new ValPhrase(this);
	}

	/**
	 * val(nullObj) will regarded as valMap(null)
	 */
	protected ValPhrase val(Object... vals) {
		if (vals == null) {
			throw new ChainedJdbcTemplateException(
					"'val(null)' is ambigious; please use valArr(null) or valMap(null) instead.");
		}
		this.val.val(vals);
		return new ValPhrase(this);
	}

	// - afVal : .affect().val()
	protected AffectValPhrase afValArr(Object[] vals) {
		this.afVal.valArr(vals);
		return new AffectValPhrase(this);
	}

	protected AffectValPhrase afValMap(Map<String, Object> vals) {
		if (this.afCols==null){		//afCols should be generated if null
			this.afCols = vals.keySet().toArray(new String[0]);
		}
		this.afVal.valMap(vals);
		return new AffectValPhrase(this);
	}

	protected AffectValPhrase afValObj(Object vals) {
		this.afVal.valObj(vals);
		return new AffectValPhrase(this);
	}

	protected AffectValPhrase afVal(Object... vals) {
		if (vals == null) {
			throw new ChainedJdbcTemplateException(
					"'val(null)' is ambigious; please use valArr(null) or valMap(null) instead.");
		}
		this.afVal.val(vals);
		return new AffectValPhrase(this);
	}

	// ------ sorting --------
	public OrderByPhrase orderBy(String... cols) {
		this.sortingFields = CriteriaUtils.toSortingFields(cols);
		return new OrderByPhrase(this);
	}
	public OrderByPhrase orderBy(List<String> cols){
		return orderBy(toStringArray(cols));
	}

	public OrderByPhrase orderBy(SortingField[] cols) {
		this.sortingFields = cols;
		return new OrderByPhrase(this);
	}

	// ------ paging --------
	public PagePhrase page(int page, Integer size) {
		if (page < 1) {
			throw new ChainedJdbcTemplateException("page index should be a positive number.");
		}
		if (size < 0) {
			throw new ChainedJdbcTemplateException("page size should be a non-negative number.");
		}
		this.limit = size;
		this.offset = (page - 1) * size;
		return new PagePhrase(this);
	}

	public PagePhrase limit(int limit) {
		if (limit < 0) {
			throw new ChainedJdbcTemplateException("limit should be a non-negative number.");
		}
		this.limit = limit;
		return new PagePhrase(this);
	}

	public PagePhrase offset(int offset) {
		if (offset < 0) {
			throw new ChainedJdbcTemplateException("offset should be a non-negative number.");
		}
		this.offset = offset;
		return new PagePhrase(this);
	}

	// ------ fetching --------

	@Override
	protected SqlAndArgs buildSelectSqlAndArgs() {
		return context.getSqlBuilder().buildSelectSql(this);
	}

	// ------ modifying --------


	public AffectPhrase affect(String... cols) {
		afCols = cols;
		return new AffectPhrase(this);
	}
	/**
	 *
	 * @param cols if (List)null, means 'cols' will be generated by an 'afVal' after.
	 * @return
	 */
	public AffectPhrase affect(List<String> cols){
		return affect(toStringArray(cols));
	}

	public AffectValPhrase affect(Map<String, Object> colsNameValMap) {
		return affect((List<String>)null).valMap(colsNameValMap);
	}

	protected int insert() {
		SqlAndArgs sa = context.getSqlBuilder().buildInsertSql(this);
		context.getSqlPrinter().printSql(sa);
		int rna = context.getJdbcTemplate().update(sa.getSql(), new ArgumentPreparedStatementSetter(sa.getArgs()));
		context.getSqlPrinter().printRowNumAffected(rna);
		return rna;
	}

	protected int update() {
		SqlAndArgs sa = context.getSqlBuilder().buildUpdateSql(this);
		context.getSqlPrinter().printSql(sa);
		int rna = context.getJdbcTemplate().update(sa.getSql(), new ArgumentPreparedStatementSetter(sa.getArgs()));
		context.getSqlPrinter().printRowNumAffected(rna);
		return rna;

	}

	protected int delete() {
		SqlAndArgs sa = context.getSqlBuilder().buildDeleteSql(this);
		context.getSqlPrinter().printSql(sa);
		int rna = context.getJdbcTemplate().update(sa.getSql(), new ArgumentPreparedStatementSetter(sa.getArgs()));
		context.getSqlPrinter().printRowNumAffected(rna);
		return rna;
	}

	//---------util -----------
	protected static String[] toStringArray(List<String> list){
		if (list==null){
			return null;
		}
		return list.toArray(new String[list.size()]);
	}
}
