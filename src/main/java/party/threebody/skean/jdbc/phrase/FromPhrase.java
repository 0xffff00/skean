package party.threebody.skean.jdbc.phrase;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import party.threebody.skean.core.query.Criterion;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.core.query.SortingField;
import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;
import party.threebody.skean.jdbc.ChainedJdbcTemplateException;

public class FromPhrase extends DefaultRootPhrase {

	static Logger logger = LoggerFactory.getLogger(FromPhrase.class);

	String table;
	String tableAlias;
	String[] colsSelected;
	String[] colsAffected;
	String[] colsBy;

	String[] whereClauses;

	Criterion[] criteria;

	boolean valEnabled;
	Object valObj;
	Map<String, Object> valMap;
	Object[] valArr;

	boolean afValEnabled;
	Object afValObj;
	Map<String, Object> afValMap;
	Object[] afValArr;

	SortingField[] sortingFields;
	int limit, offset;

	public FromPhrase(ChainedJdbcTemplateContext context, String table) {
		super();
		this.context = context;
		this.table = table;
		enableCount = false;
		valEnabled = afValEnabled = false;
		limit = offset = 0;
	}

	public SelectPhrase select(String... cols) {
		colsSelected = cols;
		return new SelectPhrase(this);
	}

	// ------ suite -------
	public PagePhrase suite(QueryParamsSuite qps) {
		return criteria(qps.getCriteria()).orderBy(qps.getSortingField()).page(qps.getPageIndexNonNull(),
				qps.getPageLengthNonNull());
	}

	// ------ filtering --------
	public ByPhrase by(String... cols) {
		colsBy = cols;
		return new ByPhrase(this);
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
		this.valEnabled = true;
		this.valArr = vals;
		return new ValPhrase(this);
	}

	protected ValPhrase valMap(Map<String, Object> vals) {
		this.valEnabled = true;
		this.valMap = vals;
		return new ValPhrase(this);
	}

	protected ValPhrase valObj(Object vals) {
		this.valEnabled = true;
		this.valObj = vals;
		return new ValPhrase(this);
	}

	/**
	 * val(nullObj) will regarded as valMap(null)
	 * @param val
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected ValPhrase val(Object... val) {
		if (val == null){
			throw new ChainedJdbcTemplateException("'val(null)' is ambigious; please use valArr(null) or valMap(null) instead.");
		}
		if (val.length == 0) {
			return new ValPhrase(this);
		}
		if (val.length == 1) {
			Object v0 = val[0];
			if (v0==null){
				return valMap(Collections.emptyMap());
			}
			if (v0 instanceof Map) {
				return valMap((Map<String, Object>) v0);
			}
			if (!(v0 instanceof Number) && !(v0 instanceof String)) {
				return valObj(v0);
			}
		}
		return valArr(val);
	}

	// - afVal : .affect().val()
	protected AffectValPhrase afValArr(Object[] vals) {
		this.afValEnabled = true;
		this.afValArr = vals;
		return new AffectValPhrase(this);
	}

	protected AffectValPhrase afValMap(Map<String, Object> vals) {
		this.afValEnabled = true;
		this.afValMap = vals;
		return new AffectValPhrase(this);
	}

	protected AffectValPhrase afValObj(Object vals) {
		this.afValEnabled = true;
		this.afValObj = vals;
		return new AffectValPhrase(this);
	}

	@SuppressWarnings("unchecked")
	protected AffectValPhrase afVal(Object... val) {
		if (val == null || val.length == 0) {
			return new AffectValPhrase(this);
		}
		if (val.length == 1) {
			Object v0 = val[0];
			if (v0 instanceof Map) {
				return afValMap((Map<String, Object>) v0);
			}
			if (!(v0 instanceof Number) && !(v0 instanceof String)) {
				return afValObj(v0);
			}
		}
		return afValArr(val);
	}

	// ------ sorting --------
	public OrderByPhrase orderBy(String... cols) {
		this.sortingFields = CriteriaUtils.toSortingFields(cols);
		return new OrderByPhrase(this);
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
	public List<Map<String, Object>> list() {
		SqlAndArgs sa = context.getSqlBuilder().buildSelectSql(this);
		return context.getJdbcTmpl().query(sa.sql, sa.args, context.getColumnMapRowMapper());
	}

	public <T> List<T> list(Class<T> elementType) {
		SqlAndArgs sa = context.getSqlBuilder().buildSelectSql(this);
		return context.getJdbcTmpl().queryForList(sa.sql, elementType, sa.args);
	}

	public <T> List<T> list(RowMapper<T> rowMapper) {
		SqlAndArgs sa = context.getSqlBuilder().buildSelectSql(this);
		return context.getJdbcTmpl().query(sa.sql, sa.args, rowMapper);
	}

	public <T> T single(Class<T> elementType) {
		SqlAndArgs sa = context.getSqlBuilder().buildSelectSql(this);
		return context.getJdbcTmpl().queryForObject(sa.sql, elementType);
	}

	public Map<String, Object> single() {
		SqlAndArgs sa = context.getSqlBuilder().buildSelectSql(this);
		return context.getJdbcTmpl().queryForMap(sa.sql, sa.args);
	}

	// ------ modifying --------

	public AffectPhrase affect(String... cols) {
		colsAffected = cols;
		return new AffectPhrase(this);
	}

	public AffectValPhrase affect(Map<String, Object> colsNameValMap) {
		String[] cols = colsNameValMap.keySet().toArray(new String[0]);
		return affect(cols).valMap(colsNameValMap);
	}

	protected int insert() {
		SqlAndArgs sa = context.getSqlBuilder().buildInsertSql(this);
		return context.getJdbcTmpl().update(sa.sql, new ArgumentPreparedStatementSetter(sa.args));

	}

	protected int update() {
		SqlAndArgs sa = context.getSqlBuilder().buildUpdateSql(this);
		return context.getJdbcTmpl().update(sa.sql, new ArgumentPreparedStatementSetter(sa.args));

	}

	protected int delete() {
		SqlAndArgs sa = context.getSqlBuilder().buildDeleteSql(this);
		return context.getJdbcTmpl().update(sa.sql, new ArgumentPreparedStatementSetter(sa.args));

	}
}
