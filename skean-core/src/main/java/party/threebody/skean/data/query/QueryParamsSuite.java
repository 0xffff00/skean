package party.threebody.skean.data.query;

public class QueryParamsSuite {

	private Criterion[] criteria;
	private SortingField[] sortingField;
	private PagingInfo pagingInfo;

	public QueryParamsSuite(Criterion[] criteria, SortingField[] sortingField,PagingInfo pagingInfo) {
		this.criteria = criteria;
		this.sortingField = sortingField;
		this.pagingInfo = pagingInfo;
	}

	public Criterion[] getCriteria() {
		return criteria;
	}

	public void setCriteria(Criterion[] criteria) {
		this.criteria = criteria;
	}

	public SortingField[] getSortingField() {
		return sortingField;
	}

	public void setSortingField(SortingField[] sortingField) {
		this.sortingField = sortingField;
	}

	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}

	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}
}
