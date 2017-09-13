package party.threebody.skean.collections;

import party.threebody.skean.lang.Strings;

import java.util.List;

public class TreeNode<T> {
	private T val;
	private List<TreeNode<T>> sons;

	public TreeNode(T val) {
		this.val = val;
	}

	public TreeNode(T val, List<TreeNode<T>> sons) {
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

	@Override
	public String toString() {
		return toString(0);
	}

	public String toString(int indent) {
		String marginLeft = indent > 0 ? Strings.repeat("  ", indent - 1) + "|_" : "";

		String sonLines = "";
		if (sons != null && !sons.isEmpty()) {
			for (TreeNode<T> son : sons) {
				sonLines += son.toString(indent + 1);
			}
		}
		return marginLeft + val + '\n' + sonLines;
	}
}