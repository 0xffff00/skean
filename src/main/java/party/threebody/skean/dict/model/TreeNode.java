package party.threebody.skean.dict.model;

import java.util.List;

public class TreeNode<T> {
	private T val;
	private List<TreeNode<T>> sons;

	TreeNode(T val) {
		this.val = val;
	}

	TreeNode(T val, List<TreeNode<T>> sons) {
		this.val = val;
		this.sons = sons;
	}

	public T getVal() {
		return val;
	}

	public void setVal(T val) {
		this.val = val;
	}

	public List<TreeNode<T>> getSons() {
		return sons;
	}

	public void setSons(List<TreeNode<T>> sons) {
		this.sons = sons;
	}

	String toString(int indent) {
		String marginLeft = new String(new char[indent]).replace("\0", "-");

		String sonLines = "";
		if (sons != null && !sons.isEmpty()) {
			for (TreeNode<T> son : sons) {
				sonLines += son.toString(indent + 1);
			}
		}
		return marginLeft + val + '\n' + sonLines;
	}
}