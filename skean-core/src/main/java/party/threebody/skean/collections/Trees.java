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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Tree Utilities Class
 * 
 * @author hzk
 * @since 2017-07-26
 */
public final class Trees {

	private Trees() {

	}

	public static <T> TreeNode<T> buildTreeRecursively(T val, Function<T, List<T>> meToSonsMapper) {
		TreeNode<T> res = new TreeNode<>(val);
		List<T> sonsVal = meToSonsMapper.apply(val);
		List<TreeNode<T>> sons = sonsVal.stream().map(s -> buildTreeRecursively(s, meToSonsMapper))
				.collect(Collectors.toList());
		res.setSons(sons);
		return res;
	}

	/**
	 * DFS
	 * 
	 * @param tree
	 * @param visitAction
	 */
	public static <T> void traverseInPreOrder(TreeNode<T> tree, Consumer<T> visitAction) {
		visitAction.accept(tree.getVal());
		tree.getSons().forEach(son -> traverseInPreOrder(son, visitAction));
	}

	/**
	 * BFS
	 * 
	 * @param tree
	 * @param visitAction
	 */
	public static <T> void traverseInLevelOrder(TreeNode<T> tree, Consumer<T> visitAction) {
		// TODO bfs algorithm
	}

	public static <T> List<T> flatten(TreeNode<T> tree) {
		List<T> res = new ArrayList<>();
		traverseInPreOrder(tree, res::add);
		return res;
	}

	public static <T> List<T> flattenDistinct(TreeNode<T> tree) {
		return flatten(tree).stream().distinct().collect(Collectors.toList());
	}

	public static <T> List<T> flattenDistinct(Collection<TreeNode<T>> trees) {
		Set<T> set = new HashSet<>();
		List<T> list = new ArrayList<>();
		trees.forEach(tree -> {
			traverseInPreOrder(tree, val -> {
				if (!set.contains(val)) {
					set.add(val);
					list.add(val);
				}

			});
		});
		return list;
	}
}
