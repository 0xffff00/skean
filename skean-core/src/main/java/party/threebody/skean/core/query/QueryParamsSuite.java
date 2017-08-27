package party.threebody.skean.core.query;

public class QueryParamsSuite {

	private Criterion[] criteria;
	private SortingField[] sortingField;
	private Integer pageIndex;
	private Integer pageLength;

	public QueryParamsSuite(Criterion[] criteria, SortingField[] sortingField, Integer pageIndex, Integer pageLength) {
		this.criteria = criteria;
		this.sortingField = sortingField;
		this.pageIndex = pageIndex;
		this.pageLength = pageLength;
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

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageLength() {
		return pageLength;
	}

	public int getPageIndexNonNull() {
		return pageIndex == null ? 1 : pageIndex;
	}

	public int getPageLengthNonNull() {
		return pageLength == null ? 0 : pageLength;
	}

	public void setPageLength(Integer pageLength) {
		this.pageLength = pageLength;
	}

	public boolean isPaginationEnabled() {
		return pageLength != null && pageLength > 0;
	}

}
