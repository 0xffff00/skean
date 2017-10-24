package party.threebody.skean.data.query;

import java.util.Map;

public class CriteriaAndSortingAndPaging extends Criteria{

	private SortingField[] sortingField;
	private PagingInfo pagingInfo;

	public CriteriaAndSortingAndPaging() {
	}

	public CriteriaAndSortingAndPaging(Criterion[] criteria, SortingField[] sortingField, PagingInfo pagingInfo) {
		setCriteria(criteria);
		this.sortingField = sortingField;
		this.pagingInfo = pagingInfo;
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
