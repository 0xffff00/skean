/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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